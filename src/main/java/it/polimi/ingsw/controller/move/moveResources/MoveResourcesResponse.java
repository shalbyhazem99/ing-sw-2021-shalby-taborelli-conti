package it.polimi.ingsw.controller.move.moveResources;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.view.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveResourcesResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to swap two {@link it.polimi.ingsw.model.Warehouse}
     * The numberOfResourcesMoved represents how many {@link Resource} have been totally moved between the two {@link it.polimi.ingsw.model.Warehouse}
     */
    private int num_from_first;
    private int num_from_second;
    private int indexFirstWarehouse;
    private int indexSecondWarehouse;

    public MoveResourcesResponse(ArrayList<Player> players, int executePlayerPos, int hashToVerify, int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse) {
        super(players, executePlayerPos, hashToVerify);
        this.num_from_first = num_from_first;
        this.num_from_second = num_from_second;
        this.indexFirstWarehouse = indexFirstWarehouse;
        this.indexSecondWarehouse = indexSecondWarehouse;
    }

    /**
     * Default get instance Method
     * @param players
     * @return an instance of the class
     */
    public static MoveResourcesResponse getInstance(ArrayList<Player> players, int executePlayerPos, int hashToVerify, int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse) {
        return new MoveResourcesResponse(players, executePlayerPos, hashToVerify, num_from_first,num_from_second, indexFirstWarehouse, indexSecondWarehouse);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.MoveResourcesInteraction(indexFirstWarehouse,indexSecondWarehouse, num_from_first, num_from_second,match.getPlayerFromPosition(getExecutePlayerPos()),true);
    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.moveResourceResponse(num_from_first,num_from_second, indexFirstWarehouse,indexSecondWarehouse);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }
}
