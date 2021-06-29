package it.polimi.ingsw.utils.jsonCreator;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 * Class used to merge together many JSON representing {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
 * It's suggested to merge together {@link it.polimi.ingsw.model.leaderCard.LeaderCard} with the same power
 */
public class LeaderCardJoinJSON {
    public static void main(String[] args) {
        //Insert the path where to save the JSON
        String path_writing = "";
        //Insert the name of the File to save
        String name_writing = "";
        //Insert the path where are stored the JSONs
        String path_reading = "";
        //Insert the common name for the LeaderCard, except the number. Example: LeaderCard_AddProductive_
        String name_reading = "";
        String data="";
        //Insert the lowest id number of the group
        int base = 0;

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

