package controller;

import models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import view.MainFrame;
import javax.swing.*;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Game {
    SceneContainer scenes;
    Person player = new Person();
    private static final String os = System.getProperty("os.name").toLowerCase();
    boolean isWindows = System.getProperty("os.name").contains("Windows");
    private MainFrame mainFrame = new MainFrame();

    public Game () {
        setAllActionListeners();
    }

    public void execute() {

        scenes = new SceneContainer();
        welcome();
        checkSaveFile();
        getPlayerBasicData();
        clearScreen();
        runSceneOneCareer(player);

        while (shouldPlay()) {
            clearScreen();
            Scene currentScene = scenes.getRandomScene(player);
            System.out.println(currentScene.getArt());
            System.out.println("\n+++++++ 5 years later +++++++");
            player.addAge(5);
            int input = prompt(currentScene);
            clearScreen();
            displayOutcome(input, currentScene);
            runEffect(input, currentScene);
            String salaryReport = player.addSalary();
            System.out.println("\nEnter any key to see your 5-year summary");
            getInput();
            displaySceneSummary(salaryReport);
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
            WriteFile saveGame = new WriteFile("saveFile.txt", displaySceneSummary(""));
            saveGame.save();
        }
    }

    private void playAgainOrExit() {
    }

    //doesn't clear the scroll bar
    public void clearScreen() {
        ProcessBuilder var0 = os.contains("windows") ? new ProcessBuilder(new String[]{"cmd", "/c", "cls"}) : new ProcessBuilder(new String[]{"clear"});

        try {
            var0.inheritIO().start().waitFor();
        } catch (InterruptedException var2) {
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    private String displaySceneSummary(String salaryBreakdown) {
        String values = "";
        System.out.println("\n++++++ 5-Year Summary ++++++");
        System.out.println("Player: " + player.getName());
        System.out.println("Age: " + player.getAge());
        System.out.println("Net Worth: " + player.getPrettyNetWorth());
        System.out.println("Health: " + player.getHealthPoints());
        System.out.println("Children: " + player.getChildren());
        if (player.isMarried()) {
            System.out.println("Spouse: Sam");
        } else {
            System.out.println("Partner: " + (player.getPartner() == null ? "none" : "Sam"));
        }
        System.out.println(salaryBreakdown);
        // This is currently being used to output the summary.
        // This can go away when serialization is implemented
        values += ("++++++ 5-Year Summary ++++++");
        values += ("\nPlayer: " + player.getName());
        values += ("\nNet Worth: " + player.getPrettyNetWorth());
        values += ("\nChildren: " + player.getChildren());
        if (player.isMarried()) {
            values += ("\nSpouse: " + player.getPartner());
        } else {
            values += ("\nPartner: " + (player.getPartner() == null ? "none" : player.getPartner().getName()));
        }
        return values;
    }

    private void runEffect(int index, Scene currentScene) {
        EffectsTranslator.doEffects(player, currentScene.getEffects().get(index));
    }

    private void displayOutcome(int index, Scene currentScene) {
        System.out.println(currentScene.getOutcomes().get(index));
        System.out.println();
    }

    private int prompt(Scene currentScene) {
        System.out.println();
        System.out.println(currentScene.getPrompt());
        System.out.println();
        for (String option : currentScene.getOptions())
            System.out.println(option);

        String input = getInput(currentScene.getOptions());

        int selectedIndex = 0;

        // currentScene.getOptions.indexOf(input) is case-sensitive and the user might not enter the correct case
        // doing it this way ignores case and still gets the index
        for(String option : currentScene.getOptions()) {
            if (option.equalsIgnoreCase(input))
                break;

            selectedIndex++;
        }


        return selectedIndex;
    }

    private void runSceneOneCareer(Person player) {

        Map<Careers, List<String>> availCareers = player.hasEducation() ? Careers.getCollegeCareers() : Careers.getNonCollegeCareers();
        String collegeSummary = player.hasEducation() ? "Congratulations!\nYou finished college." : "You decided to skip the college route.";
        System.out.println(collegeSummary);
        System.out.println("What career do you want?");

        List<String> allValidCareers = new ArrayList<>();
        for (Careers career : availCareers.keySet()) {
            for (String specialty : availCareers.get(career)) {
                System.out.println(specialty);
                allValidCareers.add(specialty);
            }
        }

        String selectedCareer = getInput(allValidCareers);

        topLoop:
        for (Careers career : availCareers.keySet()) {
            for (String specialty : availCareers.get(career)) {
                if (selectedCareer.equalsIgnoreCase(specialty)) {
                    player.setCareer(career);
                    break topLoop;
                }
            }
        }

        System.out.println("\nYou chose a " + player.getCareer() + " job");

    }

    private String getInput(Collection<String> options) {
        String[] optionsArray = options.toArray(new String[0]);
        return getInput(optionsArray);
    }

    /**
     * Gets the user input
     *
     * @param selections a list of valid selections
     * @return lower case version of user input
     */
    private String getInput(String... selections) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equalsIgnoreCase("Help"))
                this.helpMenu();

            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Quitting game");
                System.exit(1);
                return "";
            }

            if (selections.length == 0)
                return userInput;

            for (String selection : selections)
                if (userInput.equalsIgnoreCase(selection))
                    return userInput;

            System.out.println("\nInvalid input. Valid options are:\n");

            for (String selection : selections)
                System.out.println(selection);
        }
    }

    public void checkSaveFile()
    {
        File checkFile = new File("saveFile.txt");
        try
        {
            if(checkFile.exists() == true)
            {
                System.out.println("Enter name of player...");
                String playerSavedName = getInput();
                System.out.println(playerSavedName);
                ReadFile read = new ReadFile("saveFile.txt");
                String info = "";
                for(String str: read.getStringArray())
                {
                    int i = 0;
                    if(str.toUpperCase().contains(playerSavedName.toUpperCase()))
                    {
                        System.out.println("Found name");
                        for(String str1: read.getStringArray())
                        {
                            info+=str1;
                            info+="\n";
                            if(str.contains("+") && i >0)
                            {
                                break;
                            }
                            i++;
                        }
                    }
                }
                String[] infoArray = info.split("\n");
                for(int i = 0; i < infoArray.length; i++)
                {
                    System.out.println(infoArray[i]);
                }
                //System.out.println(read.toString());
            }
            else if(checkFile.exists() == false)
            {
                //System.out.println("File does not exist");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void getPlayerBasicData() {
        String printBackstoryArt = Art.getArt("backstory");
        System.out.println(printBackstoryArt);
        System.out.println("Enter your Name: ");
        String playerName = getInput();

        while(playerName.isEmpty()){
            System.out.println("Name is required. Please enter your name.");
            playerName = getInput();
        }

        if (playerName.equalsIgnoreCase("DEV")) {
            player.setName("DEV");
            player.setPrivilege(true);
            player.setEducation(true);
            player.addStrength(5);
            player.addIntellect(5);
            player.addCreativity(5);
            System.out.println("Playing the game in DEV mode");
            return;
        }
        System.out.println("Select your privilege status (Working Class)/(Middle Class): ");
        String getChoice = getInput("working class", "middle class");

        if (getChoice.equalsIgnoreCase("working class")) {
            this.player.setNetWorth(player.getNetWorth() - 25000);
        } else if (getChoice.contains("middle class")) {
            this.player.setNetWorth(player.getNetWorth() + 25000);
        }
        System.out.println("" +
                "You chose: " + getChoice + "\n" +
                "Your Net Worth is: " + player.getPrettyNetWorth() + "\n\n");

        clearScreen();

//        System.out.println(printBackstoryArt);
        List<Backstory> backstories = getBackStoryScenes();
        processBackstories(backstories);
        System.out.println();
        // TODO: Make this better narrative
        System.out.println("Do you want to go to college? (Y/N): ");
        String educationChoice = getInput("y", "n");

        boolean userWantsCollege = educationChoice.equalsIgnoreCase("y");
        System.out.printf("Your name is %s. You chose to %s college.", playerName, userWantsCollege ? "go to" : "skip");

        if(userWantsCollege)
            player.addMoney(-100000);

        player.setName(playerName);
        player.setEducation(userWantsCollege);
    }

    private void processBackstories(List<Backstory> backstories) {
        for (Backstory backstory : backstories) {
            System.out.println(Art.getArt("backstory"));
            System.out.println(backstory.getPrompt());
            System.out.println();

            for (BackstoryOption option : backstory.getOptions())
                System.out.println(option.getText());

            String resp = getInput(backstory.getBackstoryOptionsText());
            BackstoryOption selectedBackstoryOption = null;
            for (BackstoryOption option : backstory.getOptions()) {
                if (option.getText().contains(resp)) {
                    selectedBackstoryOption = option;
                    break;
                }
            }
            System.out.println();
            System.out.println(selectedBackstoryOption.getOutcome());
            EffectsTranslator.getAttribute(player, selectedBackstoryOption.getAttribute());
            System.out.println("\nPress any key to continue or help for additional instructions");
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
        StringBuilder jsonString = new StringBuilder();
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
        System.out.println("Welcome to Get Rich Or Die Trying.\nAt a young age you realize that you want to be a millionaire.\nYour mission is to make $1 million before all your health points run out.\n Each choice you make will affect your net worth and health levels.");
        System.out.println("\nPress any key to continue.");
        getInput();
        clearScreen();
        return "";
    }

    private boolean shouldPlay() {
        if (player.getHealthPoints() <= 0) {
            System.out.println("Game Over. You died because you ran out of health points: " + player.getHealthPoints());
            return false;
        }

        if (player.getNetWorth() >= 1000000) {
            System.out.println("You win. You have: " + player.getPrettyNetWorth());
            return false;
        }

        return true;
    }

    public void helpMenu() {
        System.out.println("Game is meant to simulate life." +
                "\nThe intent of the game is to have 1 million dollars by the end of the game" +
                "\nChoices will change how much money you have, as well as health points." +
                "\nEx: choosing education will grant you an extra money to your salary" +
                "\nbut skipping college will start you out with less debt." +
                "\nChoose carefully, your life depends on it" +
                "\nIf you're done with the help section, press any key to continue.");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setAllActionListeners () {
        mainFrame.playButton.addActionListener(e -> execute());
        mainFrame.exitButton.addActionListener(e -> System.out.println("Exiting game"));
        mainFrame.loadButton.addActionListener(e -> System.out.println("Loading game"));
        mainFrame.helpButton.addActionListener(e -> helpMenu());
    }

}


