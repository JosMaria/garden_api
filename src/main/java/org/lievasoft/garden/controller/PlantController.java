package org.lievasoft.garden.controller;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plant")
public class PlantController {

    private final PlantService plantService;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody PlantCreateDto payload) {
        return new ResponseEntity<>(plantService.persist(payload), HttpStatus.CREATED);
    }
}
