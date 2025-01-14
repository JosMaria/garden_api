package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CatalogDataAccess implements CatalogDao {

    private final JdbcClient jdbcClient;

    public CatalogDataAccess(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

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
                .query(new CardResponseMapper())
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
                .query(new CardResponseMapper())
                .list();
    }

    public long countPlantCardsBySituation(String situation) {
        var statement = """
                SELECT id, common_name, situation
                FROM plants
                WHERE situation = cast(:situation AS situation)
                """;

        return jdbcClient.sql(statement)
                .param("situation", situation)
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
    }

    @Override
    public long countWithFilters(CatalogFilterDto filters) {
        String collectionToInClause = filters.classifications()
                .stream()
                .map(classification -> String.format("'%s'", classification.name().toLowerCase()))
                .collect(Collectors.joining(", "));

        var statement = """
                SELECT count(*)
                FROM plants
                JOIN (
                    SELECT DISTINCT plant_id
                    FROM classifications
                    WHERE value IN (%s)
                ) f ON id = f.plant_id
                WHERE situation = cast(:situation AS situation)
                """.formatted(collectionToInClause);

        return jdbcClient.sql(statement)
                .param("situation", filters.situation().name().toLowerCase())
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
    }

    @Override
    public List<CardResponseDto> findPlantCardsWithFilters(int limit, int offset, CatalogFilterDto filters) {
        String collectionToInClause = filters.classifications()
                .stream()
                .map(classification -> String.format("'%s'", classification.name().toLowerCase()))
                .collect(Collectors.joining(", "));

        var statement = """
                SELECT id, common_name, situation
                FROM plants
                JOIN (
                    SELECT DISTINCT plant_id
                    FROM classifications
                    WHERE value IN (%s)
                ) f ON id = f.plant_id
                WHERE situation = cast(:situation AS situation)
                LIMIT :limit
                OFFSET :offset
                """.formatted(collectionToInClause);

        return jdbcClient.sql(statement)
                .param("situation", filters.situation().name().toLowerCase())
                .param("limit", limit)
                .param("offset", offset)
                .query(new CardResponseMapper())
                .list();
    }
}

class CardResponseMapper implements RowMapper<CardResponseDto> {
    @Override
    public CardResponseDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new CardResponseDto(
                resultSet.getLong("id"),
                resultSet.getString("common_name"),
                Situation.valueOf(resultSet.getString("situation").toUpperCase())
        );
    }
}
