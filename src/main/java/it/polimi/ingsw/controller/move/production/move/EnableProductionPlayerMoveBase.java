package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ResourceType;

public class EnableProductionPlayerMoveBase extends EnableProductionPlayerMove {
    private ResourceType to;

    public EnableProductionPlayerMoveBase(ResourceType to) {
        super();
        this.to=to;
    }

    public static EnableProductionPlayerMoveBase getInstance(ResourceType to) {
        return new EnableProductionPlayerMoveBase(to);
    }

    @Override
    public void execute(Match match) {
        //match.enableProductionInteraction(productivePowers,devCardProductivePlayerSelected,getPlayer());
    }
}
