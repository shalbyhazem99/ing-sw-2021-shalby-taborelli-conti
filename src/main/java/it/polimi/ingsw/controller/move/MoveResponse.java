package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public interface MoveResponse {
    //todo: method that indicate something that the user must do in response to a GameManagerResponse
    public ArrayList<Player> getPlayers();
}
