package org.lievasoft.garden.dao;

import org.lievasoft.garden.entity.Image;

public interface ImageDao {

    boolean existsImageByPlantId(long plantId);

    String insertImageByPlantId(Long plantId, Image imageId);
}
