package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CatalogDataAccess implements CatalogDao {

    private final JdbcClient jdbcClient;

    public CatalogDataAccess(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    RowMapper<CardResponseDto> rowMapper = (ResultSet resultSet, int rowNum) ->
            new CardResponseDto(
                    resultSet.getLong("id"),
                    resultSet.getString("common_name"),
                    Situation.valueOf(resultSet.getString("situation").toUpperCase())
            );

    @Override
    public Page<CardResponseDto> plantCardPage(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<CardResponseDto> content = findPlantCards(offset, limit);
        long total = countPlantCards();

        return new PageImpl<>(content, pageable, total);
    }

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
    public Page<CardResponseDto> plantCardPageBySituation(Pageable pageable, String situation) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<CardResponseDto> content = findPlantCardsBySituation(limit, offset, situation);
        long total = countPlantCardsBySituation(situation);
        return new PageImpl<>(content, pageable, total);
    }

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
    public Page<CardResponseDto> plantCardPageByClassifications(Pageable pageable, Set<Classification> classifications) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        Set<String> mappedClassifications = classifications.stream()
                .map(classification -> classification.name().toLowerCase())
                .collect(Collectors.toSet());

        List<CardResponseDto> content = findPlantCardsByClassifications(limit, offset, mappedClassifications);
        long total = countPlantCardsByClassifications(mappedClassifications);
        return new PageImpl<>(content, pageable, total);
    }

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
}
