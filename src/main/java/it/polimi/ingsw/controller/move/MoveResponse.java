package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class MoveResponse implements Serializable {
    private ArrayList<Player> players;

    public MoveResponse(ArrayList<Player> players) {
        this.players = players;
    }

    public void concatPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    //todo: method that indicate something that the user must do in response to a GameManagerResponse
    public abstract PlayerMove elaborateCliInput(String message, final Scanner stdin);
}
