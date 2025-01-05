package org.lievasoft.garden.repository;

import org.lievasoft.garden.dto.CardResponseDto;
import org.lievasoft.garden.entity.Category;
import org.lievasoft.garden.entity.Plant;
import org.lievasoft.garden.entity.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
//
//    @Query(name = """
//            select count(*)
//            from plants
//            where situation = :situation
//            """, nativeQuery = true)
//    Long countPlantCardsBySituation(
//            @Param("limit") int limit,
//            @Param("offset") int offset,
//            @Param("situation") Situation situation
//    );

    @Query(name = "findPlantCardsByCategories", nativeQuery = true)
    List<CardResponseDto> findPlantCardsByCategories(
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("categories") Set<Category> categories
    );

    @Query(name = """
                SELECT COUNT(p)
                FROM Plant p
                WHERE p.categories.name IN :categoriesNames
            """)
    Long countPlantsByCategories(@Param("categoryNames") Collection<String> categoryNames);
}


/*
* name = """
                SELECT COUNT(*)
                FROM (
                     SELECT DISTINCT plant_id
                     FROM categories
                     WHERE name IN (:categoryNames)
                ) a
            """,
            nativeQuery = true
* */