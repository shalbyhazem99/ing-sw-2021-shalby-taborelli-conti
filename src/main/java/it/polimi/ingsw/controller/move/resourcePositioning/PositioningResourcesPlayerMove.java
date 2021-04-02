package it.polimi.ingsw.controller.move.resourcePositioning;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.MoveType;

import java.util.ArrayList;

public class PositioningResourcesPlayerMove extends PlayerMove {
    private ArrayList<Integer> whereToPlaceResources;
    Player p; //va tolto


    /**
     * Default constructor
     * @param whereToPlaceResources an {@link ArrayList} of {@link Integer} that has the same size as the {@link ArrayList} of {@link it.polimi.ingsw.model.Resource}
     *                              waiting to be placed, the value contained in the position i indicates where to add the iTH Resource,
     *                              the value can be {0,1,2,3,4,null}, {0,1,2} refers to standard {@link it.polimi.ingsw.model.Warehouse} {3,4} to additional {@link it.polimi.ingsw.model.Warehouse}
     *                              null means that the Resource has to be discarded
     * @param name_of_user {@link String} of user
     */
    public PositioningResourcesPlayerMove(ArrayList<Integer> whereToPlaceResources, String name_of_user) throws Exception {
        super(name_of_user);
        if(whereToPlaceResources==null||!check(whereToPlaceResources))
        {
            throw new Exception("Selected not existing warehouse!");
        }
        this.whereToPlaceResources = (ArrayList<Integer>)whereToPlaceResources.clone();
    }

    /**
     * Default method getInstance
     * @param whereToPlaceResources an {@link ArrayList} of {@link Integer}
     * @param name_of_user {@link String} of user
     * @return an instance of {@link PositioningResourcesPlayerMove}
     */
    public PositioningResourcesPlayerMove getInstance(ArrayList<Integer> whereToPlaceResources, String name_of_user) throws Exception
    {
        return new PositioningResourcesPlayerMove(whereToPlaceResources, name_of_user);
    }

    @Override
    public void execute(Match match) {
        try {
            match.positioningResourcesInteraction(whereToPlaceResources, p);
        }catch (Exception e) {
            e.printStackTrace();
            //todo:ask to make another move
        }
    }

    public boolean check(ArrayList<Integer> whereToPlaceResources)
    {
        boolean somethingAddedToFirstAdditionalWarehouse = whereToPlaceResources.indexOf(3)!=-1;
        boolean somethingAddedToSecondAdditionalWarehouse = whereToPlaceResources.indexOf(4)!=-1;
        if(somethingAddedToFirstAdditionalWarehouse)
        {
            if(p.getWarehousesAdditional().size()<1) {return false;}
        }
        if(somethingAddedToSecondAdditionalWarehouse)
        {
            if(p.getWarehousesAdditional().size()<2) {return false;}
        }
        return true;
    }
}
