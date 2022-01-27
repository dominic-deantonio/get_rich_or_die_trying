package models;

import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class SceneContainer {

    Map<Boolean, List<Scene>> education = new HashMap<>();
    Map<Boolean, List<Scene>> partner = new HashMap<>();
    Map<Boolean, List<Scene>> privilege = new HashMap<>();
    Map<Boolean, List<Scene>> children = new HashMap<>();
    Map<String, List<Scene>> career = new HashMap<>();
    Map<String, List<Scene>> health = new HashMap<>();
    Map<String, List<Scene>> hobbies = new HashMap<>();

    public SceneContainer() {
        career = getStringScenes("career", "danger", "knowledge", "passion");
        education = getBooleanScenes("education");
    }

    private Map<String, List<Scene>> getStringScenes(String category, String... subcategories) {
        JSONObject fileData = readJsonObject("scenes/" + category + ".json");
        List<Scene> categoryScenes = new ArrayList<>();

        Map<String, List<Scene>> tempMap = new HashMap<>();

        for (String subcategory : subcategories) {
            for (Object sceneObject : fileData.getJSONArray(subcategory)) {
                JSONObject definitelyJson = (JSONObject) sceneObject;
                Scene newScene = Scene.fromJson(definitelyJson);
                categoryScenes.add(newScene);
            }
            tempMap.put(subcategory, categoryScenes);
        }

        return tempMap;
    }

    private Map<Boolean, List<Scene>> getBooleanScenes(String category) {
        JSONObject fileData = readJsonObject("scenes/" + category + ".json");
        List<Scene> categoryScenes = new ArrayList<>();

        Map<Boolean, List<Scene>> tempMap = new HashMap<>();

        Boolean[] subcategories = new Boolean[]{true, false};

        for (Boolean subcategory : subcategories) {
            for (Object sceneObject : fileData.getJSONArray(subcategory.toString())) {
                JSONObject definitelyJson = (JSONObject) sceneObject;
                Scene newScene = Scene.fromJson(definitelyJson);
                categoryScenes.add(newScene);
            }
            tempMap.put(subcategory, categoryScenes);
        }

        return tempMap;
    }

    private static JSONObject readJsonObject(String path) {
        File file = new File(path);
        StringBuilder jsonString = new StringBuilder("");
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine())
                jsonString.append(reader.nextLine());

        } catch (Exception e) {
            System.out.println(e);
        }

        return new JSONObject(jsonString.toString());
    }

    public Scene getRandomScene(Person player) {
        return null;
    }
}