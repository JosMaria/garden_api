package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Situation;

public record CardResponseDto(
        Long id,
        String commonName,
        Situation situation
) {
}
