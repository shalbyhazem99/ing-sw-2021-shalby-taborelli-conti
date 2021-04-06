package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;

import java.util.Scanner;

public class AskForData extends MoveResponse {
    private String message;

    public AskForData(String message) {
        this.message = message;
    }

    public static AskForData getInstance(String message) {
        return new AskForData(message);
    }
    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return MessageMove.getInstance(message);
    }

    @Override
    public String toString() {
        return message;
    }
}
