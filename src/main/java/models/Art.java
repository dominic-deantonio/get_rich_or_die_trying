package models;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static models.SceneContainer.readJsonObject;

public class Art {

    private static Map<String, String> artMap = loadArt();

    private static Map<String, String> loadArt() {

        Map<String, String> tempArtMap = new HashMap<>();
        JSONObject fileData = SceneContainer.readJsonObject("scenes/art"  + ".json");

        for(String category: fileData.keySet()) {
            tempArtMap.put(category, fileData.getString(category));
        }
        return tempArtMap;
    }

    public static String getArt(String category) {
        return artMap.get(category);

    }
}
