package it.polimi.ingsw.utils;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class LeaderCardJoinJSON {
    public static void main(String[] args) {
        String path_writing = "C:\\Users\\tabot\\Desktop\\Json\\LederCard\\";
        String name_writing = "LeaderCard_AddProductive";
        String path_reading = "C:\\Users\\tabot\\Desktop\\Json\\LederCard\\";
        String name_reading = "LeaderCard_AddProductive_";
        String data="";
        int base = 60;

        try {
            FileWriter myWriter = new FileWriter(path_writing+name_writing+".json");
            myWriter.write("[");
            for (int i = 1; i <= 4; i++) {
                base++;
                try {
                    File myObj = new File(path_reading+name_reading+base+".json");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                myWriter.write(data);
                if (i != 4 ){
                    myWriter.write(",");
                }
            }
            myWriter.write("]");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

