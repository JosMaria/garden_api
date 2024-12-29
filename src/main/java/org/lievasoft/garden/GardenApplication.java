package org.lievasoft.garden;

import org.lievasoft.garden.entity.Plant;
import org.lievasoft.garden.entity.Status;
import org.lievasoft.garden.repository.PlantJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GardenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GardenApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PlantJpaRepository plantRepository) {
        return args -> {
            Plant plant = new Plant();
            plant.setCommonName("acacia");
            plant.setStatus(Status.ABSENT);
            plant.setScientificName("acacia scientific name");
            plantRepository.save(plant);

            plantRepository.findAllCardsPaginated();
            
//            plantRepository.findById(1L)
//                    .ifPresent(plantObtained -> {so
//            });(




//            Plant plantOne = new Plant();
//            plantOne.setCommonName("flor de navidad");
//            Plant plantTwo = new Plant();
//            plantTwo.setCommonName("acacia orrida");
//            plantRepository.save(plantOne);
//            plantRepository.save(plantTwo);
        };
    }
}
