package models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scene {

    public String prompt;
    public List<String> options;
    public List<String> outcomes;
    public List<Map<String, Object>> effects;

    public Scene(String prompt, List<String> options, List<String> outcomes, List<Map<String, Object>> effects) {
        this.prompt = prompt;
        this.options = options;
        this.outcomes = outcomes;
        this.effects = effects;
    }

    public static Scene fromJson(JSONObject json) {
        List<String> options = new ArrayList<>();
        for (Object option : json.getJSONArray("options"))
            options.add(option.toString());

        List<String> outcomes = new ArrayList<>();
        for (Object outcome : json.getJSONArray("outcomes"))
            outcomes.add(outcome.toString());

        List<Map<String, Object>> effects = new ArrayList<>();
        for (Object effect : json.getJSONArray("effects"))
            effects.add(((JSONObject) effect).toMap());

        return new Scene(json.getString("prompt"), options, outcomes, effects);
    }
}
