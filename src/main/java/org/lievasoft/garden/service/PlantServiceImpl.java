package org.lievasoft.garden.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lievasoft.garden.dao.PlantDao;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.entity.Classification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantDao plantDao;

    @Override
    public void persist(final PlantCreateDto payload) {
        verifyIfCommonNameExists(payload.commonName());
        String generatedUUID = UUID.randomUUID().toString();
        insertPlant(generatedUUID, payload);
        Long plantId = findPlantIdOrElseThrowException(generatedUUID, payload.commonName());
        insertClassifications(plantId, payload.classifications());
    }

    private void verifyIfCommonNameExists(String commonName) {
        boolean exists = plantDao.existsByCommonName(commonName);

        Assert.isTrue(!exists, () -> {
            String errorMessage = String.format("'%s' commonName already exists", commonName);
            log.error(errorMessage);
            return errorMessage;
        });
    }

    private void insertPlant(String uuid, PlantCreateDto dto) {
        int affectedRows = plantDao.insertPlant(uuid, dto);

        Assert.state(affectedRows == 1, () -> {
            String errorMessage = String.format("Plant with uuid '%s' has not been persisted", uuid);
            log.error(errorMessage);
            return errorMessage;
        });
    }

    private Long findPlantIdOrElseThrowException(String uuid, String commonName) {
        return plantDao.findPlantIdByUuidAndCommonName(uuid, commonName)
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Plant has not been founded, searched by uuid: '%s' and commonName: '%s'",
                            uuid,
                            commonName
                    );
                    log.error(errorMessage);
                    return new EntityNotFoundException(errorMessage);
                });
    }

    private void insertClassifications(Long plantId, Set<Classification> classifications) {
        classifications.forEach(classification -> {
            String valueToInsert = classification.name().toLowerCase();
            int affectedRows = plantDao.insertClassification(plantId, valueToInsert);

            Assert.state(affectedRows == 1, () -> {
                String errorMessage = String.format("Classification with value '%s' has not been persisted", valueToInsert);
                log.error(errorMessage);
                return errorMessage;
            });
        });
    }
}
