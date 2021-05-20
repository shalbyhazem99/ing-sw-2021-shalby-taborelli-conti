package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.MatchProxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class MoveResponse implements Serializable {
    private ArrayList<Player> players;
    private int executePlayerPos;
    private long hashToVerify;

    public MoveResponse(ArrayList<Player> players, int executePlayerPos,int hashToVerify) {
        this.players = players;
        this.executePlayerPos = executePlayerPos;
        this.hashToVerify = hashToVerify;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getExecutePlayerPos() {
        return executePlayerPos;
    }

    public long getHashToVerify() {
        return hashToVerify;
    }

    public abstract PlayerMove elaborateCliInput(final Scanner stdin, Match match);

    public abstract void updateLocalMatch(Match match);
}
