package it.polimi.ingsw.controller.move.development;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

public class BuyDevelopmentCardPlayerMove extends PlayerMove {
    DevelopmentCardType type;
    DevelopmentCardLevel level;
    int posToAdd; //start from 0

    public BuyDevelopmentCardPlayerMove(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd) {
        this.type = type;
        this.level = level;
        this.posToAdd = posToAdd;
    }

    public static BuyDevelopmentCardPlayerMove getInstance(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd) {
        return new BuyDevelopmentCardPlayerMove(type, level, posToAdd);
    }

    @Override
    public void execute(Match match) {
        match.buyDevelopmentCardInteraction(type, level, player, posToAdd);
    }
}
