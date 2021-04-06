package it.polimi.ingsw.controller.move.response;

import it.polimi.ingsw.controller.move.MoveResponse;

public class IllegalMoveResponse implements MoveResponse {
    String message; //todo:or type;

    public IllegalMoveResponse(String message) {
        this.message = message;
    }

    public static IllegalMoveResponse getInstance(String message) {
        return new IllegalMoveResponse(message);
    }
}
