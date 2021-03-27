package it.polimi.ingsw.controller.move.response;

public class IllegalMoveResponse implements MoveResponse{
    public static IllegalMoveResponse getInstance() {
        return new IllegalMoveResponse();
    }
}
