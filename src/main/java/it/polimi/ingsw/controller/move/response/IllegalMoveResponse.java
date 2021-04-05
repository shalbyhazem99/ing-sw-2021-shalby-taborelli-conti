package it.polimi.ingsw.controller.move.response;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;

import java.util.Scanner;

public class IllegalMoveResponse extends MoveResponse {
    String message; //todo:or type;

    public IllegalMoveResponse(String message) {
        this.message = message;
    }

    public static IllegalMoveResponse getInstance(String message) {
        return new IllegalMoveResponse(message);
    }

    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }
}
