package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;

import java.util.List;

public interface CatalogDao {

    List<CardResponseDto> findPlantCardsWithoutFilters(int limit, int offset);

    long countPlantCardsWithoutFilter();
}
