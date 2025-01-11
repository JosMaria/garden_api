package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.PlantCreateDto;

public interface PlantService {

    void persist(PlantCreateDto payload);
}
