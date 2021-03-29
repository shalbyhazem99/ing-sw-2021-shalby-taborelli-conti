package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class MarketResponse implements MoveResponse {
    private ArrayList<Resource> resources;
    int numOfMarbleToBeCoverted;

    public MarketResponse(ArrayList<Resource> resources, int numOfMarbleToBeCoverted) {
        this.resources = resources;
        this.numOfMarbleToBeCoverted = numOfMarbleToBeCoverted;
    }

    public static MarketResponse getInstance(ArrayList<Resource> resources, int numOfMarbleToBeCoverted) {
        return new MarketResponse(resources,numOfMarbleToBeCoverted);
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return null;
    }
}
