package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class MarketResponse implements MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} interacts with the {@link it.polimi.ingsw.model.market.MarketBoard}
     * This class can represent 2 different responses based on the kind of {@link it.polimi.ingsw.controller.move.PlayerMove}:
     * - When responding to a {@link MarketInteractionPlayerMove} the resources parameter will contain the {@link Resource} got by the conversion
     *   (ignoring the white {@link it.polimi.ingsw.model.market.Marble}) and the numOfMarbleToBeCoverted will contain how many white {@link it.polimi.ingsw.model.market.Marble}
     *   that were part of the row/column selected we can convert, numOfMarbleToBeCoverted >=0
     *   (! If the {@link it.polimi.ingsw.model.Player}
     * - When responding to a {@link MarketMarbleConversionMove} the resources parameter will contain the {@link Resource} got by the conversion
     *   of the white {@link it.polimi.ingsw.model.market.Marble} based on the {@link ArrayList} of {@link it.polimi.ingsw.model.ResourceType} previously
     *   specified and the
     *
     */
    private ArrayList<Resource> resources;
    int numOfMarbleToBeCoverted;

    /**
     * Default constructor
     * @param resources the {@link ArrayList} containing the {@link Resource} got by the conversion
     * @param numOfMarbleToBeCoverted the int which specifies how many Conversion Strategy the {@link it.polimi.ingsw.model.Player} has to communicate
     *                                to the system
     */
    public MarketResponse(ArrayList<Resource> resources, int numOfMarbleToBeCoverted) {
        this.resources = resources;
        this.numOfMarbleToBeCoverted = numOfMarbleToBeCoverted;
    }

    /**
     * Default get instance method
     * @param resources the {@link ArrayList} containing the {@link Resource} got by the conversion
     * @param numOfMarbleToBeCoverted the int which specifies how many Conversion Strategy the {@link it.polimi.ingsw.model.Player} has to communicate
     *                               to the system
     * @return an instance of {@link MarketResponse}
     */
    public static MarketResponse getInstance(ArrayList<Resource> resources, int numOfMarbleToBeCoverted) {
        return new MarketResponse(resources,numOfMarbleToBeCoverted);
    }
}
