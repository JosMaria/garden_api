package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;

import java.util.Set;

public record PlantResponseDto(
        String uuid,
        String commonName,
        String scientificName,
        Situation situation,
        Set<Classification> classifications
) {
}
