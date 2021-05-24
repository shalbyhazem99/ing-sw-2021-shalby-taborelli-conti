package it.polimi.ingsw.controller.move.moveResources;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveResourcesResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to swap two {@link it.polimi.ingsw.model.Warehouse}
     * The numberOfResourcesMoved represents how many {@link Resource} have been totally moved between the two {@link it.polimi.ingsw.model.Warehouse}
     */
    private int numberOfResourcesMoved;
    private int indexFirstWarehouse;
    private int indexSecondWarehouse;


    public MoveResourcesResponse(ArrayList<Player> players, int executePlayerPos, int hashToVerify, int numberOfResourcesMoved, int indexFirstWarehouse, int indexSecondWarehouse) {
        super(players, executePlayerPos, hashToVerify);
        this.numberOfResourcesMoved = numberOfResourcesMoved;
        this.indexFirstWarehouse = indexFirstWarehouse;
        this.indexSecondWarehouse = indexSecondWarehouse;
    }


    /**
     * Default get instance Method
     * @param players
     * @return an instance of the class
     */
    public static MoveResourcesResponse getInstance(ArrayList<Player> players, int executePlayerPos, int hashToVerify, int numberOfResourcesMoved, int indexFirstWarehouse, int indexSecondWarehouse) {
        return new MoveResourcesResponse(players, executePlayerPos, hashToVerify, numberOfResourcesMoved, indexFirstWarehouse, indexSecondWarehouse);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.MoveResourcesInteraction(indexFirstWarehouse,indexSecondWarehouse,numberOfResourcesMoved,numberOfResourcesMoved,match.getPlayerFromPosition(getExecutePlayerPos()),true);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }
}
