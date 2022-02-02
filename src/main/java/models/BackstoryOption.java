package models;

public class BackstoryOption {
    private final String text;
    private final String attribute;
    private final String outcome;

    BackstoryOption(String text, String attribute, String outcome) {
        this.text = text;
        this.attribute = attribute;
        this.outcome = outcome;
    }

    public String getText() {
        return text;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getOutcome() {
        return outcome;
    }


}