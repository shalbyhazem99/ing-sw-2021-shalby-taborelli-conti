package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductivePower;

import java.util.ArrayList;
import java.util.Map;

public abstract class EnableProductionPlayerMove extends PlayerMove {
    protected ArrayList<ResourcePick> resourceToUse;

    public EnableProductionPlayerMove() {
        resourceToUse = new ArrayList<>();
    }

    public void addResourceToUse(ResourcePick resourcePick){
        resourceToUse.add(resourcePick);
    }
}
