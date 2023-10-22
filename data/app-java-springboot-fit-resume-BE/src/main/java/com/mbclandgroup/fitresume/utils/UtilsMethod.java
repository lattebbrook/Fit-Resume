package com.mbclandgroup.fitresume.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class UtilsMethod {

    public static void moveFile(File file, String destination){
        try {
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(destination + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //same logic but different method name
    public static void moveFileError(File file, String destination){
        try {
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(destination + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File file) {
        File [] f = file.listFiles();
        for(File element : f) {
            if(!element.isDirectory()) {
                element.delete();
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
