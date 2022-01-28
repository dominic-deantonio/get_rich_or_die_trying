package models;

import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class SceneContainer {

    private final Random random = new Random();

    Map<String, List<Scene>> education;
    Map<String, List<Scene>> partner;
    Map<String, List<Scene>> privilege;
    Map<String, List<Scene>> children;
    Map<String, List<Scene>> career;
    Map<String, List<Scene>> health;
    List<Map<String, List<Scene>>> categories = new ArrayList<>();

    public SceneContainer() {
        career = loadScenes("career", "danger", "knowledge", "passion");
        education = loadScenes("education", "true", "false");
        partner = loadScenes("partner", "true", "false");
        privilege = loadScenes("privilege", "true", "false");
        children = loadScenes("children", "true", "false");
        health = loadScenes("health", "true", "false");

        categories.add(career);
        categories.add(education);
        categories.add(partner);
        categories.add(privilege);
        categories.add(health);
        categories.add(children);
    }

    private Map<String, List<Scene>> loadScenes(String category, String... subcategories) {
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
        int categoryIndex = random.nextInt(categories.size()); // Get random number based on size of the categories
        Map<String, List<Scene>> category = categories.get(categoryIndex); // Get the category of scenes (this is one of the vars defined above)
        String subCategoryKey = player.getCategoryValue(categoryIndex); // Get the subcategory key (the value of the corresponding player variable)
        List<Scene> subCategoryScenes = category.get(subCategoryKey); // Get the list of scenes based on the subCategoryKey
        int sceneIndex = random.nextInt(subCategoryScenes.size()); // Get a random index based on the subcategory size
        return subCategoryScenes.get(sceneIndex); // Get and return the randomly selected scene
    }
}