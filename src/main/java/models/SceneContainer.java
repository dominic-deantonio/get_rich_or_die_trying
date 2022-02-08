package models;

import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class SceneContainer {
    //Fields
    private final Random random = new Random();
    private final List<Map<String, List<Scene>>> categories = new ArrayList<>();

    //Constructors
    public SceneContainer() {
        Map<String, List<Scene>> career = loadScenes("career", "danger", "knowledge", "passion");
        Map<String, List<Scene>> education = loadScenes("education", "true", "false");
        Map<String, List<Scene>> partner = loadScenes("partner", "married", "single", "partner");
        Map<String, List<Scene>> privilege = loadScenes("privilege", "true", "false");
        Map<String, List<Scene>> children = loadScenes("children", "true", "false");
        Map<String, List<Scene>> health = loadScenes("health", "true", "false");

        categories.add(career);
        categories.add(education);
        categories.add(partner);
        categories.add(privilege);
        categories.add(health);
        categories.add(children);
    }

    //Business Methods

    /**
     *
     * @param category Name of category (Using name of file as category name)
     * @param subcategories String name of subcategories
     * @return Map of subcategories (each contain a list of Scene Objects) from each category (Using subcategories as key)
     * returned Map Format:
     *   {
     *              'key'               'value'
     *      'subcategory name' :[ Scene Object, Scene Object, .....],
     *      'subcategory name' : [ Scene Object, Scene Object, .....],
     *      more.........
     *  }
     */
    private Map<String, List<Scene>> loadScenes(String category, String... subcategories) {
        JSONObject fileData = readJsonObject("scenes/" + category + ".json");
        Map<String, List<Scene>> tempMap = new HashMap<>();

        for (String subcategory : subcategories) {
            List<Scene> subCategoryScenes = new ArrayList<>();
            for (Object sceneObject : fileData.getJSONArray(subcategory)) {
                JSONObject definitelyJson = (JSONObject) sceneObject;
                Scene newScene = Scene.fromJson(definitelyJson); //calls method to create Scene Object
                newScene.setCategory(category);
                subCategoryScenes.add(newScene);
            }
            tempMap.put(subcategory, subCategoryScenes);
        }

        return tempMap;
    }

    /**
     * Return JSONObject after reading an external .json file
     * @param path Path of external file
     * @return JSONObject which can then be deconstructe
     */
    public static JSONObject readJsonObject(String path) {
        File file = new File(path);
        StringBuilder jsonString = new StringBuilder();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine())
                jsonString.append(reader.nextLine());

        } catch (Exception e) {
            System.out.println("Error occurred while loading scenes: " + e);
        }

        return new JSONObject(jsonString.toString());
    }

    /**
     * Returns a random Scene object based on random value
     * @param player Using Person object to determine
     * @return Scene object that was randomly selected.
     */
    public Scene getRandomScene(Person player) {

        Scene sceneToUse = null;
        int attemptsToRandomize = 0;
        final int MAX_ATTEMPTS = 100;
        do {
            int categoryIndex = random.nextInt(categories.size()); // Get random number based on size of the categories field
            Map<String, List<Scene>> category = categories.get(categoryIndex); // Get the category of scene to use
            String subCategoryKey = player.getCategoryValue(categoryIndex); // Get the subcategory key (the value of the corresponding player variable)
            List<Scene> subCategoryScenes = category.get(subCategoryKey); // Get the list of scenes based on the subCategoryKey
            int sceneIndex = random.nextInt(subCategoryScenes.size()); // Get a random index based on the subcategory size
            sceneToUse = subCategoryScenes.get(sceneIndex);
            attemptsToRandomize++;

            if (attemptsToRandomize > MAX_ATTEMPTS) {
                //System.out.println("Exceeded max attempts; Unlocking all scenes");
                setAllScenesToUnused(); // We have tried to get an unused scene too many times. Unlock them all, makes them all available
            }

        } while (sceneToUse.hasBeenUsed());

        //System.out.println("Picked random scene after " + attemptsToRandomize + " attempts");

        sceneToUse.setHasBeenUsed(true); // Need to set this to true to make sure it is not used
        return sceneToUse;
    }

    /**
     * Setts all Scene objects 'hasBeenUsed' field to false
     */
    private void setAllScenesToUnused() {
        for (Map<String, List<Scene>> category : categories)
            for (String subcategory : category.keySet())
                for (Scene scene : category.get(subcategory))
                    scene.setHasBeenUsed(false);
    }

}