package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.entity.Category;
import org.lievasoft.garden.repository.PlantJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final PlantJpaRepository plantJpaRepository;

    public CatalogServiceImpl(PlantJpaRepository plantJpaRepository) {
        this.plantJpaRepository = plantJpaRepository;
    }

    @Override
    public Page<CardResponseDto> fetchPlantCardsByPagination(Pageable pageable, CatalogFilterDto filter) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<CardResponseDto> plantCardsBySituation = plantJpaRepository.findPlantCardsBySituation(limit, offset, filter.situation().name());

//        long situationCount = plantJpaRepository.countPlantCardsBySituation(limit, offset, filter.situation());

        /*
        if (Objects.isNull(filter)) {
            plantCardsObtained = plantJpaRepository.findPlantCards(limit, offset);
        } else {
            if (Objects.nonNull(filter.situation()) && Objects.nonNull(filter.categories())) {
                plantCardsObtained = plantJpaRepository.findPlantCardsBySituationAndCategory(limit, offset, filter.situation().name(), Category.ORNAMENTAL.name());
            } else {
                if (Objects.nonNull(filter.situation())) {
                    plantCardsObtained = plantJpaRepository.findPlantCardsBySituation(limit, offset, filter.situation().name());
                } else {
                    plantCardsObtained = plantJpaRepository.findPlantCardsByCategory(limit, offset, Category.ORNAMENTAL.name());
                }
            }
        }*/

        List<String> categoryNames = filter.categories().stream()
                .map(Category::name)
                .toList();

        long count = plantJpaRepository.countPlantsByCategories(categoryNames);

        List<CardResponseDto> plantCardsObtained = plantJpaRepository.findPlantCardsByCategories(limit, offset, filter.categories());

        return new PageImpl<>(plantCardsObtained, pageable, plantJpaRepository.count());
    }
}
