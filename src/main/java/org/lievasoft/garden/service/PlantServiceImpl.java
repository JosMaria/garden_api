package org.lievasoft.garden.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lievasoft.garden.dao.PlantDao;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.entity.Classification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantDao plantDao;

    @Override
    public boolean persist(final PlantCreateDto payload) {
        if (plantDao.existsByCommonName(payload.commonName())) {
            String errorMessage = String.format("'%s' commonName already exists", payload.commonName());
            log.error(errorMessage);
            throw new EntityExistsException(errorMessage);
        }

        long persistedPlantId = plantDao.insertPlant(payload);
        insertClassifications(persistedPlantId, payload.classifications());
        return true;
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
