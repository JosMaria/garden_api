package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class CatalogDataAccess implements CatalogDao {

    private final JdbcClient jdbcClient;

    private final RowMapper<CardResponseDto> rowMapper = (ResultSet resultSet, int rowNum) ->
            new CardResponseDto(
                    resultSet.getLong("id"),
                    resultSet.getString("common_name"),
                    Situation.valueOf(resultSet.getString("situation").toUpperCase())
            );

    public CatalogDataAccess(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<CardResponseDto> findPlantCards(int limit, int offset) {
        var statement = """
                SELECT id, common_name, situation
                FROM plants
                LIMIT :limit
                OFFSET :offset;
                """;

        return jdbcClient.sql(statement)
                .param("limit", limit)
                .param("offset", offset)
                .query(rowMapper)
                .list();
    }

    @Override
    public long countPlantCards() {
        var statement = """
                SELECT count(*)
                FROM plants
                """;

        return jdbcClient.sql(statement)
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
    }

    @Override
    public List<CardResponseDto> findPlantCardsBySituation(int limit, int offset, String situation) {
        var statement = """
                SELECT id, common_name, situation
                FROM plants
                WHERE situation = cast(:situation AS situation)
                LIMIT :limit
                OFFSET :offset;
                """;

        return jdbcClient.sql(statement)
                .param("situation", situation)
                .param("limit", limit)
                .param("offset", offset)
                .query(rowMapper)
                .list();
    }

    @Override
    public long countPlantCardsBySituation(String situation) {
        var statement = """
                SELECT count(*)
                FROM plants
                WHERE situation = cast(:situation AS situation)
                """;

        return jdbcClient.sql(statement)
                .param("situation", situation)
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
    }

    @Override
    public List<CardResponseDto> findPlantCardsByClassifications(int limit, int offset, Set<String> classifications) {
        var statement = """
                SELECT id, common_name, situation
                FROM plants
                JOIN (
                    SELECT DISTINCT plant_id
                    FROM classifications
                    WHERE cast(value AS VARCHAR) IN (:values)
                ) f ON id = f.plant_id
                LIMIT :limit
                OFFSET :offset
                """;

        return jdbcClient.sql(statement)
                .params(Collections.singletonMap("values", classifications))
                .param("limit", limit)
                .param("offset", offset)
                .query(rowMapper)
                .list();
    }

    @Override
    public long countPlantCardsByClassifications(Set<String> classifications) {
        var statement = """
                SELECT count(*)
                FROM plants
                JOIN (
                    SELECT DISTINCT plant_id
                    FROM classifications
                    WHERE cast(value AS VARCHAR) IN (:values)
                ) f ON id = f.plant_id
                """;

        return jdbcClient.sql(statement)
                .params(Collections.singletonMap("values", classifications))
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
    }

    @Override
    public List<CardResponseDto> findFilteredPlantCards(int limit, int offset, Set<String> classifications, String situation) {
        var statement = """
                SELECT id, common_name, situation
                FROM plants
                JOIN (
                    SELECT DISTINCT plant_id
                    FROM classifications
                    WHERE cast(value AS VARCHAR) IN (:values)
                ) f ON id = f.plant_id
                WHERE situation = cast(:situation AS situation)
                LIMIT :limit
                OFFSET :offset
                """;

        return jdbcClient.sql(statement)
                .params(Collections.singletonMap("values", classifications))
                .param("situation", situation)
                .param("limit", limit)
                .param("offset", offset)
                .query(rowMapper)
                .list();
    }

    @Override
    public long countFilteredPlantCards(Set<String> classifications, String situation) {
        var statement = """
                SELECT count(*)
                FROM plants
                JOIN (
                    SELECT DISTINCT plant_id
                    FROM classifications
                    WHERE cast(value AS VARCHAR) IN (:values)
                ) f ON id = f.plant_id
                WHERE situation = cast(:situation AS situation)
                """;

        return jdbcClient.sql(statement)
                .params(Collections.singletonMap("values", classifications))
                .param("situation", situation)
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
    }
}
