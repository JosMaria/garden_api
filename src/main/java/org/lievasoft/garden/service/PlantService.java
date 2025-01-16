package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.PlantCreateDto;

public interface PlantService {

    boolean persist(PlantCreateDto payload);
}
