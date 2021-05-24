package it.polimi.ingsw.controller.move.resourcePositioning;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

public class PositioningResourcesPlayerMove extends PlayerMove {
    private ArrayList<Integer> whereToPlaceResources;

    /**
     * Default constructor
     * @param whereToPlaceResources an {@link ArrayList} of {@link Integer} that has the same size as the {@link ArrayList} of {@link Resource}
     *                              waiting to be placed, the value contained in the position i indicates where to add the iTH Resource,
     *                              the value can be {0,1,2,3,4,null}, {0,1,2} refers to standard {@link it.polimi.ingsw.model.Warehouse} {3,4} to additional {@link it.polimi.ingsw.model.Warehouse}
     *                              null means that the Resource has to be discarded
     */
    public PositioningResourcesPlayerMove(ArrayList<Integer> whereToPlaceResources) throws Exception {
        this.whereToPlaceResources = (ArrayList<Integer>)whereToPlaceResources.clone();
    }

    /**
     * Default method getInstance
     * @param whereToPlaceResources an {@link ArrayList} of {@link Integer}
     * @return an instance of {@link PositioningResourcesPlayerMove}
     */
    public static PositioningResourcesPlayerMove getInstance(ArrayList<Integer> whereToPlaceResources) throws Exception
    {
        return new PositioningResourcesPlayerMove(whereToPlaceResources);
    }

    @Override
    public void execute(Match match) {
            match.positioningResourcesInteraction(whereToPlaceResources, getPlayer(),false);
    }
}
