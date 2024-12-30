package org.lievasoft.garden.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lievasoft.garden.beans.PlantToBuild;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.lievasoft.garden.mockdata.MockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlantJpaRepositoryTest {
    
    @Autowired
    private PlantJpaRepository underTest;

    @AfterEach
    void teardown() {
        underTest.deleteAll();
    }

    @Test
    @DisplayName("Should return empty list when no cards exist")
    public void shouldReturnEmptyList_WhenNoCardsExist() {
        List<CardResponseDto> expected = underTest.findAllCardsPaginated();
        assertThat(expected).isEmpty();
    }

    @Test
    public void shouldReturnList_WhenCardExist() {
        List<PlantToBuild> plantsToConvert = MockData.getPlantCards();
        
        List<Plant> plantsToPersist = 
            plantsToConvert.stream()
                    .map(plantToConvert -> 
                        Plant.builder()
                            .commonName(plantToConvert.commonName())
                            .scientificName(plantToConvert.scientificName())
                            .status(plantToConvert.status()).build()
                    ).toList();
        
        underTest.saveAll(plantsToPersist);
        
        List<CardResponseDto> expected = underTest.findAllCardsPaginated();
        
        assertThat(expected).isNotEmpty();
        assertThat(expected).hasSize(31);
    }
}
