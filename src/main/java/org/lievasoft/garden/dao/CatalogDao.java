package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatalogDao {

    List<CardResponseDto> findPlantCards(int limit, int offset);

    long countPlantCardsWithoutFilter();

    List<CardResponseDto> findPlantCardsWithFilters(int limit, int offset, CatalogFilterDto filters);

    long countWithFilters(CatalogFilterDto filters);

    List<CardResponseDto> findFilteredPlantCardsBySituation(Pageable pageable, Situation situation);

    Long countFilteredBySituation(Situation situation);
}
