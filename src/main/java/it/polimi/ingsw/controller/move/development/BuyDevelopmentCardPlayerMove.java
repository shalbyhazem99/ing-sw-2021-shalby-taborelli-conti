package it.polimi.ingsw.controller.move.development;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.util.ArrayList;

public class BuyDevelopmentCardPlayerMove extends PlayerMove {
    DevelopmentCardType type;
    DevelopmentCardLevel level;
    int posToAdd; //start from 0
    ArrayList<ResourcePick> resourceToUse;

    public BuyDevelopmentCardPlayerMove(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse) {
        this.type = type;
        this.level = level;
        this.posToAdd = posToAdd;
        this.resourceToUse=resourceToUse;
    }

    public static BuyDevelopmentCardPlayerMove getInstance(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse) {
        return new BuyDevelopmentCardPlayerMove(type, level, posToAdd,resourceToUse);
    }

    @Override
    public void execute(Match match) {
        if(match.getCanChangeTurn()) {
            match.buyDevelopmentCardInteraction(type, level, player, posToAdd,resourceToUse,false);
        }
    }
}
