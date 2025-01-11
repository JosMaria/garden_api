package org.lievasoft.garden.controller;

import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plant")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PlantCreateDto payload) {
        // TODO: add more information to the header
        plantService.persist(payload);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
