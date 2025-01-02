package org.lievasoft.garden;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.lievasoft.garden.entity.Category;
import org.lievasoft.garden.entity.Plant;
import org.lievasoft.garden.entity.Situation;
import org.lievasoft.garden.repository.PlantJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class GardenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GardenApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PlantJpaRepository plantRepository) {

        return args -> {
            InputStream inputStream = Resources.getResource("plantCards.json").openStream();
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Type listType = new TypeToken<ArrayList<PlantToBuild>>() {}.getType();
            List<PlantToBuild> plantsToConvert = new Gson().fromJson(json, listType);

            List<Plant> plantsToPersist = plantsToConvert.stream()
                    .map(plantToConvert -> {
                        Plant plant = new Plant();
                        plant.setCommonName(plantToConvert.commonName());
                        plant.setScientificName(plantToConvert.scientificName());
                        plant.setSituation(plantToConvert.situation());
                        plant.addCategories(plantToConvert.categories());
                        return plant;
                    }).toList();
            plantRepository.saveAll(plantsToPersist);
        };
    }
}

record PlantToBuild(
        String commonName,
        String scientificName,
        Situation situation,
        Set<Category> categories
) {
}
