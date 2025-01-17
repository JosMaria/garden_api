package org.lievasoft.garden.service;

import org.lievasoft.garden.dto.PlantCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PlantService {

    boolean persist(PlantCreateDto payload);

    UUID uploadImageToFileSystem(Long plantId, MultipartFile file);
}
