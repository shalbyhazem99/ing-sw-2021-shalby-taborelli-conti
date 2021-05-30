package it.polimi.ingsw.controller.move.endMatch;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EndMatchResponse extends MoveResponse {

    public EndMatchResponse(ArrayList<Player> players, int executePlayerPos, int hashToVerify) {
        super(players, executePlayerPos, hashToVerify);
    }

    public static EndMatchResponse getInstance(Player player, int executePlayerPos, int hashToVerify) {
        return new EndMatchResponse(new ArrayList<>(Arrays.asList(player)),executePlayerPos,hashToVerify);
    }


    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        System.out.println("Match End!");
        ArrayList<Winner> winners = match.whoIsWinner();
        if(winners.size()==1) {
            System.out.println("The winner is ");
        }
        else {
            System.out.println("The winners are");
        }
        for (Winner winner:winners) {
            System.out.println(winner.toString());
        }
        return null;
    }
}
