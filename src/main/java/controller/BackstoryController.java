package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Backstory;

import java.util.List;

public class BackstoryController {

    public BackstoryController() {
    }

    @FXML
    private Label backstoryLabel1;

    @FXML
    private Label backstoryLabel2;


    @FXML
    private ToggleButton backstoryButton1;

    @FXML
    private ToggleButton backstoryButton2;

    @FXML
    private ToggleButton backstoryButton3;

    @FXML
    private ToggleGroup backstoryChoice;

    @FXML
    private Button backstorySubmit;

    @FXML
    private void BSSubmitPressed() {
        ToggleButton selected = (ToggleButton) backstoryChoice.getSelectedToggle();

    }

    void startBackstory(int round) {
        List<Backstory> backstories = Game.getBackStoryScenes();
        String labelText = backstories.get(round).getPrompt();
        String button1Text = backstories.get(round).getOptions().get(0).getText();
        String button2Text = backstories.get(round).getOptions().get(1).getText();
        String button3Text = backstories.get(round).getOptions().get(2).getText();
        backstoryLabel1.setText(labelText);
        backstoryButton1.setText(button1Text);
        backstoryButton2.setText(button2Text);
        backstoryButton3.setText(button3Text);
//        for (Backstory story: backstories) {
//            backstoryLabel1.setText(story.getPrompt());
//            int counter = 1;
//            for (BackstoryOption option: story.getOptions()) {
//                if (counter == 1){
//                    backstoryButton1.setText(option.getText());
//                    counter++;
//                } else if (counter == 2){
//                    backstoryButton2.setText(option.getText());
//                    counter++;
//                } else {
//                    backstoryButton3.setText(option.getText());
//                    counter = 1;
//                }
//            }
//            String resp = BSSubmitPressed();
//            BackstoryOption optionSelected = null;
//            for (BackstoryOption option : story.getOptions()) {
//                if (option.getText().contains(resp)) {
//                    optionSelected = option;
//                    break;
//                }
//            }
//            backstoryLabel2.setText(optionSelected.getOutcome());
//            EffectsTranslator.getAttribute(player, optionSelected.getAttribute());

        //}
    }

}