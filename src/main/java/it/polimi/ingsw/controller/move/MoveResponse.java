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

    public MoveResponse(ArrayList<Player> players, int executePlayerPos) {
        this.players = players;
        this.executePlayerPos = executePlayerPos;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public abstract PlayerMove elaborateCliInput(final Scanner stdin, Match match);

    //todo:to uncomment
    //public abstract void updateLocalMatch(Match match);
}
