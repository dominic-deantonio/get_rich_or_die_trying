package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class WriteFile {
    //Fields
    String fileName;
    String content;

    //Constructors
    public WriteFile(String fileName, String content)
    {
        this.fileName = fileName;
        this.content = content;
    }

    //Business Methods

    /*
    * Will be called when more than one player is added (Not being used right now
    * Should be a method that calls this method for adding more than one player
    **/

    /**
     * No Return value
     * @param appendFile Content that needs to be appended to the exisiting file
     */
    public void saveFile(boolean appendFile) {
        try
        {
            BufferedWriter write = new BufferedWriter(new FileWriter(fileName, appendFile));
            write.write(content);
            write.close();
            System.out.println("Your saved game has been updated.");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Saves Current players 5-year Summary if file already exits
     */
    public void saveFile()
    {
        try
        {
            BufferedWriter write = new BufferedWriter(new FileWriter(fileName, false));
            write.write(content);
            write.close();
            System.out.println("Your saved game has been updated.");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Make a check using CreateNewFile() method to see if abstract file path does not exists and
     * uses existing file to update with new data by calling method saveFile()
     * Saves players 5-year summary
     */
    public void save() {
        File file = new File(fileName);
        try
        {
            if(file.createNewFile() == true)
            {
                System.out.println("Your game has been saved .");
                FileWriter writeFile = new FileWriter(fileName);
                writeFile.write(content);
                writeFile.close();
            }
            else
            {
                saveFile();
            }
        }
        catch(Exception e)
        {
            System.out.println("Can't save new file");
        }
    }

    public static void main(String[] args) {

        WriteFile saveGame = new WriteFile("src/main/java/models/test.txt","This is my file.");
        saveGame.save();

    }
}
