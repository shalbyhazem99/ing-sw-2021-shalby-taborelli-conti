package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.market.MoveType;

public class MarketInteractionPlayerMove extends PlayerMove {
    MoveType moveType;
    int pos;

    /**
     * Constructor of the class, it requires the {@link MoveType} and the position of the row/column
     * @param moveType can be ROW / COLUMN
     * @param pos which ROW / COLUMN is selected
     */
    public MarketInteractionPlayerMove(MoveType moveType, int pos, String name_of_user) {
        super(name_of_user);
        this.moveType = moveType;
        this.pos = pos;
    }

    @Override
    public void execute(Match match) {
        match.marketInteraction(moveType, pos, getName_of_user());
    }
}
