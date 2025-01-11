package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CatalogDataAccess implements CatalogDao {

    private final JdbcClient jdbcClient;

    public CatalogDataAccess(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<CardResponseDto> findPlantCardsWithoutFilters(int limit, int offset) {
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

    @Override
    public long countPlantCardsWithoutFilter() {
        var statementCount = """
                SELECT count(*)
                FROM plants
                """;

        return jdbcClient.sql(statementCount)
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();
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
