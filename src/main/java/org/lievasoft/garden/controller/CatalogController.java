package org.lievasoft.garden.controller;

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
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<Page<CardResponseDto>> fetchPlantCards(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(catalogService.fetchPlantCards(pageable));
    }

    @GetMapping("filter")
    public ResponseEntity<Page<CardResponseDto>> fetchFilteredPlantCards(
            @PageableDefault Pageable pageable,
            @RequestBody CatalogFilterDto filters
    ) {
        return ResponseEntity.ok(catalogService.fetchFilteredPlantCards(pageable, filters));
    }
}
