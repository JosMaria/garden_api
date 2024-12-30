package org.lievasoft.garden.repository;

import java.util.List;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlantJpaRepository extends JpaRepository<Plant, Long> {

    @Query(name = "findAllCardsPaginated", nativeQuery = true)
    List<CardResponseDto> findAllCardsPaginated();
}
