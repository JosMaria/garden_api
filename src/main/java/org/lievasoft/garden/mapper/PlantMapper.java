package org.lievasoft.garden.mapper;

import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.entity.Plant;
import org.springframework.stereotype.Service;

@Service
public class PlantMapper {

    public Plant convertToPlant(final PlantCreateDto payload) {
        Plant plant = new Plant();
        plant.setCommonName(payload.commonName());
        plant.setScientificName(payload.scientificName());
        plant.setSituation(payload.situation());
        plant.addCategories(payload.classifications());
        return plant;
    }
}
