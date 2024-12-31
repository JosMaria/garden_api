package org.lievasoft.garden.service;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.repository.PlantJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final PlantJpaRepository plantJpaRepository;

    public CatalogServiceImpl(PlantJpaRepository plantJpaRepository) {
        this.plantJpaRepository = plantJpaRepository;
    }

    @Override
    public Page<CardResponseDto> fetchPlantCardsByPagination(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;
        List<CardResponseDto> plantCardsObtained = plantJpaRepository.findPlantCardsByPagination(limit, offset);
        return new PageImpl<>(plantCardsObtained, pageable, plantJpaRepository.count());
    }
}
