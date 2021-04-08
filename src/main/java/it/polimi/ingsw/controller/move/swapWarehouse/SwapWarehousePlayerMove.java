package it.polimi.ingsw.controller.move.swapWarehouse;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.SwapWarehouseException;
import it.polimi.ingsw.model.Match;

public class SwapWarehousePlayerMove extends PlayerMove {
    /**
     * Class representing the {@link PlayerMove} performed by the {@link it.polimi.ingsw.model.Player} in order to swap two {@link it.polimi.ingsw.model.Warehouse}
     * Indexes of warehouses can be {0,1,2} for standard warehouses or {3,4} for additional warehouses
     */
    int firstWarehouseIndex;
    int secondWarehouseIndex;

    /**
     * Default constructor
     * @param firstWarehouseIndex an int value to identify the first {@link it.polimi.ingsw.model.Warehouse}
     * @param secondWarehouseIndex an int value to identify the second {@link it.polimi.ingsw.model.Warehouse}
     */
    public SwapWarehousePlayerMove(int firstWarehouseIndex, int secondWarehouseIndex) {
        this.firstWarehouseIndex = firstWarehouseIndex;
        this.secondWarehouseIndex = secondWarehouseIndex;
    }

    @Override
    public void execute(Match match) {
        try
        {
            match.swapWarehouseInteraction(firstWarehouseIndex,secondWarehouseIndex,getPlayer());
        }catch (SwapWarehouseException e)
        {
            e.printStackTrace();
        }
    }
}
