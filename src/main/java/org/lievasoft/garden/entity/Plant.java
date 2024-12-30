package org.lievasoft.garden.entity;

import org.lievasoft.garden.dto.CardResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Setter;

@NamedNativeQuery(
        name = "findAllCardsPaginated",
        query = """
            SELECT id, common_name, status
            FROM plants
        """,
        resultSetMapping = "CardPageMapped"
)
@SqlResultSetMapping(
        name = "CardPageMapped",
        classes = {
                @ConstructorResult(
                        targetClass = CardResponseDto.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "common_name", type = String.class),
                                @ColumnResult(name = "status", type = Status.class),
                        }
                )
        }
)
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
    private Status status;

    @Setter(AccessLevel.NONE)
    @Version
    private int version;

    @Builder
    public Plant(String commonName, String scientificName, Status status) {
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.status = status;
    }
}
