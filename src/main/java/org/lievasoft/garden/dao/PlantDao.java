package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.PlantCreateDto;

public interface PlantDao {

    boolean existsByCommonName(String commonName);

    long insertPlant(PlantCreateDto dto);

    int insertClassification(Long plantId, String classificationValue);
}
