package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;

import java.util.List;

public interface CatalogDao {

    List<CardResponseDto> findPlantCardsWithoutFilters(int limit, int offset);

    long countPlantCardsWithoutFilter();

    List<CardResponseDto> findPlantCardsWithFilters(int limit, int offset, CatalogFilterDto filters);

    long countWithFilters(CatalogFilterDto filters);
}
