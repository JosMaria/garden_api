package org.lievasoft.garden.entity;

import jakarta.persistence.*;
import lombok.*;
import org.lievasoft.garden.dto.CardResponseDto;

@NamedNativeQuery(
        name = "findPlantCardsByPagination",
        query = """
            SELECT id, common_name, status
            FROM plants
            LIMIT :limit
            OFFSET :offset
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
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
