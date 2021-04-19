package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;

public class EnableProductionPlayerMoveLeaderCard extends EnableProductionPlayerMove {
    private int positionOfProductivePower;

    public EnableProductionPlayerMoveLeaderCard(int positionOfProductivePower) {
        super();
        this.positionOfProductivePower = positionOfProductivePower;
    }

    public static EnableProductionPlayerMoveLeaderCard getInstance(int positionOfProductivePower) {
        return new EnableProductionPlayerMoveLeaderCard(positionOfProductivePower);
    }

    @Override
    public void execute(Match match) {
        //match.enableProductionInteraction(productivePowers,devCardProductivePlayerSelected,getPlayer());
    }
}
