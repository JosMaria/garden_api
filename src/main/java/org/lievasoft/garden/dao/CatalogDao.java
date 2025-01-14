package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.entity.Classification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CatalogDao {

    Page<CardResponseDto> plantCardPage(Pageable pageable);

    Page<CardResponseDto> plantCardPageBySituation(Pageable pageable, String situation);

    Page<CardResponseDto> plantCardPageByClassifications(Pageable pageable, Set<Classification> classifications);

    Page<CardResponseDto> filteredPlantCardPage(Pageable pageable, CatalogFilterDto filters);
}
