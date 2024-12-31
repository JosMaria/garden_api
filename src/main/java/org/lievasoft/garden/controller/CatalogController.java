package org.lievasoft.garden.controller;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.service.CatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Page<CardResponseDto>> fetchPlantCardsByPagination(@PageableDefault(size = 12) Pageable pageable) {
        return ResponseEntity.ok(catalogService.fetchPlantCardsByPagination(pageable));
    }
}
