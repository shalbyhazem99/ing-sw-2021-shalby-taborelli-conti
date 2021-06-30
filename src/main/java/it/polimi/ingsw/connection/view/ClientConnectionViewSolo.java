package it.polimi.ingsw.connection.view;

import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.controller.move.PlayerMove;

public class ClientConnectionViewSolo extends ClientConnectionView {


    private GameManger gameManger;

    public ClientConnectionViewSolo(GameManger gameManger) {
        this.gameManger = gameManger;
    }

    @Override
    public void update(PlayerMove message) {
        new Thread(() -> {
            gameManger.update(message);
        }).start();
    }
}
