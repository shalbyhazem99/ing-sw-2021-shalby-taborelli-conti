package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;

public class EnableProductionPlayerMoveDevelopmentCard extends EnableProductionPlayerMove {
    private int positionOfDevelopmentCard;

    public EnableProductionPlayerMoveDevelopmentCard(int positionOfDevelopmentCard) {
        super();
        this.positionOfDevelopmentCard = positionOfDevelopmentCard;
    }

    public static EnableProductionPlayerMoveDevelopmentCard getInstance(int positionOfDevelopmentCard) {
        return new EnableProductionPlayerMoveDevelopmentCard(positionOfDevelopmentCard);
    }

    @Override
    public void execute(Match match) {
        //match.enableProductionInteraction(productivePowers,devCardProductivePlayerSelected,getPlayer());
    }
}
