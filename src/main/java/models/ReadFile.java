package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ReadFile {
    //Fields
    private String[] StringArray;

    //Constructors
    public ReadFile(String line) {
        try
        {
            File file = new File(line);
            FileReader Read = new FileReader(file);
            BufferedReader Reader = new BufferedReader(Read);
            String ln;
            int num = 0;
            StringBuilder build = new StringBuilder();
            try {
                while((ln=Reader.readLine())!=null)
                {
                    build.append(ln);
                    build.append("\n");
                    num++;
                }
                //setStringArray(build.split("\n"));
                StringArray = build.toString().split("\n");
                Read.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    //Setters and Getters
    public String[] getStringArray() {
        return StringArray;
    }

    public void setStringArray(String[] stringArray) {
        StringArray = stringArray;
    }

    //toString Method
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(String getString : this.StringArray)
        {
            output.append(getString);
            output.append("\n");
        }
        //return Arrays.toString(StringArray);Print out array on one line
        return output.toString();
    }
}
