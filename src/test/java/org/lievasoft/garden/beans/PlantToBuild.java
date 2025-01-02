package org.lievasoft.garden.beans;

import org.lievasoft.garden.entity.Situation;

public record PlantToBuild(
    String commonName,
    String scientificName,
    Situation situation
) {}
