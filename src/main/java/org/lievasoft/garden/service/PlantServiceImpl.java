package org.lievasoft.garden.service;

import jakarta.persistence.EntityNotFoundException;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PlantServiceImpl implements PlantService {

    private final JdbcClient jdbcClient;

    public PlantServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public PlantResponseDto persist(final PlantCreateDto payload) {
        var insertPlantSql = """
                INSERT INTO plants (uuid, common_name, scientific_name, situation)
                VALUES (:uuid, :commonName, :scientificName, cast(:situation AS situation));
                """;

        String generatedUUID = UUID.randomUUID().toString();
        int result = jdbcClient.sql(insertPlantSql)
                .param("uuid", generatedUUID)
                .param("commonName", payload.commonName())
                .param("scientificName", payload.scientificName())
                .param("situation", payload.situation().getValue())
                .update();

        Assert.state(result == 1, "Plant: " + payload.commonName() + " has not been persisted");

        var obtainPlantByIDSql = """
                SELECT id, uuid, common_name, scientific_name, situation
                FROM plants
                WHERE uuid = :uuid;
                """;

        Set<Classification> persistedClassifications = new HashSet<>();
        PlantResponseDto response = jdbcClient.sql(obtainPlantByIDSql)
                .param("uuid", generatedUUID)
                .query((resultSet, rowNum) -> new PlantResponseDto(
                        resultSet.getLong("id"),
                        resultSet.getString("uuid"),
                        resultSet.getString("common_name"),
                        resultSet.getString("scientific_name"),
                        Situation.valueOf(resultSet.getString("situation").toUpperCase()),
                        persistedClassifications

                ))
                .optional()
                .orElseThrow(() -> new EntityNotFoundException("Plant with uuid " + generatedUUID + " not found"));

        var insertClassificationsSql = """
                INSERT INTO classifications
                VALUES (:plantId, cast(:classification AS classification));
                """;

        payload.classifications().forEach(classification -> {
            String value = classification.getValue();

            int countAffected = jdbcClient.sql(insertClassificationsSql)
                    .param("plantId", response.id())
                    .param("classification", value)
                    .update();

            Assert.state(countAffected == 1, "Classification: " + value + " has not been persisted");
        });

        persistedClassifications.addAll(payload.classifications());
        return response;
    }
}
