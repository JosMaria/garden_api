package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.PlantCreateDto;
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
    public int insertPlant(String uuid, PlantCreateDto dto) {
        var statement = """
                INSERT INTO plants (uuid, common_name, scientific_name, situation)
                VALUES (:uuid, :commonName, :scientificName, cast(:situation AS situation));
                """;

        return jdbcClient.sql(statement)
                .param("uuid", uuid)
                .param("commonName", dto.commonName())
                .param("scientificName", dto.scientificName())
                .param("situation", dto.situation())
                .update();
    }

    @Override
    public int insertClassification(Long plantId, String classificationValue) {
        var statement = """
                INSERT INTO classifications
                VALUES (:plantId, cast(:classification AS classification));
                """;

        return jdbcClient.sql(statement)
                .param("plantId", plantId)
                .param("classification", classificationValue)
                .update();
    }

    @Override
    public Optional<Long> findPlantIdByUuidAndCommonName(String uuid, String commonName) {
        var statement = """
                SELECT id
                FROM plants
                WHERE uuid = :uuid AND common_name = :commonName;
                """;

        return jdbcClient.sql(statement)
                .param("uuid", uuid)
                .param("commonName", commonName)
                .query((resultSet, rowNum) -> resultSet.getLong("id"))
                .optional();
    }
}
