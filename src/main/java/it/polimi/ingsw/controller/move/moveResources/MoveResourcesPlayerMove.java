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
    int how_many_resources_first;
    int how_many_resources_second;

    /**
     * Default constructor
     * @param firstWarehouseIndex an int value to identify the first {@link it.polimi.ingsw.model.Warehouse}
     * @param secondWarehouseIndex an int value to identify the second {@link it.polimi.ingsw.model.Warehouse}
     * @param how_many_resources_first an int value to indicate the number of {@link it.polimi.ingsw.model.Resource} to move
     */
    public MoveResourcesPlayerMove(int firstWarehouseIndex, int secondWarehouseIndex, int how_many_resources_first, int how_many_resources_second) {
        this.firstWarehouseIndex = firstWarehouseIndex;
        this.secondWarehouseIndex = secondWarehouseIndex;
        this.how_many_resources_first = how_many_resources_first;
        this.how_many_resources_second = how_many_resources_second;
    }

    public static MoveResourcesPlayerMove getInstance(int firstWarehouseIndex, int secondWarehouseIndex, int how_many_resources_first,int how_many_resources_second) {
        return new MoveResourcesPlayerMove(firstWarehouseIndex, secondWarehouseIndex, how_many_resources_first,how_many_resources_second);
    }

    @Override
    public void execute(Match match){
        match.MoveResourcesInteraction(firstWarehouseIndex,secondWarehouseIndex, how_many_resources_first,how_many_resources_second,getPlayer(),false);
    }
}
