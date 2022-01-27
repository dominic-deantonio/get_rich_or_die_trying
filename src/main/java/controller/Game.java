package controller;

import models.Person;
import models.Scene;
import models.SceneContainer;

public class Game {
    SceneContainer scenes;
    Person player;


    public void execute(){
        scenes = new SceneContainer();
        welcome();
        player = getPlayerBasicData();

        runSceneOneCareer(player);

        while(shouldPlay()) {
            Scene currentScene = scenes.getRandomScene(player);
            int input = prompt(currentScene);
            displayOutcome(input, currentScene);
            runEffect(input, currentScene);
            displaySceneSummary();
        }
        playAgainOrExit();
    }

    private void playAgainOrExit() {
    }

    private void displaySceneSummary() {
    }

    private void runEffect(int input, Scene currentScene) {
    }

    private void displayOutcome(int input, Scene currentScene) {
    }

    private int prompt(Scene currentScene) {
        System.out.println(currentScene.prompt);
        return 0;
    }

    private void runSceneOneCareer(Person player) {
        if(player.hasEducation()) {
            System.out.println("You finished college. What do you want to do?");
        } else {
            System.out.println("You didn't go to college. Pick your career.");
        }
    }

    private Person getPlayerBasicData() {
        return new Person();
    }

    public static String welcome() {
        System.out.println("Welcome to Get Rich Or Die Trying.\n At a young age you realize that you want to be a millionaire by 40 years old.\n Your mission is to make $1 million before all your health points run out.\n Each choice you make will affect your net worth and health levels.");
        return "";
    }

    private boolean shouldPlay() {
        if(player.getHealthPoints() <= 0) {
            System.out.println("Game Over. You ran out of health points: " + player.getHealthPoints());
            return false;
        }

        if(player.getNetWorth() >= 1000000) {
            System.out.println("You win. You have: " + player.getPrettyNetWorth());
            return false;
        }

        return true;
    }


}
