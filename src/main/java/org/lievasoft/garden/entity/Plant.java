package org.lievasoft.garden.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

@Setter
@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plant_seq")
    @SequenceGenerator(name = "plant_seq", sequenceName = "plant_sequence", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String commonName;

    @Column(length = 50)
    private String  scientificName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter(AccessLevel.NONE)
    @Version
    private int version;
}
