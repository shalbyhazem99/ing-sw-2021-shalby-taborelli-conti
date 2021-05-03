package it.polimi.ingsw.controller.move.swapWarehouse;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class SwapWarehouseResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to swap two {@link it.polimi.ingsw.model.Warehouse}
     * The numberOfResourcesMoved represents how many {@link Resource} have been totally moved between the two {@link it.polimi.ingsw.model.Warehouse}
     */
    private int numberOfResourcesMoved;

    public SwapWarehouseResponse(ArrayList<Player> players, int numberOfResourcesMoved) {
        super(players);
        this.numberOfResourcesMoved = numberOfResourcesMoved;
    }

    /**
     * Default get instace Method
     * @param players
     * @param numberOfResourcesMoved
     * @return
     */
    public static SwapWarehouseResponse getInstance(ArrayList<Player> players, int numberOfResourcesMoved) {
        return new SwapWarehouseResponse(players,numberOfResourcesMoved);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }
}
