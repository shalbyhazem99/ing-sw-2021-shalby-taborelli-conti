package it.polimi.ingsw.controller.move.resourcePositioning;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;

public class PositioningResourcesResponse implements MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} asks to position his {@link it.polimi.ingsw.model.Resource}
     */
    private int numberOfDiscardedResources;
    private int numberOfGainedResources;


    /**
     * Default constructor
     * @param numberOfDiscardedResources how many {@link it.polimi.ingsw.model.Resource} got discarded
     * @param numberOfGainedResources how many {@link it.polimi.ingsw.model.Resource} got gained
     */
    public PositioningResourcesResponse(int numberOfDiscardedResources, int numberOfGainedResources) {
        this.numberOfDiscardedResources = numberOfDiscardedResources;
        this.numberOfGainedResources = numberOfGainedResources;
    }

    /**
     * Default get instance method
     * @param numberOfDiscardedResources how many {@link it.polimi.ingsw.model.Resource} got discarded
     * @param numberOfGainedResources how many {@link it.polimi.ingsw.model.Resource} got gained
     * @return an instance of {@link PositioningResourcesResponse}
     */
    public static PositioningResourcesResponse getInstance(int numberOfDiscardedResources, int numberOfGainedResources) {
        return new PositioningResourcesResponse(numberOfDiscardedResources,numberOfGainedResources);
    }
}
