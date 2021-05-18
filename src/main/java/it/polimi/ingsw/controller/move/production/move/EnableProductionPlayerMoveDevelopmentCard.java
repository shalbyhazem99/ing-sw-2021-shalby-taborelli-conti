package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;

import java.util.ArrayList;

public class EnableProductionPlayerMoveDevelopmentCard extends EnableProductionPlayerMove {
    private int positionOfDevelopmentCard;

    public EnableProductionPlayerMoveDevelopmentCard(ArrayList<ResourcePick> resourceToUse,int positionOfDevelopmentCard) {
        super(resourceToUse);
        this.positionOfDevelopmentCard = positionOfDevelopmentCard;
    }

    public static EnableProductionPlayerMoveDevelopmentCard getInstance(ArrayList<ResourcePick> resourceToUse,int positionOfDevelopmentCard) {
        return new EnableProductionPlayerMoveDevelopmentCard(resourceToUse, positionOfDevelopmentCard);
    }

    @Override
    public void execute(Match match) {
        match.enableProductionDevelopmentInteraction(resourceToUse,positionOfDevelopmentCard,getPlayer(),false);
    }
}
