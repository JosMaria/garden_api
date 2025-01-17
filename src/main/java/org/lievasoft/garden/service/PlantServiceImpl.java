package org.lievasoft.garden.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lievasoft.garden.dao.PlantDao;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.entity.Classification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private static final String FOLDER_PATH = "/home/josmaria/nursery/images/";

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

    @Override
    public UUID uploadImageToFileSystem(final Long plantId, final MultipartFile file) {
        if (plantDao.existsById(plantId)) {

        } else {
            String message = String.format("Plant with ID '%s' does not exist", plantId);
            log.info(message);
            throw new EntityExistsException(message);
        }

        return null;
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
