package it.polimi.ingsw.controller.move.response;

import it.polimi.ingsw.controller.move.MoveResponse;

public class IllegalMoveResponse implements MoveResponse {
    public static IllegalMoveResponse getInstance() {
        return new IllegalMoveResponse();
    }
}
