package org.lievasoft.garden.service;

import org.lievasoft.garden.dao.CatalogDao;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogDao catalogDao;

    public CatalogServiceImpl(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    @Override
    public Page<CardResponseDto> fetchPlantCards(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<CardResponseDto> content = catalogDao.findPlantCards(limit, offset);
        long count = catalogDao.countPlantCardsWithoutFilter();
        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<CardResponseDto> fetchFilteredPlantCards(Pageable pageable, CatalogFilterDto filter) {
        List<CardResponseDto> content = catalogDao.findFilteredPlantCardsBySituation(pageable, filter.situation());
        Long count = catalogDao.countFilteredBySituation(filter.situation());
        return new PageImpl<>(content, pageable, count);
    }
}
