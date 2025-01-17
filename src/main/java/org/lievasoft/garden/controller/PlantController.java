package org.lievasoft.garden.controller;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plant")
public class PlantController {

    private final PlantService plantService;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody PlantCreateDto payload) {
        return new ResponseEntity<>(plantService.persist(payload), CREATED);
    }

    @PostMapping(value = "/{plantId}/images", consumes = {"multipart/form-data"})
    public ResponseEntity<UUID> uploadImage(@PathVariable("plantId") Long plantId, @RequestPart("image") MultipartFile file) {
        return new ResponseEntity<>(plantService.uploadImageToFileSystem(plantId, file), CREATED);
    }
}
