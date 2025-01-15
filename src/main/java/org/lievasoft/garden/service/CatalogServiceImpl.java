package org.lievasoft.garden.service;

import org.lievasoft.garden.dao.CatalogDao;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogDao catalogDao;

    public CatalogServiceImpl(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    private final Function<Set<Classification>, Set<String>> convertClassification =
            classifications ->
                    classifications.stream()
                            .map(classification -> classification.name().toLowerCase())
                            .collect(Collectors.toSet());

    private final Function<Situation, String> convertSituation = situation -> situation.name().toLowerCase();

    @Override
    public Page<CardResponseDto> fetchPlantCards(Pageable pageable) {
        return catalogDao.plantCardPage(pageable);
    }

    @Override
    public Page<CardResponseDto> fetchFilteredPlantCards(Pageable pageable, CatalogFilterDto filters) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<CardResponseDto> content;
        long total;

        if (filters.situation() != null && filters.classifications() != null) {
            Set<String> classifications = convertClassification.apply(filters.classifications());
            String situation = convertSituation.apply(filters.situation());
            content = catalogDao.findFilteredPlantCards(limit, offset, classifications, situation);
            total = catalogDao.countFilteredPlantCards(classifications, situation);

        } else {
            if (filters.situation() != null) {
                String situation = convertSituation.apply(filters.situation());
                content = catalogDao.findPlantCardsBySituation(limit, offset, situation);
                total = catalogDao.countPlantCardsBySituation(situation);

            } else {
                Set<String> classifications = convertClassification.apply(filters.classifications());
                content = catalogDao.findPlantCardsByClassifications(limit, offset, classifications);
                total = catalogDao.countPlantCardsByClassifications(classifications);
            }
        }

        return new PageImpl<>(content, pageable, total);
    }
}
