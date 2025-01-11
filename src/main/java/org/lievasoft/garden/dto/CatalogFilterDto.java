package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;

import java.util.Set;

public record CatalogFilterDto(
        Set<Classification> categories,
        Situation situation
) {
}
