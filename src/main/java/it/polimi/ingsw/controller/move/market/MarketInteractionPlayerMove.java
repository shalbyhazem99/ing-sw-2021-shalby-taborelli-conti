package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.market.MoveType;

public class MarketInteractionPlayerMove implements PlayerMove {
    MoveType moveType;
    int pos;
    @Override
    public void exectute(Match match) {
        match.marketInteraction(moveType, pos);
    }
}
