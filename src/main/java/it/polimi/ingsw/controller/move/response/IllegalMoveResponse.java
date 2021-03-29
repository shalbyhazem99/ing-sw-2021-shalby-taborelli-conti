package it.polimi.ingsw.controller.move.response;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class IllegalMoveResponse implements MoveResponse {
    public static IllegalMoveResponse getInstance() {
        return new IllegalMoveResponse();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return null;
    }
}
