package it.polimi.ingsw.model;

import java.util.ArrayList;

public class ProductiveDevelopmentCard extends CartaLeader {
    public ProductiveDevelopmentCard(int victoryPoints, ArrayList<CountResources> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(victoryPoints, resourcesNeeded, developmentCardNeeded);
    }

    public static ProductiveDevelopmentCard getInstance(int victoryPoints, ArrayList<CountResources> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new ProductiveDevelopmentCard(victoryPoints, resourcesNeeded, developmentCardNeeded);
    }
    @Override
    public boolean activate(Player player) {
        //TODO: can activate and set the power to the player
        return false;
    }
}
