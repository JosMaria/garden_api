package org.lievasoft.garden.mapper;

import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.springframework.stereotype.Service;

@Service
public class PlantMapper {

    public Plant convertToPlant(final PlantCreateDto payload) {
        Plant plant = new Plant();
        plant.setCommonName(payload.commonName());
        plant.setScientificName(payload.scientificName());
        plant.setStatus(payload.status());
        plant.addCategories(payload.categories());
        return plant;
    }

    public PlantResponseDto convertToPlantResponseDto(final Plant plant) {
        return new PlantResponseDto(plant.getId(), plant.getCommonName(), plant.getScientificName(), plant.getStatus());
    }
}
