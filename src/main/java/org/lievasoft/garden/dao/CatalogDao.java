package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CatalogDao {

    Page<CardResponseDto> plantCardPage(Pageable pageable);

    List<CardResponseDto> findPlantCardsBySituation(int limit, int offset, String situation);

    long countPlantCardsBySituation(String situation);

    List<CardResponseDto> findPlantCardsByClassifications(int limit, int offset, Set<String> classifications);

    long countPlantCardsByClassifications(Set<String> classifications);

    List<CardResponseDto> findFilteredPlantCards(int limit, int offset, Set<String> classifications, String situation);

    long countFilteredPlantCards(Set<String> classifications, String situation);
}
