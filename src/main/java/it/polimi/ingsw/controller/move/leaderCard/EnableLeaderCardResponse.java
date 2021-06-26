package it.polimi.ingsw.controller.move.leaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.view.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class EnableLeaderCardResponse extends MoveResponse {

    private int leaderCardPosition;
    /**
     * Default constructor
     */
    public EnableLeaderCardResponse(ArrayList<Player> players, int executePlayerPos, int leaderCardPosition,int hashToVerify) {
        super(players, executePlayerPos,hashToVerify);
        this.leaderCardPosition = leaderCardPosition;
    }


    public static EnableLeaderCardResponse getInstance(ArrayList<Player> players, int executePlayerPos, int leaderCardPosition,int hashToVerify) {
        return new EnableLeaderCardResponse(players,executePlayerPos,leaderCardPosition,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.enableLeaderCardInteraction(leaderCardPosition,match.getPlayerFromPosition(getExecutePlayerPos()),true);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
       System.out.println("Leader Card enabled");
       return null;
    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.flipLeaderCard(leaderCardPosition,getExecutePlayerPos());
    }
}
