package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogDao {

    Page<CardResponseDto> plantCardPage(Pageable pageable);

    Page<CardResponseDto> plantCardPageBySituation(Pageable pageable, String situation);
}
