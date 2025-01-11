package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final JdbcClient jdbcClient;

    public CatalogServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Page<CardResponseDto> fetchPlantCardsByPagination(Pageable pageable, CatalogFilterDto filter) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        var statement = """
                SELECT id, common_name, situation
                FROM plants
                LIMIT :limit
                OFFSET :offset;
                """;

        List<CardResponseDto> response = jdbcClient.sql(statement)
                .param("limit", limit)
                .param("offset", offset)
                .query((resultSet, rowNum) ->
                        new CardResponseDto(
                                resultSet.getLong("id"),
                                resultSet.getString("common_name"),
                                Situation.valueOf(resultSet.getString("situation").toUpperCase())
                        ))
                .list();

        var statementCount = """
                SELECT count(*)
                FROM plants
                """;

        Long count = jdbcClient.sql(statementCount)
                .query((resultSet, rowNum) -> resultSet.getLong("count"))
                .single();


//        Page<CardResponseDto> page = plantJpaRepository.findPlantCardsBySituation(filter.situation().name(), pageable);

//        long situationCount = plantJpaRepository.countPlantCardsBySituation(limit, offset, filter.situation());

        /*
        if (Objects.isNull(filter)) {
            plantCardsObtained = plantJpaRepository.findPlantCards(limit, offset);
        } else {
            if (Objects.nonNull(filter.situation()) && Objects.nonNull(filter.categories())) {
                plantCardsObtained = plantJpaRepository.findPlantCardsBySituationAndCategory(limit, offset, filter.situation().name(), Category.ORNAMENTAL.name());
            } else {
                if (Objects.nonNull(filter.situation())) {
                    plantCardsObtained = plantJpaRepository.findPlantCardsBySituation(limit, offset, filter.situation().name());
                } else {
                    plantCardsObtained = plantJpaRepository.findPlantCardsByCategory(limit, offset, Category.ORNAMENTAL.name());
                }
            }
        }*/

        return new PageImpl<>(response, pageable, count);
        /*List<String> categoryNames = filter.categories().stream()
                .map(Category::name)
                .toList();

        long count = plantJpaRepository.countPlantsByCategories(categoryNames);

        List<CardResponseDto> plantCardsObtained = plantJpaRepository.findPlantCardsByCategories(limit, offset, filter.categories());

        return new PageImpl<>(plantCardsObtained, pageable, plantJpaRepository.count());*/
    }
}
