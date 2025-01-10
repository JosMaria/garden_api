package org.lievasoft.garden.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.lievasoft.garden.entity.Classification;
import org.lievasoft.garden.entity.Situation;

import java.util.Set;

public class PlantResponseDto {

    @JsonIgnore
    private Long id;

    private String uuid;
    private String commonName;
    private String scientificName;
    private Situation situation;
    private Set<Classification> classifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(Set<Classification> classifications) {
        this.classifications = classifications;
    }
}
