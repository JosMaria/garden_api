package org.lievasoft.garden.dao;

import org.lievasoft.garden.dto.ClassificationToPersist;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.dto.PlantToPersist;

import java.util.Optional;

public interface PlantDao {

    boolean existsByCommonName(String commonName);

    int insertPlant(PlantToPersist dto);

    int insertClassification(ClassificationToPersist dto);

    Optional<PlantResponseDto> findByUUID(String uuid);
}
