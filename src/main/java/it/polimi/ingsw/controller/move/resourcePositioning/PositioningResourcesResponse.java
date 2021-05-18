package it.polimi.ingsw.controller.move.resourcePositioning;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class PositioningResourcesResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} asks to position his {@link it.polimi.ingsw.model.Resource}
     */
    private int numberOfDiscardedResources;
    private int numberOfGainedResources;
    ArrayList<Integer> whereToPlace;


    /**
     * Default constructor
     * @param numberOfDiscardedResources how many {@link it.polimi.ingsw.model.Resource} got discarded
     * @param numberOfGainedResources how many {@link it.polimi.ingsw.model.Resource} got gained
     */
    public PositioningResourcesResponse(int numberOfDiscardedResources, int numberOfGainedResources, ArrayList<Player> players,int executePlayerPos,ArrayList<Integer> whereToPlace,int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
        this.numberOfDiscardedResources = numberOfDiscardedResources;
        this.numberOfGainedResources = numberOfGainedResources;
        this.whereToPlace=whereToPlace;
    }

    /**
     * Default get instance method
     * @param numberOfDiscardedResources how many {@link it.polimi.ingsw.model.Resource} got discarded
     * @param numberOfGainedResources how many {@link it.polimi.ingsw.model.Resource} got gained
     * @return an instance of {@link PositioningResourcesResponse}
     */
    public static PositioningResourcesResponse getInstance(int numberOfDiscardedResources, int numberOfGainedResources, ArrayList<Player> players,int executePlayerPos,ArrayList<Integer> whereToPlace,int hashToVerify) {
        return new PositioningResourcesResponse(numberOfDiscardedResources,numberOfGainedResources,players,executePlayerPos,whereToPlace,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {
        match.positioningResourcesInteraction(whereToPlace,match.getPlayerFromPosition(getExecutePlayerPos()),true);
    }

    @Override
    public PlayerMove elaborateCliInput( Scanner stdin, Match match) {
        return null;
    }
}
