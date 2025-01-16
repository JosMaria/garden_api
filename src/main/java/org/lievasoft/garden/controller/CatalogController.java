package org.lievasoft.garden.controller;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.dto.CatalogFilterDto;
import org.lievasoft.garden.service.CatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<Page<CardResponseDto>> fetchPlantCards(
            @PageableDefault Pageable pageable,
            @RequestBody(required = false) CatalogFilterDto filters
    ) {
        return ResponseEntity.ok(catalogService.fetchPlantCards(pageable, filters));
    }
}
