package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class MoveResponse implements Serializable {
    //todo: method that indicate something that the user must do in response to a GameManagerResponse
    public abstract PlayerMove elaborateCliInput(String message, final Scanner stdin);

    public ArrayList<Player> getPlayers(){
        //todo:to ibe implemented
        return null;
    }
}