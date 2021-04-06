package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;

import java.util.Scanner;

public class SendMessage extends MoveResponse {
    private String message;

    public SendMessage(String message) {
        this.message = message;
    }

    public static SendMessage getInstance(String message) {
        return new SendMessage(message);
    }
    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }

    @Override
    public String toString() {
        return message;
    }
}
