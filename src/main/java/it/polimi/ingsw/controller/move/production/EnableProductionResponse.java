package it.polimi.ingsw.controller.move.production;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketMarbleConversionMove;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class EnableProductionResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} interacts with the ProductionSystem
     */
    private ArrayList<Resource> resourcesGot;

    /**
     * Default constructor
     * @param resourcesGot {@link ArrayList} containing the {@link Resource} obtained by the Production
     */
    public EnableProductionResponse(ArrayList<Resource> resourcesGot, ArrayList<Player> players) {
        super(players);
        this.resourcesGot = resourcesGot;
    }

    /**
     * Default getInstance
     * @param resourcesGot {@link ArrayList} containing the {@link Resource}
     * @return an istance of {@link EnableProductionResponse}
     */
    public static EnableProductionResponse getInstance(ArrayList<Resource> resourcesGot,ArrayList<Player> players) {
        return new EnableProductionResponse(resourcesGot,players);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin) {
        return null;
    }
}
