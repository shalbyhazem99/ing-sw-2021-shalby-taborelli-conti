package it.polimi.ingsw.controller.move.swapWarehouse;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveResourcesResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to move {@link Resource} two {@link it.polimi.ingsw.model.Warehouse}
     */

    public MoveResourcesResponse(ArrayList<Player> players) {
        super(players);
    }

    /**
     * Default get instance Method
     * @param players
     * @return an instance of the class
     */
    public static MoveResourcesResponse getInstance(ArrayList<Player> players) {
        return new MoveResourcesResponse(players);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }
}
