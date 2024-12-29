package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Status;

public record CardResponseDto(
        Long id,
        String commonName,
        Status status
) {
}
