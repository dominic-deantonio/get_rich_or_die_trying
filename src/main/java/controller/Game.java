package controller;

import models.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class Game {
    SceneContainer scenes;
    Person player = new Person();
    boolean isWindows = System.getProperty("os.name").contains("Windows");

    public void execute() {

        scenes = new SceneContainer();
        welcome();
        getPlayerBasicData();
        clearScreen();
        runSceneOneCareer(player);

        while (shouldPlay()) {
            clearScreen();
            Scene currentScene = scenes.getRandomScene(player);
            System.out.println("+++++++ 5 years later +++++++");
            player.addAge(5);
            int input = prompt(currentScene);
            clearScreen();
            displayOutcome(input, currentScene);
            runEffect(input, currentScene);
            player.addSalary();
            displaySceneSummary();
            nextTurnPrompt();
        }
        playAgainOrExit();
    }

    private void nextTurnPrompt() {
        System.out.println("\nEnter any key to continue or type 'save' to save the game. Or 'quit' to end the game.");
        String askToSave = getInput();

        if (askToSave.equalsIgnoreCase("quit")) {
            System.out.println("Quitting game");
            System.exit(1);
        }

        if (askToSave.equalsIgnoreCase("save")) {
            WriteFile saveGame = new WriteFile("saveFile.txt", displaySceneSummary());
            saveGame.save();
        }
    }

    private void playAgainOrExit() {
    }

    public void clearScreen() {
        try {
            if (isWindows) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception ignored) {
            // Failed to clear the screen. Not much we can do about that.
        }
    }

    private String displaySceneSummary() {
        String values = "";
        System.out.println("\n++++++ 5-Year Summary ++++++");
        System.out.println("Player: " + player.getName());
        System.out.println("Net Worth: " + player.getPrettyNetWorth());
        System.out.println("Health: " + player.getHealthPoints());
        System.out.println("Children: " + player.getChildren());
        if (player.isMarried()) {
            System.out.println("Spouse: " + player.getPartner());
        } else {
            System.out.println("Partner: " + (player.getPartner() == null ? "none" : player.getPartner()));
        }
        values += ("++++++ 5-Year Summary ++++++");
        values += ("\nPlayer: " + player.getName());
        values += ("\nNet Worth: " + player.getPrettyNetWorth());
        values += ("\nChildren: " + player.getChildren());
        if (player.isMarried()) {
            values += ("\nSpouse: " + player.getPartner());
        } else {
            values += ("\nPartner: " + (player.getPartner() == null ? "none" : player.getPartner()));
        }
        return values;
    }

    private void runEffect(int index, Scene currentScene) {
        EffectsTranslator.doEffects(player, currentScene.effects.get(index));
    }

    private void displayOutcome(int index, Scene currentScene) {
        System.out.println(currentScene.outcomes.get(index));
        System.out.println();
    }

    private int prompt(Scene currentScene) {
        System.out.println();
        System.out.println(currentScene.prompt);
        System.out.println();
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

    private void getPlayerBasicData() {
        System.out.println("Enter your Name: ");
        String playerName = getInput();
        clearScreen();
        List<Backstory> backstories = getBackStoryScenes();
        processBackstories(backstories);
        System.out.println();
        // TODO: Make this better narrative
        System.out.println("Do you want to go to college? (Y/N): ");
        String educationChoice = getInput();

        System.out.println("Your name is " + playerName + ". \nYou chose " + educationChoice + " for college. ");

        player.setName(playerName);

        if (educationChoice.equalsIgnoreCase("y"))
            player.setEducation(true);
    }

    private void processBackstories(List<Backstory> backstories) {
        for (Backstory backstory : backstories) {
            System.out.println(backstory.getPrompt());
            System.out.println();

            for (BackstoryOption option : backstory.getOptions())
                System.out.println(option.text);

            String resp = getInput();
            BackstoryOption selectedBackstoryOption = null;
            for (BackstoryOption option : backstory.getOptions()) {
                if(option.text.equalsIgnoreCase(resp)){
                    selectedBackstoryOption = option;
                    break;
                }
            }
            System.out.println();
            System.out.println(selectedBackstoryOption.outcome);
            EffectsTranslator.getAttribute(player, selectedBackstoryOption.attribute);
            System.out.println("\nPress any key to continue");
            getInput();
            clearScreen();
        }
        System.out.println("Your character's stats:");
        System.out.println("Strength: " + player.getStrength());
        System.out.println("Intellect: " + player.getIntellect());
        System.out.println("Creativity: " + player.getCreativity());
    }

    private List<Backstory> getBackStoryScenes() {
        List<Backstory> backstories = new ArrayList<>();
        JSONArray fileData = readJsonArray("scenes/backstory.json");
        for (Object jsonBackstory : fileData) {
            Backstory backstory = Backstory.fromJson((JSONObject) jsonBackstory);
            backstories.add(backstory);
        }
        return backstories;
    }

    private JSONArray readJsonArray(String path) {
        File file = new File(path);
        StringBuilder jsonString = new StringBuilder("");
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine())
                jsonString.append(reader.nextLine());

        } catch (Exception e) {
            System.out.println(e);
        }

        return new JSONArray(jsonString.toString());
    }

    private String welcome() {
        String art = "\n" +
                "  /$$$$$$              /$$           /$$$$$$$  /$$           /$$              /$$$$$$                  /$$$$$$$  /$$                 /$$$$$$$$                  /$$                    \n" +
                " /$$__  $$            | $$          | $$__  $$|__/          | $$             /$$__  $$                | $$__  $$|__/                |__  $$__/                 |__/                    \n" +
                "| $$  \\__/  /$$$$$$  /$$$$$$        | $$  \\ $$ /$$  /$$$$$$$| $$$$$$$       | $$  \\ $$  /$$$$$$       | $$  \\ $$ /$$  /$$$$$$          | $$  /$$$$$$  /$$   /$$ /$$ /$$$$$$$   /$$$$$$ \n" +
                "| $$ /$$$$ /$$__  $$|_  $$_/        | $$$$$$$/| $$ /$$_____/| $$__  $$      | $$  | $$ /$$__  $$      | $$  | $$| $$ /$$__  $$         | $$ /$$__  $$| $$  | $$| $$| $$__  $$ /$$__  $$\n" +
                "| $$|_  $$| $$$$$$$$  | $$          | $$__  $$| $$| $$      | $$  \\ $$      | $$  | $$| $$  \\__/      | $$  | $$| $$| $$$$$$$$         | $$| $$  \\__/| $$  | $$| $$| $$  \\ $$| $$  \\ $$\n" +
                "| $$  \\ $$| $$_____/  | $$ /$$      | $$  \\ $$| $$| $$      | $$  | $$      | $$  | $$| $$            | $$  | $$| $$| $$_____/         | $$| $$      | $$  | $$| $$| $$  | $$| $$  | $$\n" +
                "|  $$$$$$/|  $$$$$$$  |  $$$$/      | $$  | $$| $$|  $$$$$$$| $$  | $$      |  $$$$$$/| $$            | $$$$$$$/| $$|  $$$$$$$         | $$| $$      |  $$$$$$$| $$| $$  | $$|  $$$$$$$\n" +
                " \\______/  \\_______/   \\___/        |__/  |__/|__/ \\_______/|__/  |__/       \\______/ |__/            |_______/ |__/ \\_______/         |__/|__/       \\____  $$|__/|__/  |__/ \\____  $$\n" +
                "                                                                                                                                                      /$$  | $$               /$$  \\ $$\n" +
                "                                                                                                                                                     |  $$$$$$/              |  $$$$$$/\n" +
                "                                                                                                                                                      \\______/                \\______/ \n";
        System.out.println(art);
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


