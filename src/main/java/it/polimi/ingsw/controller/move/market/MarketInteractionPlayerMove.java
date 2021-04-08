package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.MoveType;

public class MarketInteractionPlayerMove extends PlayerMove {
    /**
     * Class representing the {@link PlayerMove} performed by the {@link it.polimi.ingsw.model.Player} in order to get {@link it.polimi.ingsw.model.Resource} from the {@link it.polimi.ingsw.model.market.MarketBoard}
     */
    MoveType moveType;
    int pos;

    /**
     * Constructor of the class, it requires the {@link MoveType} and the position of the row/column
     * @param moveType can be ROW / COLUMN
     * @param pos which ROW / COLUMN is selected
     */
    public MarketInteractionPlayerMove(MoveType moveType, int pos) {
        this.moveType = moveType;
        this.pos = pos;
    }

    public static MarketInteractionPlayerMove getInstance(MoveType moveType, int pos) {
        return new MarketInteractionPlayerMove(moveType, pos);
    }
    @Override
    public void execute(Match match) {
        if(match.getCanChangeTurn()) {
            match.marketInteraction(moveType, pos, player);
        }
    }
}
