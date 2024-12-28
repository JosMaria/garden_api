package org.lievasoft.garden.repository;

import org.lievasoft.garden.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantJpaRepository extends JpaRepository<Plant, Long> {
}
