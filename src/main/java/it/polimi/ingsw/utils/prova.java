package it.polimi.ingsw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class prova {
    public static void main(String[] args) {
        String str = new String();
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader myInput = new BufferedReader(reader);
        while (true) {
            System.out.println("How many points has the card?");
            try {
                str = myInput.readLine();
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
                System.exit(-1);
            }
            try {

                int victoryPoints = Integer.valueOf(str);
                break;

            } catch (Exception e) {

                System.out.println("Hai sbagliato a inserire il numero");

            }
        }
    }
}
