package org.lievasoft.garden.mockdata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.lievasoft.garden.beans.PlantToBuild;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class MockData {
    
    public static List<PlantToBuild> getPlantCards() {
        try {
            InputStream inputStream = Resources.getResource("plantCards.json").openStream();
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Type listType = new TypeToken<ArrayList<PlantToBuild>>() {}.getType();
            return new Gson().fromJson(json, listType);    
        
        } catch (IOException e) {
            System.out.println(e);
            return Collections.emptyList();
        }
    }
}
