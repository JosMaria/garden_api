package org.lievasoft.garden.beans;

import org.lievasoft.garden.entity.Category;
import org.lievasoft.garden.entity.Situation;

import java.util.Set;

public record PlantToBuild(
    String commonName,
    String scientificName,
    Situation situation,
    Set<Category> categories
) {}
