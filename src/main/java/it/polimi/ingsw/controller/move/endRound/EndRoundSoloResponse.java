package it.polimi.ingsw.controller.move.endRound;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class EndRoundSoloResponse extends EndRoundResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to swap two {@link it.polimi.ingsw.model.Warehouse}
     * The numberOfResourcesMoved represents how many {@link Resource} have been totally moved between the two {@link it.polimi.ingsw.model.Warehouse}
     */
    private String message;
    private ActionToken actionToken;

    public EndRoundSoloResponse(ArrayList<Player> players, int executePlayerPos, boolean correctlyEnded, int hashToVerify, String message, ActionToken actionToken) {
        super(players, executePlayerPos, correctlyEnded, hashToVerify);
        this.message = message;
        this.actionToken = actionToken;
    }


    public static EndRoundSoloResponse getInstance(ArrayList<Player> players, int executePlayerPos, boolean correctlyEnded, int hashToVerify, String message, ActionToken actionToken) {
        return new EndRoundSoloResponse(players, executePlayerPos, correctlyEnded, hashToVerify, message, actionToken);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.endRoundInteraction(match.getPlayerFromPosition(getExecutePlayerPos()),true);
        match.executeAction(actionToken,match.getPlayerFromPosition(getExecutePlayerPos()),true);
    }

    @Override
    public PlayerMove elaborateCliInput( Scanner stdin, Match match) {
        System.out.println(message);
        return null;
    }

    public String getMessage() {
        return message;
    }
}
