package it.polimi.ingsw.model;

import java.util.ArrayList;

public class LeaderWarehouseCard extends LeaderCard {
    public LeaderWarehouseCard(int victoryPoints, ArrayList<CountResources> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(victoryPoints, resourcesNeeded, developmentCardNeeded);
    }

    public static LeaderWarehouseCard getInstance(int victoryPoints, ArrayList<CountResources> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderWarehouseCard(victoryPoints, resourcesNeeded, developmentCardNeeded);
    }

    @Override
    public boolean attiva(Player player) {
        //TODO: can activate and set the power to the player
        return false;
    }
}
