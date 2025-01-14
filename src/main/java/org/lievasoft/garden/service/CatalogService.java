package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {

    Page<CardResponseDto> fetchPlantCards(Pageable pageable);

    Page<CardResponseDto> fetchFilteredPlantCards(Pageable pageable, CatalogFilterDto filter);
}
