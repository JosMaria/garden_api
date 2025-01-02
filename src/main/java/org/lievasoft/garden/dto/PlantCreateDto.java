package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Category;
import org.lievasoft.garden.entity.Status;

import java.util.Set;

public record PlantCreateDto(
        String commonName,
        String scientificName,
        Status status,
        Set<Category> categories
) {
}
