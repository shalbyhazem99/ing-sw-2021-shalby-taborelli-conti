package it.polimi.ingsw.controller.move.endRound;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class EndRoundResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to swap two {@link it.polimi.ingsw.model.Warehouse}
     * The numberOfResourcesMoved represents how many {@link Resource} have been totally moved between the two {@link it.polimi.ingsw.model.Warehouse}
     */
    private boolean correctlyEnded;

    public EndRoundResponse(ArrayList<Player> players,int executePlayerPos, boolean correctlyEnded,int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
        this.correctlyEnded = correctlyEnded;
    }


    public static EndRoundResponse getInstance(ArrayList<Player> players,int executePlayerPos, boolean correctlyEnded,int hashToVerify) {
        return new EndRoundResponse(players, executePlayerPos, correctlyEnded,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.endRoundInteraction(match.getPlayerFromPosition(getExecutePlayerPos()));
    }

    @Override
    public PlayerMove elaborateCliInput( Scanner stdin, Match match) {
        return null;
    }
}
