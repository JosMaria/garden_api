package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.ClassificationToPersist;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.dto.PlantToPersist;
import org.lievasoft.garden.entity.Situation;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlantDataAccess implements PlantDao {

    private final JdbcClient jdbcClient;

    public PlantDataAccess(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public boolean existsByCommonName(String commonName) {
        var statement = """
                SELECT exists (
                    SELECT id
                    FROM plants
                    WHERE common_name = :commonName
                );
                """;

        return jdbcClient.sql(statement)
                .param("commonName", commonName)
                .query((resultSet, rowNum) -> resultSet.getBoolean("exists"))
                .single();
    }

    @Override
    public int insertPlant(PlantToPersist dto) {
        var statement = """
                INSERT INTO plants (uuid, common_name, scientific_name, situation)
                VALUES (:uuid, :commonName, :scientificName, cast(:situation AS situation));
                """;

        return jdbcClient.sql(statement)
                .param("uuid", dto.uuid())
                .param("commonName", dto.commonName())
                .param("scientificName", dto.scientificName())
                .param("situation", dto.situation())
                .update();
    }

    @Override
    public int insertClassification(ClassificationToPersist dto) {
        var statement = """
                INSERT INTO classifications
                VALUES (:plantId, cast(:classification AS classification));
                """;

        return jdbcClient.sql(statement)
                .param("plantId", dto.plantId())
                .param("classification", dto.valueClassification())
                .update();
    }

    @Override
    public Optional<PlantResponseDto> findByUUID(String uuid) {
        var statement = """
                SELECT id, uuid, common_name, scientific_name, situation
                FROM plants
                WHERE uuid = :uuid;
                """;

        return jdbcClient.sql(statement)
                .param("uuid", uuid)
                .query((resultSet, rowNum) -> {
                    PlantResponseDto response = new PlantResponseDto();
                    response.setId(resultSet.getLong("id"));
                    response.setUuid(resultSet.getString("uuid"));
                    response.setCommonName(resultSet.getString("common_name"));
                    response.setScientificName(resultSet.getString("scientific_name"));
                    response.setSituation(Situation.valueOf(resultSet.getString("situation").toUpperCase()));
                    return response;
                })
                .optional();
    }
}
