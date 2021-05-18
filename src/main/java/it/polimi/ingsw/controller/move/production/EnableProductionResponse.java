package it.polimi.ingsw.controller.move.production;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class EnableProductionResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} interacts with the ProductionSystem
     */
    private ProductivePower power;
    private ArrayList<ResourcePick> resourceToUse;

    public EnableProductionResponse(ProductivePower power, ArrayList<ResourcePick> resourceToUse,ArrayList<Player> players, int executePlayerPos) {
        super(players, executePlayerPos);
        this.power = power;
        this.resourceToUse = resourceToUse;
    }

    /**
     * Default getInstance
     * @return an istance of {@link EnableProductionResponse}
     */
    public static EnableProductionResponse getInstance(ProductivePower power, ArrayList<ResourcePick> resourceToUse,ArrayList<Player> players, int executePlayerPos) {
        return new EnableProductionResponse(power, resourceToUse, players, executePlayerPos);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }
}
