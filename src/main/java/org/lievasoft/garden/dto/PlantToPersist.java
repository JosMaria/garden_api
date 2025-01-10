package org.lievasoft.garden.dto;

public record PlantToPersist(
        String uuid,
        String commonName,
        String scientificName,
        String situation
) {
}
