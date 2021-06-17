package it.polimi.ingsw.controller.move.settings.resilience;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.connection.view.RemoteView;

public class DisconnectionMove extends PlayerMove {
    private String playerName;
    private RemoteView playerView;

    public DisconnectionMove(String playerName,RemoteView playerView) {
        this.playerName = playerName;
        this.playerView =playerView;
    }

    public static DisconnectionMove getInstance(String playerName,RemoteView playerView) {
        return new DisconnectionMove(playerName,playerView);
    }

    @Override
    public void execute(Match match) {
        match.removeObserver(playerView);
        match.disconnectPlayer(playerName,false);
    }

    public String getPlayerName() {
        return playerName;
    }
}
