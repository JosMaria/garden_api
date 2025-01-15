package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.CardResponseDto;

import java.util.List;
import java.util.Set;

public interface CatalogDao {

    List<CardResponseDto> findPlantCards(int limit, int offset);

    long countPlantCards();

    List<CardResponseDto> findPlantCardsBySituation(int limit, int offset, String situation);

    long countPlantCardsBySituation(String situation);

    List<CardResponseDto> findPlantCardsByClassifications(int limit, int offset, Set<String> classifications);

    long countPlantCardsByClassifications(Set<String> classifications);

    List<CardResponseDto> findFilteredPlantCards(int limit, int offset, Set<String> classifications, String situation);

    long countFilteredPlantCards(Set<String> classifications, String situation);
}
