package org.lievasoft.garden.entity;

import jakarta.persistence.*;
import org.lievasoft.garden.dto.CardResponseDto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findPlantCards",
                query = """
                    SELECT id, common_name, situation
                    FROM plants
                    LIMIT :limit
                    OFFSET :offset
                """,
                resultSetMapping = "PlantCardPageMapped"
        ),
        @NamedNativeQuery(
                name = "findPlantCardsBySituationAndCategories",
                query = """
                    SELECT id, common_name, situation
                    FROM plants p
                    LEFT JOIN categories c
                        ON p.id = c.plant_id
                    WHERE c.name = :categoryName AND p.situation = :situation
                    LIMIT :limit
                    OFFSET :offset
                """,
                resultSetMapping = "PlantCardPageMapped"
        ),
        @NamedNativeQuery(
                name = "findPlantCardsBySituation",
                query = """
                    SELECT id, common_name, situation
                    FROM plants
                    WHERE situation = :situation
                """,
                resultSetMapping = "PlantCardPageMapped"
        ),
        @NamedNativeQuery(
                name = "findPlantCardsByCategories",
                query = """
                    SELECT id, common_name, situation
                    FROM plants
                    JOIN (
                        SELECT DISTINCT plant_id
                        FROM categories
                        WHERE name IN :categories
                    ) f
                        ON id = f.plant_id
                    LIMIT :limit
                    OFFSET :offset
                """,
                resultSetMapping = "PlantCardPageMapped"
        )
})
@SqlResultSetMapping(
        name = "PlantCardPageMapped",
        classes = {
                @ConstructorResult(
                        targetClass = CardResponseDto.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "common_name", type = String.class),
                                @ColumnResult(name = "situation", type = Situation.class),
                        }
                )
        }
)
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

    @Version
    @Transient
    private int version;

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

    public int getVersion() {
        return version;
    }

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public void addCategories(Collection<? extends Classification> categories) {
        this.classifications.addAll(categories);
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

    public Plant() {
    }
}
