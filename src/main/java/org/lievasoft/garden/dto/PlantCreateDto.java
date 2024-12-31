package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Status;

public record PlantCreateDto(
        String commonName,
        String scientificName,
        Status status
) {
}
