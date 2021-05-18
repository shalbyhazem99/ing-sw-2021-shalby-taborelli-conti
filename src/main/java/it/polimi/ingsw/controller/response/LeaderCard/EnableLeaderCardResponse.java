package it.polimi.ingsw.controller.response.LeaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class EnableLeaderCardResponse extends MoveResponse {

    private int leaderCardPosition;
    /**
     * Default constructor
     */
    public EnableLeaderCardResponse(ArrayList<Player> players, int executePlayerPos, int leaderCardPosition) {
        super(players, executePlayerPos);
        this.leaderCardPosition = leaderCardPosition;
    }


    public static EnableLeaderCardResponse getInstance(ArrayList<Player> players, int executePlayerPos, int leaderCardPosition) {
        return new EnableLeaderCardResponse(players,executePlayerPos,leaderCardPosition);
    }

    /*@Override
    public void updateLocalMatch(Match match) {
        match.enableLeaderCardInteraction(leaderCardPosition,match.getPlayerFromPosition(leaderCardPosition),true);
    }*/

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        //updateLocalMatch(match);
       System.out.println("Leader Card enabled");
       return null;
    }
}
