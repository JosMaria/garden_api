package org.lievasoft.garden.service;

import lombok.extern.slf4j.Slf4j;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.lievasoft.garden.mapper.PlantMapper;
import org.lievasoft.garden.repository.PlantJpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlantServiceImpl implements PlantService {

    private final PlantJpaRepository plantJpaRepository;
    private final PlantMapper plantMapper;

    public PlantServiceImpl(PlantJpaRepository plantJpaRepository, PlantMapper plantMapper) {
        this.plantJpaRepository = plantJpaRepository;
        this.plantMapper = plantMapper;
    }

    @Override
    public PlantResponseDto persist(final PlantCreateDto payload) {
        Plant plantToPersist = plantMapper.convertToPlant(payload);
        Plant plantPersisted = plantJpaRepository.save(plantToPersist);
        System.out.printf("Plant persisted with ID: %s%n", plantPersisted.getId());
        return plantMapper.convertToPlantResponseDto(plantPersisted);
    }
}
