package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Backstory {

    private final String prompt;
    private final List<BackstoryOption> options;

    private Backstory(String prompt, List<BackstoryOption> options) {
        this.prompt = prompt;
        this.options = options;
    }

    public static Backstory fromJson(JSONObject json) {

        // Parse the options from the JSON into the correct datatype
        List<BackstoryOption> options = new ArrayList<>();
        JSONArray optionsJson = json.getJSONArray("options");
        for (Object optionJson : optionsJson) {
            JSONObject parsedOption = (JSONObject) optionJson;
            BackstoryOption bo = new BackstoryOption(
                    parsedOption.getString("text"),
                    parsedOption.getString("attribute"),
                    parsedOption.getString("outcome")
            );
            options.add(bo);
        }

        String prompt = (String) json.get("prompt");

        // Use the parsed data to construct the object
        return new Backstory(prompt, options);
    }

    public List<BackstoryOption> getOptions() {
        return options;
    }

    public List<String> getBackstoryOptionsText(){
        List<String> optionsText = new ArrayList<>();
        for(BackstoryOption o : options)
            optionsText.add(o.getText());

        return optionsText;
    }

    public String getPrompt() {
        return prompt;
    }
}
