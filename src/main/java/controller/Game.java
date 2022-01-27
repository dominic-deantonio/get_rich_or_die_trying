package controller;

import models.Careers;
import models.Person;
import models.Scene;
import models.SceneContainer;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Game {
    SceneContainer scenes;
    Person player;
//    String input = "";


    public void execute() {
        scenes = new SceneContainer();
        welcome();
        player = getPlayerBasicData();

        runSceneOneCareer(player);

        while (shouldPlay()) {
            Scene currentScene = scenes.getRandomScene(player);
            int input = prompt(currentScene);
            displayOutcome(input, currentScene);
            runEffect(input, currentScene);
            displaySceneSummary();
            player.addAge(5);
        }
        playAgainOrExit();
    }

    private void playAgainOrExit() {
    }

    private void displaySceneSummary() {
        System.out.println("++++++ 5-Year Summary ++++++");
        System.out.println("Player: " + player.getName());
        System.out.println("Net Worth: " + player.getPrettyNetWorth());
        System.out.println("Children: " + player.getChildren());
        if(player.isMarried()){
            System.out.println("Spouse: " + player.getPartner());
        }else{
            System.out.println("Partner: " + (player.getPartner() == null ? "none" : player.getPartner()));
        }

    }

    private void runEffect(int index, Scene currentScene) {
        EffectsTranslator.doEffects(player, currentScene.effects.get(index));
    }

    private void displayOutcome(int index, Scene currentScene) {
        System.out.println(currentScene.outcomes.get(index));
    }

    private int prompt(Scene currentScene) {
        System.out.println("+++++++ 5 years later +++++++");
        System.out.println(currentScene.prompt);
        for (String option : currentScene.options)
            System.out.println(option);

        String input = getInput();

        // TODO: We have to validate the input
        // TODO: Think about capitalization
        return currentScene.options.indexOf(input.toLowerCase());
    }

    private void runSceneOneCareer(Person player) {

        Map<Careers, List<String>> availCareers = player.hasEducation() ? Careers.getCollegeCareers() : Careers.getNonCollegeCareers();
        String collegeSummary = player.hasEducation() ? "Congratulations!\nYou finished college." : "You decided to skip the college route.";
        System.out.println(collegeSummary);
        System.out.println("What career do you want?");

        for (Careers career : availCareers.keySet()) {
            for (String specialty : availCareers.get(career)) {
                System.out.println(specialty);
            }
        }

        String input = getInput();

        topLoop:
        for (Careers career : availCareers.keySet()) {
            for (String specialty : availCareers.get(career)) {
                if (input.equalsIgnoreCase(specialty)) {
                    player.setCareer(career);
                    break topLoop;
                }
            }
        }

        System.out.println(player.getCareer());

    }

    private String getInput() {
        Scanner playerInput = new Scanner(System.in);
        return playerInput.nextLine();
    }

    private Person getPlayerBasicData() {
        Scanner playerInput = new Scanner(System.in);

        System.out.println("Enter your Name: ");
        String playerName = playerInput.nextLine();

        Scanner playerInput2 = new Scanner(System.in);

        System.out.println("Do you want to go to college ? Y/N: ");
        String educationChoice = playerInput2.nextLine();


        System.out.println("Your name is " + playerName + ". You chose " + educationChoice + "for college. ");

        Person p = new Person();

        if (educationChoice.equalsIgnoreCase("y"))
            p.setEducation(true);


        return p;
    }

    private String welcome() {
        System.out.println("Welcome to Get Rich Or Die Trying.\n At a young age you realize that you want to be a millionaire by 40 years old.\n Your mission is to make $1 million before all your health points run out.\n Each choice you make will affect your net worth and health levels.");
        return "";
    }

    private boolean shouldPlay() {
        if (player.getHealthPoints() <= 0) {
            System.out.println("Game Over. You ran out of health points: " + player.getHealthPoints());
            return false;
        }

        if (player.getNetWorth() >= 1000000) {
            System.out.println("You win. You have: " + player.getPrettyNetWorth());
            return false;
        }

        return true;
    }


}
