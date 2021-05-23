package it.polimi.ingsw.utils.jsonCreator;

import com.google.gson.Gson;
import java.io.FileWriter;

import it.polimi.ingsw.model.developmentCard.*;
import it.polimi.ingsw.model.leaderCard.*;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;



public class LeaderCard2JSON {


    public static void main(String[] args) {

        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader myInput = new BufferedReader(reader);
        String name = "LeaderCard_Discount_";
        String str = "";
        String path = "C:\\Users\\tabot\\Desktop\\Json\\LederCard\\";
        //ArrayList<LeaderCard> list = new ArrayList<LeaderCard>();
        int victoryPoints=0;
        ResourceType resourceTypeRelated = null;
        int base = 48;



        for (int i = 0; i < 16; i++) {
            base++;
            ArrayList<ResourcesCount> resourcesNeeded = new ArrayList<>();
            ArrayList<DevelopmentCardNeeded> developmentCardNeeded = new ArrayList<DevelopmentCardNeeded>();

            // Asking victory points of the card
            while (true) {
                System.out.println("How many points has the card?");
                try {
                    str = myInput.readLine();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e);
                    System.exit(-1);
                }
                try {

                    victoryPoints = Integer.valueOf(str);
                    break;

                } catch (Exception e) {

                    System.out.println("Insert an integer");

                }
            }


            // Asking the type of the
            do {
                System.out.println("Which is the type of resource involved in the power? g/b/y/p/w/r");
                try {
                    str = myInput.readLine();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e);
                    System.exit(-1);
                }

                if (str.equals("g")) {
                    resourceTypeRelated = ResourceType.STONE;
                } else if (str.equals("b")) {
                    resourceTypeRelated = ResourceType.SHIELD;
                } else if (str.equals("y")) {
                    resourceTypeRelated = ResourceType.COIN;
                } else if (str.equals("p")) {
                    resourceTypeRelated = ResourceType.SERVANT;
                } else if (str.equals("w")) {
                    resourceTypeRelated = ResourceType.ANY;
                } else if (str.equals("r")) {
                    resourceTypeRelated = ResourceType.FAITH;
                } else {
                    str = "error";
                }
            } while (str.equals("error"));


            // Asking if any resources are needed as cost
            while (true){
                System.out.println("Do you need any resource as cost? N/Y");
                try {
                    str = myInput.readLine();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("Y")){

                    while (true) {
                        int count = 0;
                        ResourceType resourceType = null;

                        System.out.println("How many resources do you need? Insert a number");
                        try {
                            str = myInput.readLine();
                        } catch (IOException e) {
                            System.out.println("An error occurred: " + e);
                            System.exit(-1);
                        }
                        count = Integer.valueOf(str);
                        do {
                            System.out.println("Which type of resource do you need? g/b/y/p/w/r");
                            try {
                                str = myInput.readLine();
                            } catch (IOException e) {
                                System.out.println("An error occurred: " + e);
                                System.exit(-1);
                            }

                            if (str.equals("g")) {
                                resourceType = ResourceType.STONE;
                            } else if (str.equals("b")) {
                                resourceType = ResourceType.SHIELD;
                            } else if (str.equals("y")) {
                                resourceType = ResourceType.COIN;
                            } else if (str.equals("p")) {
                                resourceType = ResourceType.SERVANT;
                            } else if (str.equals("w")) {
                                resourceType = ResourceType.ANY;
                            } else if (str.equals("r")) {
                                resourceType = ResourceType.FAITH;
                            } else {
                                str = "error";
                            }
                        } while (str.equals("error"));

                        ResourcesCount cost = new ResourcesCount(count, resourceType);
                        resourcesNeeded.add(cost);

                        System.out.println("Do you need other resources as cost? Y/N");
                        try {
                            str = myInput.readLine();
                        } catch (IOException e) {
                            System.out.println("An error occurred: " + e);
                            System.exit(-1);
                        }
                        if (str.equals("N")){
                            break;
                        }
                    }
                    break;
                }
                else if (str.equals("N")){
                    break;
                }
                else {
                    System.out.println("Insert a correct option!");
                }
            }


            // Asking if there is any type of card as requirements

            while (true){
                System.out.println("Do you need any type of DevelopmentCard as cost? N/Y");
                try {
                    str = myInput.readLine();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("Y")){
                    while (true) {
                        int count=0;
                        DevelopmentCardType type = null;
                        DevelopmentCardLevel level = null;

                        System.out.println("How many DevelopmentCard of that type do you need? Insert a number");
                        try {
                            str = myInput.readLine();
                        } catch (IOException e) {
                            System.out.println("An error occurred: " + e);
                            System.exit(-1);
                        }
                        count = Integer.valueOf(str);
                        do {
                            System.out.println("Which color of DevelopmentCard do you need? g/b/y/p");
                            try {
                                str = myInput.readLine();
                            } catch (IOException e) {
                                System.out.println("An error occurred: " + e);
                                System.exit(-1);
                            }

                            if (str.equals("g")) {
                                type=DevelopmentCardType.GREEN;
                            } else if (str.equals("b")) {
                                type=DevelopmentCardType.BLUE;
                            } else if (str.equals("y")) {
                                type=DevelopmentCardType.YELLOW;
                            } else if (str.equals("p")) {
                                type=DevelopmentCardType.PURPLE;
                            }
                            else {
                                str = "error";
                            }
                        }while (str.equals("error"));

                        do {
                            System.out.println("Do you need a level for that color? 0/1/2/3");
                            try {
                                str = myInput.readLine();
                            } catch (IOException e) {
                                System.out.println("An error occurred: " + e);
                                System.exit(-1);
                            }

                            if (str.equals("1")) {
                                level=DevelopmentCardLevel.FIRST;
                            }
                            else if (str.equals("2")) {
                                level=DevelopmentCardLevel.SECOND;
                            }
                            else if (str.equals("3")) {
                                level=DevelopmentCardLevel.THIRD;
                            }
                            else if (str.equals("0")) {
                            }
                            else{
                                str = "error";
                            }
                        }while (str.equals("error"));
                        DevelopmentCardNeeded item = new DevelopmentCardNeeded(count, type, level);

                        developmentCardNeeded.add(item);

                        System.out.println("Do you need other type of DevelopmentCard as cost? Y/N");
                        try {
                            str = myInput.readLine();
                        } catch (IOException e) {
                            System.out.println("An error occurred: " + e);
                            System.exit(-1);
                        }
                        if (str.equals("N")){
                            break;
                        }
                    }
                    break;
                }
                else if (str.equals("N")){
                    break;
                }
                else {
                    System.out.println("Insert a correct option!");
                }
            }
            LeaderCardDiscount card = new LeaderCardDiscount(victoryPoints, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
            //list.add(card);

            // Catching the name of the file that will be created
            try {
                FileWriter myWriter = new FileWriter(path+name+base+".json");
                myWriter.write((new Gson().toJson(card)));
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }


    }
}

