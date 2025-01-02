package org.lievasoft.garden.dto;

import org.lievasoft.garden.entity.Category;
import org.lievasoft.garden.entity.Situation;

import java.util.Set;

public record CatalogFilterDto(
        Set<Category> categories,
        Situation situation
) {
}
