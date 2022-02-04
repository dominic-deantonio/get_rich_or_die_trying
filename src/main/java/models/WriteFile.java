package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class WriteFile {
    String fileName;
    String content;

    public WriteFile(String fileName, String content)
    {
        this.fileName = fileName;
        this.content = content;
    }

    public void saveFile(boolean appendFile)//Will be called when more than one player is added
    {//Should be a method that calls this method for adding more than one player
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
