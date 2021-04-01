package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import java.io.FileWriter;

import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.*;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.leaderCard.LeaderCardAddProductive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.IOException;

// todo: add the link to image
// todo: add the documentation of parms


public class DevelopmentCard2JSON {


    public static void main(String[] args) {

        InputStreamReader reader = new InputStreamReader (System.in);
        BufferedReader myInput = new BufferedReader (reader);
        String name = "DevelopmentCard_";
        String str ="";
        String path ="C:\\Users\\tabot\\Desktop\\Json\\DevelopmentCard\\";
        DevelopmentCardLevel level = null;
        DevelopmentCardType color =null;
        int victoryPoints;
        int neededResource;
        ResourceType resourceType = null;
        //ArrayList<DevelopmentCard> list = new ArrayList<DevelopmentCard>();




        for (int i = 1; i <= 48; i++) {
            ArrayList<ResourcesCount> costs = new ArrayList<ResourcesCount>();
            ArrayList<ResourcesCount> from = new ArrayList<ResourcesCount>();
            ArrayList<Resource> to = new ArrayList<Resource>();
            // Asking the level of the card
            do {
                System.out.println("Which is the level of the card " + i +" ? 1/2/3");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("1")){
                    level =DevelopmentCardLevel.FIRST;
                }
                else if (str.equals("2")){
                    level =DevelopmentCardLevel.SECOND;
                }
                else if (str.equals("3")){
                    level =DevelopmentCardLevel.THIRD;
                }
                else{
                    str="error";
                }
            }while (str.equals("error"));


            //Asking the colour of the card
            do {
                System.out.println("Which is the color of the card? g/b/y/p");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("g")){
                    color=DevelopmentCardType.GREEN;
                }
                else if (str.equals("b")){
                    color=DevelopmentCardType.BLUE;
                }
                else if (str.equals("y")){
                    color=DevelopmentCardType.YELLOW;
                }
                else if(str.equals("p")){
                    color=DevelopmentCardType.PURPLE;
                }
                else{
                    str="error";
                }
            }while (str.equals("error"));


            //Asking the value of the card
            System.out.println("Which is the value of the card? Insert a number");
            try {str = myInput.readLine(); }
            catch (IOException e) {
                System.out.println ("An error occurred: " + e);
                System.exit(-1);
            }
            victoryPoints = Integer.parseInt(str);

            //Asking the cost of play of the card

            while (true){
                System.out.println("Which is the number of the resource cost? Insert a number");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                neededResource = Integer.parseInt(str);

                System.out.println("Which is the type of resource cost? g/b/y/p/w/r");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                do {
                    if (str.equals("g")){
                        resourceType=ResourceType.STONE;
                    }
                    else if (str.equals("b")){
                        resourceType=ResourceType.SHIELD;
                    }
                    else if (str.equals("y")){
                        resourceType=ResourceType.COIN;
                    }
                    else if(str.equals("p")){
                        resourceType=ResourceType.SERVANT;
                    }
                    else if(str.equals("w")){
                        resourceType=ResourceType.ANY;
                    }
                    else if(str.equals("r")){
                        resourceType=ResourceType.FAITH;
                    }
                    else{
                        str="error";
                    }
                } while (str.equals("error"));

                ResourcesCount item = new ResourcesCount(neededResource, resourceType);
                costs.add(item);

                System.out.println("Do you need any other cost? Y/N");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("N")){
                    break;
                }

            }

            //Asking the trades of the card
            while (true){
                System.out.println("Which is the number of the resource for the trade? Insert a number");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                neededResource = Integer.parseInt(str);

                System.out.println("Which is the type of the resource you need for trade? g/b/y/p/w/r");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                do {
                    if (str.equals("g")){
                        resourceType=ResourceType.STONE;
                    }
                    else if (str.equals("b")){
                        resourceType=ResourceType.SHIELD;
                    }
                    else if (str.equals("y")){
                        resourceType=ResourceType.COIN;
                    }
                    else if(str.equals("p")){
                        resourceType=ResourceType.SERVANT;
                    }
                    else if(str.equals("w")){
                        resourceType=ResourceType.ANY;
                    }
                    else if(str.equals("r")){
                        resourceType=ResourceType.FAITH;
                    }
                    else{
                        str="error";
                    }
                } while (str.equals("error"));

                ResourcesCount trade = new ResourcesCount(neededResource, resourceType);
                from.add(trade);

                System.out.println("Do you need any other resources for the trade? Y/N");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("N")){
                    break;
                }
            }

            while (true){

                System.out.println("Which is the number of the resource you get from the trade? Insert a number");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                neededResource = Integer.parseInt(str);

                System.out.println("Which is the type of resource you get from the trade? g/b/y/p/w/r");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                do {
                    if (str.equals("g")){
                        resourceType=ResourceType.STONE;
                    }
                    else if (str.equals("b")){
                        resourceType=ResourceType.SHIELD;
                    }
                    else if (str.equals("y")){
                        resourceType=ResourceType.COIN;
                    }
                    else if(str.equals("p")){
                        resourceType=ResourceType.SERVANT;
                    }
                    else if(str.equals("w")){
                        resourceType=ResourceType.ANY;
                    }
                    else if(str.equals("r")){
                        resourceType=ResourceType.FAITH;
                    }
                    else{
                        str="error";
                    }
                } while (str.equals("error"));

                Resource marble = new Resource(resourceType);
                for (int j = 0; j < neededResource; j++) {

                    to.add(marble);
                }

                System.out.println("Do you get any other resources from the trade? Y/N");
                try {str = myInput.readLine(); }
                catch (IOException e) {
                    System.out.println ("An error occurred: " + e);
                    System.exit(-1);
                }
                if (str.equals("N")){
                    break;
                }

            }


            // insert the conversion i into string
            ProductivePower powers = ProductivePower.getInstance(from, to);
            DevelopmentCard card = DevelopmentCard.getInstance(level, color, victoryPoints, costs, powers, String.valueOf(i));
            //list.add(card);

            // Catching the name of the file that will be created
            try {
                FileWriter myWriter = new FileWriter(path+name+i+".json");
                myWriter.write((new Gson().toJson(card)));
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }


    }
}
