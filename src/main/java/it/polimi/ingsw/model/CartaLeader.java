package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class LeaderCard {
    private boolean active;
    private int victoryPoints;
    private java.util.ArrayList<CountResources> neededResources;
    private java.util.ArrayList<DevelopmentCardNeeded> neededDevelopmentCard;

    public LeaderCard(int victoyPoints, ArrayList<CountResources> neededResources, ArrayList<DevelopmentCardNeeded> developmentCardNeed ) {
        this.victoryPoints = victoyPoints;
        this.neededResources = neededResources;
        this.carteSviluppoRichieste = developmentCardNeed;
        this.active=false;
    }

    private boolean activable(Player player){
        //Todo: check if the player can activate the card
        return false;
    }

    public abstract boolean play(Player player);

    public Boolean isActive(){
        return active;
    }
}
