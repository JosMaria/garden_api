package org.lievasoft.garden.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plant_seq")
    @SequenceGenerator(name = "plant_seq", sequenceName = "plant_sequence", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private int id;

    private String commonName;

    @Setter(AccessLevel.NONE)
    @Version
    private int version;
}
