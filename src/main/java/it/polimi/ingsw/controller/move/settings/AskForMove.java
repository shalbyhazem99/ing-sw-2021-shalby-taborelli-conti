package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AskForMove extends MoveResponse {
    public AskForMove(ArrayList<Player> players) {
        super(players);
    }

    public static AskForMove getInstance(ArrayList<Player> players) {
        return new AskForMove(players);
    }

    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }
}
