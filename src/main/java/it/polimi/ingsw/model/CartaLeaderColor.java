package it.polimi.ingsw.model;

import java.util.ArrayList;

public class LeaderCardColor extends CartaLeader {

    public LeaderCardColor(int victoryPoints, ArrayList<CountResources> neededResources, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(victoryPoints, neededResources, developmentCardNeeded);
    }

    public static LeaderCardColor getInstance(int victiryPoints, ArrayList<CountResources> neededResources, ArrayList<DevelopmentCardNeeded> developmentCardNeeded){
        return new LeaderCardColor(victiryPoints, neededResources, developmentCardNeeded);
    }

    public boolean activate(Player player) {
        //TODO: can activate and set the power to the player
        return false;
    }
}
