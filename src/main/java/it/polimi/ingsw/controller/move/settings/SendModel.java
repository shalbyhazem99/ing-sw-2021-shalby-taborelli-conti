package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;

import java.util.Scanner;

public class SendModel extends MoveResponse {
    Match match;

    public SendModel(Match match) {
        this.match = match;
    }

    public static SendModel getInstance(Match match) {
        return new SendModel(match);
    }
    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        //TODO: require which to discard
        return null;
    }

    public Match getMatch() {
        return match;
    }
}
