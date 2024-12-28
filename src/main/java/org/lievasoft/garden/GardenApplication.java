package org.lievasoft.garden;

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
//            plantRepository.findById(1L)
//                    .ifPresent(plantObtained -> {so
//            });




//            Plant plantOne = new Plant();
//            plantOne.setCommonName("flor de navidad");
//            Plant plantTwo = new Plant();
//            plantTwo.setCommonName("acacia orrida");
//            plantRepository.save(plantOne);
//            plantRepository.save(plantTwo);
        };
    }
}
