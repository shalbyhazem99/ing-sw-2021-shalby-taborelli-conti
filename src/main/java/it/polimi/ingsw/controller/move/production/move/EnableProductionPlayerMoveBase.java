package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class EnableProductionPlayerMoveBase extends EnableProductionPlayerMove {
    private ResourceType to;

    public EnableProductionPlayerMoveBase(ArrayList<ResourcePick> resourceToUse, ResourceType to) {
        super(resourceToUse);
        this.to=to;
    }

    public static EnableProductionPlayerMoveBase getInstance(ArrayList<ResourcePick> resourceToUse,ResourceType to) {
        return new EnableProductionPlayerMoveBase(resourceToUse, to);
    }

    @Override
    public void execute(Match match) {
        match.enableProductionBaseInteraction(resourceToUse,to,getPlayer(),false);
    }
}
