package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {
    private String[] StringArray;
    public ReadFile(String line)//Constructor
    {
        try
        {
            File file = new File(line);
            FileReader Read = new FileReader(file);
            BufferedReader Reader = new BufferedReader(Read);
            String ln;
            int num = 0;
            String build = "";
            try {
                while((ln=Reader.readLine())!=null)
                {
                    build+=ln;
                    build+="\n";
                    num++;
                }
                //setStringArray(build.split("\n"));
                StringArray = build.split("\n");
                Read.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    public String[] getStringArray() {
        return StringArray;
    }

    public void setStringArray(String[] stringArray) {
        StringArray = stringArray;
    }

    public String toString()//Method
    {
        String output = "";
        for(String getString : this.StringArray)
        {
            output+=getString;
            output+="\n";
        }
        //return Arrays.toString(StringArray);Print out array on one line
        return output;
    }

    public static void main(String[] args)
    {
        ReadFile read = new ReadFile("data.txt");
        System.out.println(read.toString());
    }
}
