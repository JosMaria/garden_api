package org.lievasoft.garden.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plant_seq")
    @SequenceGenerator(name = "plant_seq", sequenceName = "plant_sequence", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String commonName;

    @Column(length = 50)
    private String scientificName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Situation situation;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "classifications", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "value", nullable = false)
    private final Set<Classification> classifications = new HashSet<>();
}
