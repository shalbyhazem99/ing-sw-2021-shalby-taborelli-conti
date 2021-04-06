package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AskForData extends MoveResponse {
    private String message;

    public AskForData(String message, ArrayList<Player> players) {
        super(players);
        this.message = message;
    }

    public static AskForData getInstance(String message,ArrayList<Player> players) {
        return new AskForData(message,players);
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
