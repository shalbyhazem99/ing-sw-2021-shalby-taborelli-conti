package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class SendModel extends MoveResponse {
    Match match;

    public SendModel(Match match, ArrayList<Player> players) {
        super(players);
        this.match = match;
    }

    public static SendModel getInstance(Match match,ArrayList<Player> players) {
        return new SendModel(match,players);
    }
    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        //TODO: require which to discard
        return null;
    }

    public Match getMatch() {
        return match;
    }
}
