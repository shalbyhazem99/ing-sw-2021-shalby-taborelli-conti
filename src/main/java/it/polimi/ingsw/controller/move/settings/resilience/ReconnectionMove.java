package it.polimi.ingsw.controller.move.settings.resilience;

import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.connection.ClientConnection;
import it.polimi.ingsw.connection.view.RemoteView;

import java.util.Map;

public class ReconnectionMove extends PlayerMove {
    private String playerName;
    private GameManger gameManger;
    private ClientConnection clientConnection;
    private Map<String, RemoteView> playingConnection;

    public ReconnectionMove(String playerName,GameManger gameManger,ClientConnection clientConnection,Map<String, RemoteView> playingConnection) {
        this.playerName = playerName;
        this.gameManger = gameManger;
        this.clientConnection = clientConnection;
        this.playingConnection = playingConnection;
    }

    public static ReconnectionMove getInstance(String playerName,GameManger gameManger,ClientConnection clientConnection,Map<String, RemoteView> playingConnection) {
        return new ReconnectionMove(playerName,gameManger,clientConnection,playingConnection);
    }

    @Override
    public void execute(Match match) {
        Player player= match.getPlayerFromName(playerName);
        if(player == null){
            System.out.println("Not existing player");
            return;
        }
        RemoteView playerView = new RemoteView(player, clientConnection);
        match.addObserver(playerView);
        playerView.addObserver(gameManger);
        playingConnection.put(playerName,playerView);
        match.ReconnectPlayer(playerName,false);
    }

    public String getPlayerName() {
        return playerName;
    }
}
