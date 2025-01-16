package org.lievasoft.garden.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plant_seq")
    @SequenceGenerator(name = "plant_seq", sequenceName = "plant_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 50)
    private String uuid;

    @Column(unique = true, nullable = false, length = 50)
    private String commonName;

    @Column(length = 50)
    private String scientificName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Situation situation;

    @ElementCollection
    @CollectionTable(
            name = "classifications",
            joinColumns = @JoinColumn(name = "plant_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "value", nullable = false)
    private final Set<Classification> classifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public Situation getSituation() {
        return situation;
    }

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }
}
