package org.lievasoft.garden.service;

import jakarta.persistence.EntityNotFoundException;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
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

        Assert.state(result == 1, "Plant has not been persisted");

        var obtainPlantByIDSql = """
                SELECT uuid, common_name, scientific_name, situation
                FROM plants
                WHERE uuid = :uuid;
                """;


        return jdbcClient.sql(obtainPlantByIDSql)
                .param("uuid", generatedUUID)
                .query((resultSet, rowNum) -> new PlantResponseDto(
                        resultSet.getString("uuid"),
                        resultSet.getString("common_name"),
                        resultSet.getString("scientific_name"),
                        Situation.valueOf(resultSet.getString("situation").toUpperCase()),
                        Collections.emptySet()

                ))
                .optional()
                .orElseThrow(() -> new EntityNotFoundException("Plant with uuid " + generatedUUID + " not found"));
    }
}
