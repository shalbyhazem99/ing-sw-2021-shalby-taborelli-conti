package it.polimi.ingsw.controller.move.response;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class IllegalMoveResponse extends MoveResponse {
    String message; //todo:or type;

    public IllegalMoveResponse(String message, ArrayList<Player> players) {
        super(players);
        this.message = message;
    }

    public static IllegalMoveResponse getInstance(String message,ArrayList<Player> players) {
        return new IllegalMoveResponse(message,players);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }
}
