package org.lievasoft.garden.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;

import java.util.Set;

public record PlantResponseDto(
        @JsonIgnore
        Long id,

        String uuid,
        String commonName,
        String scientificName,
        Situation situation,
        Set<Classification> classifications
) {
}
