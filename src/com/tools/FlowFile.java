package com.tools;

import java.io.*;

//Allows the user to easily read and write files(8-bit)
public class FlowFile {
    //Will be true if the FlowFile could not write or read the file
    public boolean Failed = false;
    
    //Stores the name(and path) of the file
    private final String FileName;
    
    public FlowFile(String FileName) { this.FileName = FileName; }
    
    //Write an array to the file
    public void write(int[] Print) {
        //Open a new FileOutputStream going to the file name(and path)
        try (FileOutputStream end = new FileOutputStream(FileName)) {
            //Write all the arrays elements
            for (int a = 0; a < Print.length; a++) end.write(Print[a]);
            //close the stream
            end.close();
        } catch (IOException error) {
            //If an error has been caught
            
            //Say an error has occured in the console
            System.err.println("A Write Error has occured:" + error.toString());
            
            //Set the "Failed" value to true, to show it has failed.
            Failed = true;
        }
    }
    public int[] read(int length) {
        //Create a new array to read from
        int[] result = new int[length];
        
        //Create a new FileInputStream going to the file name(and path)
        try (FileInputStream start = new FileInputStream(FileName)) {
            //Dump all the data asked for into "result"
            for (int a = 0; a < length; a++) result[a] = start.read();
            
            //close the stream
            start.close();
        } catch (IOException error) {
            //If an error was caught
            
            //Say an error has occured in the console
            System.err.println("A Read Error has occured:" + error.toString());
            
            //Set the "Failed" value to true, to show it has failed.
            Failed = true;
            
            //Nullify the array
            result = null;
        }
        //Return what I got
        return result;
    }
}