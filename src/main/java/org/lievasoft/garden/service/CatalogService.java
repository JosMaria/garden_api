package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.CardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {

    Page<CardResponseDto> fetchPlantCardsByPagination(Pageable pageable);
}
