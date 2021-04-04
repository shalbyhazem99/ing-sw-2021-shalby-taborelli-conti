package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends Observable<PlayerMove> implements Observer<MoveResponse> {

    private Player player;
    private ClientConnection clientConnection;

    private class MessageReceiver implements Observer<PlayerMove> {
        @Override
        public void update(PlayerMove message) {
            handleMove(message);
        }
    }

    public RemoteView(Player player, ClientConnection clientConnection) {
        this.player = player;
        this.clientConnection = clientConnection;
        clientConnection.addObserver(new MessageReceiver());
    }

    public Player getPlayer() {
        return player;
    }

    void handleMove(PlayerMove playerMove) {
        playerMove.concatPlayer(player);
        notify(playerMove); //notify it to the controller
    }

    @Override
    public void update(MoveResponse message) {
        //todo: see to which player to send
        clientConnection.asyncSend(message);
    }

}
