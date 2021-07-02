package it.polimi.ingsw.controller.move.endMatch;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EndMatchSoloResponse extends EndMatchResponse {

    boolean hasLose;

    public EndMatchSoloResponse(ArrayList<Player> players, int executePlayerPos, int hashToVerify, boolean hasLose) {
        super(players, executePlayerPos, hashToVerify);
        this.hasLose = hasLose;
    }

    public static EndMatchSoloResponse getInstance(ArrayList<Player> players, int executePlayerPos, int hashToVerify, boolean hasLos) {
        return new EndMatchSoloResponse(players, executePlayerPos, hashToVerify, hasLos);
    }


    @Override
    public void updateLocalMatch(Match match) {

    }


    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        if (hasLose) {
            System.out.println("You Lose");
        } else {
            ArrayList<Winner> winners = match.whoIsWinner();
            if (winners.size() == 1) {
                System.out.println("You Win!");
                System.out.println("You make: "+winners.get(0).getPoints()+" points");
            }
        }
        return null;
    }
}
