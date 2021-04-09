package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.observer.Observer;

public class GameManger implements Observer<PlayerMove> {
    private Match match;

    public GameManger(Match match) {
        super();
        this.match = match;
    }

    public static GameManger getInstance(Match match) {
        return new GameManger(match);
    }

    private synchronized void excuteMove(PlayerMove playerMove){
        if(match.isMyTurn(playerMove.getPlayer())) {
            playerMove.execute(match);
            //match.updateTurn();
            //TODO: control if someone won the match
        }
    }

    @Override
    public void update(PlayerMove playerMove) {
        excuteMove(playerMove);
    }
}
