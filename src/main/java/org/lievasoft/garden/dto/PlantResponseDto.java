package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Status;

public record PlantResponseDto(
        Long id,
        String commonName,
        String scientificName,
        Status status
) {
}
