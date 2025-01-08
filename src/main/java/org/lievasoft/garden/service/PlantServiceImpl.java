package org.lievasoft.garden.service;

import lombok.extern.slf4j.Slf4j;
import org.lievasoft.garden.dto.PlantCreateDto;
import org.lievasoft.garden.dto.PlantResponseDto;
import org.lievasoft.garden.entity.Situation;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class PlantServiceImpl implements PlantService {

    private final JdbcClient jdbcClient;

    public PlantServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public PlantResponseDto persist(final PlantCreateDto payload) {
        var sql = """
                INSERT INTO plants (common_name, scientific_name, situation)
                VALUES (:commonName, :scientificName, cast(:situation AS situation));
                """;


        int result = jdbcClient.sql(sql)
                .param("commonName", payload.commonName())
                .param("scientificName", payload.scientificName())
                .param("situation", payload.situation().getValue())
                .update();

        Assert.state(result == 1, "Plant has not been persisted");
        return null;
    }
}
