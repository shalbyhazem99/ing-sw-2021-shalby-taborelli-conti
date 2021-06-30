package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.settings.resilience.DisconnectionMove;
import it.polimi.ingsw.controller.move.settings.resilience.ReconnectionMove;
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

    private synchronized void executeMove(PlayerMove playerMove){
        if(playerMove.getPlayer()==null)
            playerMove.concatPlayer(match.getPlayerFromPosition(0));
        if(match.isMyTurn(playerMove.getPlayer()) || playerMove instanceof DisconnectionMove || playerMove instanceof ReconnectionMove) {
            playerMove.execute(match);
        }
    }

    @Override
    public void update(PlayerMove playerMove) {
        executeMove(playerMove);
    }
}
