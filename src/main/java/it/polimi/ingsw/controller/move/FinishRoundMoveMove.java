package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;

public abstract class FinishRoundMoveMove implements Move {

    @Override
    public void exectute(Match match) {
        match.nextRound();
    }
}
