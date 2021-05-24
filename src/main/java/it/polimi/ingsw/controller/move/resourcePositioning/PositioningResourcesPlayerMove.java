package it.polimi.ingsw.controller.move.resourcePositioning;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;

import java.util.ArrayList;

public class PositioningResourcesPlayerMove extends PlayerMove {
    private ArrayList<Integer> whereToPlaceResources;

    /**
     * Default constructor
     * @param whereToPlaceResources an {@link ArrayList} of {@link Integer} that has the same size as the {@link ArrayList} of {@link it.polimi.ingsw.model.Resource}
     *                              waiting to be placed, the value contained in the position i indicates where to add the iTH Resource,
     *                              the value can be {0,1,2,3,4,null}, {0,1,2} refers to standard {@link it.polimi.ingsw.model.Warehouse} {3,4} to additional {@link it.polimi.ingsw.model.Warehouse}
     *                              null means that the Resource has to be discarded
     */
    public PositioningResourcesPlayerMove(ArrayList<Integer> whereToPlaceResources) throws Exception {
        if(whereToPlaceResources==null||!check(whereToPlaceResources))
        {
            throw new Exception("Selected not existing warehouse!");
        }
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

    /**
     * Method to check if an invalid {@link it.polimi.ingsw.model.Warehouse} is contained in the whereToPlaceResources {@link ArrayList}
     * @param whereToPlaceResources
     * @return
     */
    public boolean check(ArrayList<Integer> whereToPlaceResources)
    {
        boolean somethingAddedToFirstAdditionalWarehouse = whereToPlaceResources.indexOf(3)!=-1;
        boolean somethingAddedToSecondAdditionalWarehouse = whereToPlaceResources.indexOf(4)!=-1;
        if(somethingAddedToFirstAdditionalWarehouse)
        {
            //TODO --> player è NULL e mi da problemi
            if(player.getWarehousesAdditional().size()<1) {return false;}
        }
        if(somethingAddedToSecondAdditionalWarehouse)
        {
            if(player.getWarehousesAdditional().size()<2) {return false;}
        }
        return true;
    }
}
