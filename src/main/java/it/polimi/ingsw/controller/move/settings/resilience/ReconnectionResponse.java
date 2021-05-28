package it.polimi.ingsw.controller.move.settings.resilience;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class ReconnectionResponse extends MoveResponse {
    private String playerName;

    public ReconnectionResponse(String playerName, ArrayList<Player> players, int executePlayerPos, int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
        this.playerName = playerName;
    }

    public static ReconnectionResponse getInstance(String playerName, ArrayList<Player> players, int executePlayerPos, int hashToVerify) {
        return new ReconnectionResponse(playerName,players,executePlayerPos,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.ReconnectPlayer(playerName,true);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        System.out.println(playerName+ "is back");
        return null;
    }

    @Override
    public String toString() {
        return playerName;
    }
}
