package org.lievasoft.garden.beans;

import org.lievasoft.garden.entity.Status;

public record PlantToBuild(
    String commonName,
    String scientificName,
    Status status
) {}
