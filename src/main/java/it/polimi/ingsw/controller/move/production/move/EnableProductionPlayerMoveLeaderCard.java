package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;

import java.util.ArrayList;

public class EnableProductionPlayerMoveLeaderCard extends EnableProductionPlayerMove {
    private int positionOfProductivePower;

    public EnableProductionPlayerMoveLeaderCard(ArrayList<ResourcePick> resourceToUse,int positionOfProductivePower) {
        super(resourceToUse);
        this.positionOfProductivePower = positionOfProductivePower;
    }

    public static EnableProductionPlayerMoveLeaderCard getInstance(ArrayList<ResourcePick> resourceToUse,int positionOfProductivePower) {
        return new EnableProductionPlayerMoveLeaderCard(resourceToUse,positionOfProductivePower);
    }

    @Override
    public void execute(Match match) {
        match.enableProductionLeaderInteraction(resourceToUse,positionOfProductivePower,getPlayer(),false);
    }
}
