package com.mbclandgroup.fitresume.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
public class UtilsMethod {

    public static void moveFile(File file){
        File [] f = file.listFiles();
        for(File element : f) {
            if(!element.isDirectory() && f.length > 0) {
                //TODO move to new location . . .
            }
        }
    }

    public static void deleteFile(File file) {

        File [] f = file.listFiles();
        for(File element : f) {
            if(!element.isDirectory()) {
                //TODO move to delete or remove . . .
            }
        }
    }

    public static boolean isPathFromEmpty(File fileWithPathToCheck) {

        try {
            File [] f = fileWithPathToCheck.listFiles();
            if(f.length == 0 || fileWithPathToCheck == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.err.println("Cannot find file(s) in path!");
        }

        return false;
    }

    public static int countRow(String pathToFile) {
        int count = 0;
        try {
            File file = new File(pathToFile);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred. File could not be read" + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }

}
