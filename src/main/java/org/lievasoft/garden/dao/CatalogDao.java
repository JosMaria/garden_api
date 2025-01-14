package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatalogDao {

    Page<CardResponseDto> plantCardPage(Pageable pageable);

    Page<CardResponseDto> plantCardPageBySituation(Pageable pageable, String situation);

    List<CardResponseDto> findPlantCardsWithFilters(int limit, int offset, CatalogFilterDto filters);

    long countWithFilters(CatalogFilterDto filters);
}
