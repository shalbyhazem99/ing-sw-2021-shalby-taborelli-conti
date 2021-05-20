package it.polimi.ingsw.controller.move.moveResources;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;

public class MoveResourcesPlayerMove extends PlayerMove {
    /**
     * Class representing the {@link PlayerMove} performed by the {@link it.polimi.ingsw.model.Player} in order to move {@link it.polimi.ingsw.model.Resource} between two {@link it.polimi.ingsw.model.Warehouse}
     * Indexes of warehouses can be {0,1,2} for standard warehouses or {3,4} for additional warehouses
     *
     */
    int firstWarehouseIndex;
    int secondWarehouseIndex;
    int numberOfResources;

    /**
     * Default constructor
     * @param firstWarehouseIndex an int value to identify the first {@link it.polimi.ingsw.model.Warehouse}
     * @param secondWarehouseIndex an int value to identify the second {@link it.polimi.ingsw.model.Warehouse}
     * @param numberOfResources an int value to indicate the number of {@link it.polimi.ingsw.model.Resource} to move
     */
    public MoveResourcesPlayerMove(int firstWarehouseIndex, int secondWarehouseIndex, int numberOfResources) {
        this.firstWarehouseIndex = firstWarehouseIndex;
        this.secondWarehouseIndex = secondWarehouseIndex;
        this.numberOfResources = numberOfResources;
    }

    public static MoveResourcesPlayerMove getInstance(int firstWarehouseIndex, int secondWarehouseIndex, int numberOfResources) {
        return new MoveResourcesPlayerMove(firstWarehouseIndex, secondWarehouseIndex, numberOfResources);
    }

    @Override
    public void execute(Match match){
        match.MoveResourcesInteraction(firstWarehouseIndex,secondWarehouseIndex,numberOfResources,getPlayer(),false);

    }
}
