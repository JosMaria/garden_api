package org.lievasoft.garden.service;

import org.lievasoft.garden.dao.CatalogDao;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogDao catalogDao;

    public CatalogServiceImpl(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    @Override
    public Page<CardResponseDto> fetchPlantCards(Pageable pageable) {
        return catalogDao.plantCardPage(pageable);
    }

    @Override
    public Page<CardResponseDto> fetchFilteredPlantCards(Pageable pageable, CatalogFilterDto filter) {
        return catalogDao.plantCardPageBySituation(pageable, filter.situation().name().toLowerCase());
    }
}
