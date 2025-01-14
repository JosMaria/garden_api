package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

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
}
