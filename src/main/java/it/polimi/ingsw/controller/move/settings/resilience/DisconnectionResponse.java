package it.polimi.ingsw.controller.move.settings.resilience;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.view.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class DisconnectionResponse extends MoveResponse {
    private String playerName;

    public DisconnectionResponse(String playerName, ArrayList<Player> players, int executePlayerPos, int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
        this.playerName = playerName;
    }

    public static DisconnectionResponse getInstance(String playerName, ArrayList<Player> players, int executePlayerPos, int hashToVerify) {
        return new DisconnectionResponse(playerName,players,executePlayerPos,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.disconnectPlayer(playerName,true);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        System.out.println(playerName+ "is offline");
        return null;
    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.manageDisconnection(playerName);
    }

    @Override
    public String toString() {
        return playerName;
    }
}
