package org.lievasoft.garden.dao;

import lombok.RequiredArgsConstructor;
import org.lievasoft.garden.entity.Image;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImageDataAccess implements ImageDao {

    private final JdbcClient jdbcClient;
    private final KeyHolder keyHolder;

    @Override
    public boolean existsImageByPlantId(long plantId) {
        var statement = """
                SELECT exists (
                    SELECT id
                    FROM images
                    WHERE plant_id = :plantId
                )
                """;

        return jdbcClient.sql(statement)
                .param("plantId", plantId)
                .query((resultSet, rowNum) -> resultSet.getBoolean("exists"))
                .single();
    }

    @Override
    public String insertImageByPlantId(Long plantId, Image imageId) {
        var statement = """
                INSERT INTO images(plant_id, name, type, path, favorite)
                VALUES (:plantId, :name, :type, :path, :favorite)
                """;

        int updatedRows = jdbcClient.sql(statement)
                .param("plantId", plantId)
                .param("name", imageId.getName())
                .param("type", imageId.getType())
                .param("path", imageId.getPath())
                .param("favorite", imageId.isFavorite())
                .update(keyHolder, "id");

        Assert.state(updatedRows > 0, "Failed to insert image");

        return Optional.ofNullable(keyHolder.getKeys())
                .orElseThrow(() -> new IllegalArgumentException("No keys returned from insert"))
                .get("id")
                .toString();
    }
}
