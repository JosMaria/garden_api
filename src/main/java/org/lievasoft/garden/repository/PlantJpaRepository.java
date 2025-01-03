package org.lievasoft.garden.repository;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlantJpaRepository extends JpaRepository<Plant, Long> {

    @Query(name = "findPlantCards", nativeQuery = true)
    List<CardResponseDto> findPlantCards(@Param("limit") int limit, @Param("offset") int offset);

    @Query(name = "findPlantCardsBySituationAndCategories", nativeQuery = true)
    List<CardResponseDto> findPlantCardsBySituationAndCategory(
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("situation") String situation,
            @Param("categoryName") String category
    );

    @Query(name = "findPlantCardsBySituation", nativeQuery = true)
    List<CardResponseDto> findPlantCardsBySituation(
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("situation") String situation
    );

    @Query(name = "findPlantCardsByCategories", nativeQuery = true)
    List<CardResponseDto> findPlantCardsByCategory(
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("categoryName") String category
    );
}
