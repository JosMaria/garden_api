package org.lievasoft.garden.repository;

import java.util.List;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlantJpaRepository extends JpaRepository<Plant, Long> {

    @Query(name = "findPlantCardsByPagination", nativeQuery = true)
    List<CardResponseDto> findPlantCardsByPagination(@Param("limit") int limit, @Param("offset") int offset);
}
