package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class SendMessage extends MoveResponse {
    private String message;

    public SendMessage(String message, ArrayList<Player> players) {
        super(players);
        this.message = message;
    }

    public static SendMessage getInstance(String message, ArrayList<Player> players) {
        return new SendMessage(message,players);
    }
    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }

    @Override
    public String toString() {
        return message;
    }
}
