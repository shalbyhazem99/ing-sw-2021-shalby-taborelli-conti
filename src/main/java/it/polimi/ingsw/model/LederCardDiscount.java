package it.polimi.ingsw.model;

import java.util.ArrayList;

public class LederCardDiscount extends CartaLeader {
    public LederCardDiscount(int victoryPoints, ArrayList<CountResources> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(victoryPoints, resourcesNeeded, developmentCardNeeded);
    }

    public static LederCardDiscount getInstance(int victoryPoints, ArrayList<CountResources> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LederCardDiscount(victoryPoints, resourcesNeeded, developmentCardNeeded);
    }

    public boolean active(Player player) {
        //TODO: can activate and set the power to the player
        return false;
    }
}
