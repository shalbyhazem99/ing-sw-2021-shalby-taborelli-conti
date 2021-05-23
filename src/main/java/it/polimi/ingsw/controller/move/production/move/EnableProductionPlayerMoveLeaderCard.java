package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class EnableProductionPlayerMoveLeaderCard extends EnableProductionPlayerMove {
    private int positionOfProductivePower;
    private ResourceType resourceType;

    public EnableProductionPlayerMoveLeaderCard(ArrayList<ResourcePick> resourceToUse, int positionOfProductivePower, ResourceType resourceType) {
        super(resourceToUse);
        this.positionOfProductivePower = positionOfProductivePower;
        this.resourceType = resourceType;
    }

    public static EnableProductionPlayerMoveLeaderCard getInstance(ArrayList<ResourcePick> resourceToUse, int positionOfProductivePower,ResourceType resourceType) {
        return new EnableProductionPlayerMoveLeaderCard(resourceToUse,positionOfProductivePower,resourceType);
    }

    @Override
    public void execute(Match match) {
        match.enableProductionLeaderInteraction(resourceToUse,positionOfProductivePower,resourceType,getPlayer(),false);
    }
}
