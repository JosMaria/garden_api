package org.lievasoft.garden.service;

import jakarta.persistence.EntityNotFoundException;
import org.lievasoft.garden.dao.PlantDao;
import org.lievasoft.garden.dto.ClassificationToPersist;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.dto.PlantToPersist;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantDao plantDao;

    public PlantServiceImpl(PlantDao plantDao) {
        this.plantDao = plantDao;
    }

    @Override
    public PlantResponseDto persist(final PlantCreateDto payload) {
        boolean exists = plantDao.existsByCommonName(payload.commonName());
        Assert.isTrue(!exists, "Plant with commonName " + payload.commonName() + " already exists");

        String generatedUUID = UUID.randomUUID().toString();
        PlantToPersist plantToPersist = new PlantToPersist(
                generatedUUID,
                payload.commonName(),
                payload.scientificName(),
                payload.situation().name().toLowerCase()
        );

        int result = plantDao.insertPlant(plantToPersist);
        Assert.state(result == 1, "Plant: " + payload.commonName() + " has not been persisted");

        PlantResponseDto responseDto = plantDao.findByUUID(generatedUUID)
                .orElseThrow(() -> new EntityNotFoundException("Plant with uuid " + generatedUUID + " not found"));

        payload.classifications().forEach(classification -> {
            String value = classification.name().toLowerCase();
            int countAffected = plantDao.insertClassification(new ClassificationToPersist(responseDto.getId(), value));
            Assert.state(countAffected == 1, "Classification: " + value + " has not been persisted");
        });

        responseDto.setClassifications(payload.classifications());
        return responseDto;
    }
}
