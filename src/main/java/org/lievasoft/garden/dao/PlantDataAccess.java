package org.lievasoft.garden.dao;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlantDataAccess implements PlantDao {

    private final JdbcClient jdbcClient;
    private final KeyHolder keyHolder;

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
    public boolean existsById(Long plantId) {
        var statement = """
                SELECT exists (
                    SELECT id
                    FROM plants
                    WHERE id = :id
                );
                """;

        return jdbcClient.sql(statement)
                .param("id", plantId)
                .query((resultSet, rowNum) -> resultSet.getBoolean("exists"))
                .single();
    }

    @Override
    public long insertPlant(PlantCreateDto dto) {
        var statement = """
                INSERT INTO plants (common_name, scientific_name, situation)
                VALUES (:commonName, :scientificName, cast(:situation AS situation));
                """;

        jdbcClient.sql(statement)
                .param("commonName", dto.commonName())
                .param("scientificName", dto.scientificName())
                .param("situation", dto.situation().name().toLowerCase())
                .update(keyHolder, "id");

        return Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(() -> new IllegalArgumentException("Key not found"))
                .longValue();
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
}
