package org.lievasoft.garden.repository;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.lievasoft.garden.beans.PlantToBuild;
import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlantJpaRepositoryTest {
    
    private static final int PAGE_LIMIT = 12;

    @Autowired
    private PlantJpaRepository underTest;

    @BeforeAll
    void initAll() {
        try {
            InputStream inputStream = Resources.getResource("plantCards.json").openStream();
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Type listType = new TypeToken<ArrayList<PlantToBuild>>() {}.getType();
            List<PlantToBuild> plantsToConvert = new Gson().fromJson(json, listType);    
            
            List<Plant> plantsToPersist = plantsToConvert.stream()
                    .map(plantToConvert -> 
                        Plant.builder()
                            .commonName(plantToConvert.commonName())
                            .scientificName(plantToConvert.scientificName())
                            .status(plantToConvert.status())
                            .build()
                    ).toList();
            underTest.saveAll(plantsToPersist);
        
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should return all plant cards to single page")
    void shouldReturnAllPlantCardsToSinglePage_WithFirstOffset() {
        int offset = 0;
        String commonNameOfFirstPlantCard = "flor de navidad";
        String commonNameOfLastPlantCard = "aspidastra";

        List<CardResponseDto> actual = underTest.findPlantCardsByPagination(PAGE_LIMIT, offset);
        
        assertAll("plant cards to first single page", 
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual).isNotEmpty(),
            () -> assertThat(actual).hasSize(12),
            () -> assertThat(actual).first()
                    .extracting(CardResponseDto::commonName)
                    .isEqualTo(commonNameOfFirstPlantCard),
            () -> assertThat(actual).last()
                    .extracting(CardResponseDto::commonName)
                    .isEqualTo(commonNameOfLastPlantCard)
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should return empty list when no cards exist")
    void shouldReturnEmptyList_WhenNoCardsExist() {
        int offset = 0;

        underTest.deleteAll();
        List<CardResponseDto> actual = underTest.findPlantCardsByPagination(PAGE_LIMIT, offset);
        
        assertAll("empty list", 
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual).isEmpty()
        );
    }
}
