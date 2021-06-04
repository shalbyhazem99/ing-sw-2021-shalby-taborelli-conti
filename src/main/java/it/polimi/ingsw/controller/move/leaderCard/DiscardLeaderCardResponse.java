package it.polimi.ingsw.controller.move.leaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class DiscardLeaderCardResponse extends MoveResponse {

    private int leaderCardPosition;
    /**
     * Default constructor
     */
    public DiscardLeaderCardResponse(ArrayList<Player> players, int executePlayerPos, int leaderCardPosition,int hashToVerify) {
        super(players, executePlayerPos,hashToVerify);
        this.leaderCardPosition = leaderCardPosition;
    }


    public static DiscardLeaderCardResponse getInstance(ArrayList<Player> players, int executePlayerPos, int leaderCardPosition,int hashToVerify) {
        return new DiscardLeaderCardResponse(players,executePlayerPos,leaderCardPosition,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.discardLeaderCardInteraction(leaderCardPosition,match.getPlayerFromPosition(getExecutePlayerPos()),true);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        //updateLocalMatch(match);
       System.out.println("Leader Card discarded, moved 1 position the faith path");
       return null;
    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.discardLeaderCard(leaderCardPosition,getExecutePlayerPos());
    }
}
