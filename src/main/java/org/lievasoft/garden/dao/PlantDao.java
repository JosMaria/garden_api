package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.PlantCreateDto;

import java.util.Optional;

public interface PlantDao {

    boolean existsByCommonName(String commonName);

    int insertPlant(String uuid, PlantCreateDto dto);

    int insertClassification(Long plantId, String classificationValue);

    Optional<Long> findPlantIdByUuidAndCommonName(String uuid, String commonName);
}
