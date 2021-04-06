package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;

import java.util.Scanner;

public class AskForMove extends MoveResponse {
    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }
}
