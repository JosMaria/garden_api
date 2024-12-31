package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;

public interface PlantService {

    PlantResponseDto persist(PlantCreateDto payload);
}
