package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {
    private int locationPath;
    private java.util.ArrayList<PopeTales> popeTales;
    private java.util.ArrayList<LeaderCard> leaderCards;
    private java.util.ArrayList<DevelopmentCardSpace> developmentCardSpaces;
    private java.util.ArrayList<Resource> standardWarehouse;
    private ArrayList<Resource> strongbox;
    private java.util.ArrayList<Warehouse> additionalWarehouses;
    private ArrayList<CountResources> descounts;
    private ArrayList<ResourceType> strategyConversion;
    private ArrayList<ProductivePower> additionalPower;

    public static Player generatePlayer(){
        //TODO:create player n
        return null;
    }

    public void haedPopeFaith(int numberPosition){
        //TODO: goes haed
    }

    public Boolean addDevelopmentCard(DevelopmentCard developmentCard){
        //TODO: check if he can or can't add the card.
        //TODO: check how to get developmentcardSpace where u should add the card
        return false;
    }
    public void addResourceStrongbox(Resource resource){
        //TODO:Aahhiunge una risorsa al forziere
    }

}
