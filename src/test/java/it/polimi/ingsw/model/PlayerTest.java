package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.leaderCard.*;
import it.polimi.ingsw.utils.Utils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;


public class PlayerTest extends TestCase {

    /**
     * Testing the correct operation of the method
     * 1) Testing the moving ahead when the {@link Player} stays under the 20 points
     * 2) Testing the moving ahead when the {@link Player} goes over the 20 points
     */
    @Test
    public void testMoveAheadFaith() {
        Player tester = new Player("tester");

        // 1) Testing the moving ahead when the {@link Player} stays under the 20 points
        tester.moveAheadFaith(1);
        assertEquals(1, tester.getPosFaithMarker());
        // 2) Testing the moving ahead when the {@link Player} goes over the 20 points
        tester.moveAheadFaith(20);
        assertEquals(Utils.FAITH_LENGTH, tester.getPosFaithMarker());
    }

    /*
    public boolean canAfford(ArrayList<ResourcePick> resourceToUse) {
        Gson gson = new Gson();
        String warehouseStandard = gson.toJson(getWarehousesStandard());
        String warehouseAdditional = gson.toJson(getWarehousesAdditional());
        String strongBox = gson.toJson(getStrongBox());
        ArrayList<Warehouse> standardTemp = getWarehousesStandard();
        ArrayList<Warehouse> additionalTemp = getWarehousesAdditional();
        ArrayList<Resource> strongBoxTemp = getStrongBox();

        //check if the player has the resource
        for (ResourcePick resourcePick : resourceToUse) {
            ResourceType resourceType = resourcePick.getResourceType();
            ArrayList<Resource> resToBeRemoved = null;
            switch (resourcePick.getResourceWarehouseType()) {
                case WAREHOUSE:
                    if (resourcePick.getWarehousePosition() >= 0 && resourcePick.getWarehousePosition() < 3) {
                        resToBeRemoved = standardTemp.get(resourcePick.getWarehousePosition()).getResources();
                    } else if (additionalTemp.size() > resourcePick.getWarehousePosition() - 3 && resourcePick.getWarehousePosition() >= 3 && resourcePick.getWarehousePosition() < 5) {
                        resToBeRemoved = additionalTemp.get(resourcePick.getWarehousePosition() - 3).getResources();
                    } else {
                        return false;
                    }
                    break;
                case STRONGBOX:
                    resToBeRemoved = strongBoxTemp;
                    break;
            }
            if (!resToBeRemoved.remove(Resource.getInstance(resourceType))) {
                ArrayList<Warehouse> temp = new ArrayList<>();
                Collections.addAll(temp, gson.fromJson(warehouseStandard, Warehouse[].class));
                setWarehousesStandard(temp);
                temp = new ArrayList<>();
                Collections.addAll(temp, gson.fromJson(warehouseAdditional, Warehouse[].class));
                setWarehousesAdditional(temp);
                ArrayList<Resource> temp2 = new ArrayList<>();
                Collections.addAll(temp2, gson.fromJson(strongBox, Resource[].class));
                setStrongBox(temp2);
                return false;
            }
        }
        return true;
    }
     */
    @Test
    public void testCanAfford() {

    }

    /**
     * Testing the correct operation of the method
     * 1) Testing when the {@link Player} has no discounts
     * 2) Testing when the {@link Player} has one or more discounts
     */
    @Test
    public void testHasDiscount() {
        Player tester = new Player("Tester");

        // 1) Testing when the player has no discounts
        assertFalse(tester.hasDiscount());

        // 2) Testing when the player has one or more discounts
        tester.addDiscount(new ResourcesCount(2, ResourceType.SHIELD));
        assertTrue(tester.hasDiscount());
        tester.addDiscount(new ResourcesCount(1, ResourceType.STONE));
        assertTrue(tester.hasDiscount());
    }

    /**
     * Testing the method that check if a player can afford the price of a card
     * 1) Testing that a card that does not need anything can always be afforded
     * 2) Testing the case when the player has resources in the standard warehouses
     * 3) Testing the case when the player has resources in the additional warehouses
     * 4) Testing the case when the player has resources in the strongbox
     * 5) testing the case when the player can not afford the price of a card
     */
    public void testIsActionable() {
        Player tester = new Player("tester");
        ArrayList<ResourcesCount> resourcesCountArrayList = new ArrayList<>();

        //1) Testing that a card that does not need anything can always be afforded
        assertTrue(tester.isActionable(resourcesCountArrayList));

        //2) Testing the case when the player has resources in the standard warehouses
        resourcesCountArrayList.add(new ResourcesCount(1, ResourceType.STONE));
        tester.addResourceToWarehouseStandard(new Resource(ResourceType.STONE), 0);
        assertTrue(tester.isActionable(resourcesCountArrayList));
        resourcesCountArrayList.add(new ResourcesCount(1, ResourceType.SHIELD));
        tester.addResourceToWarehouseStandard(new Resource(ResourceType.SHIELD), 1);
        assertTrue(tester.isActionable(resourcesCountArrayList));
        resourcesCountArrayList.add((new ResourcesCount(1, ResourceType.COIN)));
        tester.addResourceToWarehouseStandard(new Resource(ResourceType.COIN), 2);
        assertTrue(tester.isActionable(resourcesCountArrayList));

        //3) Testing the case when the player has resources in the additional warehouses
        resourcesCountArrayList.add(ResourcesCount.getInstance(1, ResourceType.SERVANT));
        tester.addAdditionalWarehouse(Warehouse.getInstance(2, ResourceType.SERVANT));
        tester.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.SERVANT), 0);
        assertTrue(tester.isActionable(resourcesCountArrayList));

        //4) Testing the case when the player has resources in the strongbox
        resourcesCountArrayList.add(ResourcesCount.getInstance(1, ResourceType.COIN));
        tester.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        //todo: vedere con la correzione di shalby
        //assertTrue(tester.isActionable(resourcesCountArrayList));

        //5) testing the case when the player can not afford the price of a card
        resourcesCountArrayList.add(ResourcesCount.getInstance(3, ResourceType.FAITH));
        assertFalse(tester.isActionable(resourcesCountArrayList));
    }


    /**
     * Class used to test the discarding of a {@link LeaderCard}
     * 1) Testing player can't discard anything if hasn't any LeaderCard
     * 2) Testing the normal workflow of the method
     * 3) Testing the method in a case where the player has many LeaderCards, where the player has 4 Leader Card
     * 4) Testing an activated LeaderCard can't be discarded
     */
    @Test
    public void testDiscardLeaderCard() {
        Player tester = new Player("tester");

        //Testing player can't discard anything if hasn't any LeaderCard
        assertFalse(tester.discardLeaderCard(0));

        //Testing the normal workflow of the method
        tester.addLeaderCard(LeaderCardAddProductive.getInstance(2, null, null, null));
        assertFalse(tester.discardLeaderCard(2));
        assertTrue(tester.discardLeaderCard(0));
        assertEquals(0, tester.getLeaderCards().size());
        assertEquals(1, tester.getPosFaithMarker());

        //Testing the method in a case where the player has many LeaderCards, where the player has 4 LeaderCard
        tester.addLeaderCard(LeaderCardAddProductive.getInstance(2, null, null, null));
        tester.addLeaderCard(LeaderCardAddWarehouse.getInstance(4, null, null, null));
        tester.addLeaderCard(LeaderCardDiscount.getInstance(3, null, null, null));
        tester.addLeaderCard(LeaderCardColor.getInstance(1, null, null, null));
        assertTrue(tester.discardLeaderCard(3));
        assertEquals(3, tester.getLeaderCards().size());
        assertEquals(2, tester.getPosFaithMarker());
        assertTrue(tester.discardLeaderCard(2));
        assertEquals(2, tester.getLeaderCards().size());
        assertEquals(3, tester.getPosFaithMarker());

        //Testing an activated LeaderCard can't be discarded
        tester.getLeaderCards().get(0).active(tester);
        assertFalse(tester.discardLeaderCard(0));
        assertEquals(2, tester.getLeaderCards().size());
        assertEquals(3, tester.getPosFaithMarker());
    }

    /**
     * Testing the correct assignment of a {@link DevelopmentCard}
     * 1)Testing when a {@link DevelopmentCard} in a wrong space
     * 2)Testing that {@link Player} add only one LEVEL.FIRST DevelopmentCard per {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     * 3)Testing that {@link Player} add only one LEVEL.SECOND DevelopmentCard per {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     * 4)Testing that {@link Player} add only one LEVEL.THIRD DevelopmentCard per {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     */
    @Test
    public void testAddDevelopmentCard(){
        Player tester = new Player("tester");

        //Testing when a Development card in a wrong space
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),5));

        //Testing that player add only one LEVEL.FIRST DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),0));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),0));
        assertEquals(1, tester.getDevelopmentCards().size());
        //DevSpace1
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),1));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),1));
        assertEquals(2, tester.getDevelopmentCards().size());
        //DevSpace2
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),2));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),2));
        assertEquals(3, tester.getDevelopmentCards().size());

        //Testing that player add only one LEVEL.SECOND DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),0));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),0));
        assertEquals(4, tester.getDevelopmentCards().size());
        //DevSpace1
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),1));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),1));
        assertEquals(5, tester.getDevelopmentCards().size());
        //DevSpace2
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),2));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),2));
        assertEquals(6, tester.getDevelopmentCards().size());

        //Testing that player add only one LEVEL.THIRD DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),0));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),0));
        assertEquals(7, tester.getDevelopmentCards().size());
        //DevSpace1
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),1));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),1));
        assertEquals(8, tester.getDevelopmentCards().size());
        //DevSpace2
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null),2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null),2));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null),2));
        assertEquals(9, tester.getDevelopmentCards().size());
    }

    /**
     * Testing when a player can add a {@link DevelopmentCard}
     * 1)Testing when a {@link DevelopmentCard} in a wrong space
     * 2)Testing that {@link Player} can add only one LEVEL.FIRST DevelopmentCard per {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     * 3)Testing that {@link Player} can add only one LEVEL.SECOND DevelopmentCard per {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     * 4)Testing that {@link Player} can add only one LEVEL.THIRD DevelopmentCard per {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     */
    @Test
    public void testDevelopmentCardCanBeAdded() {
        Player tester = new Player("tester");

        // Testing when a wrong position is given the method returns false
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 3));

        //Testing that player can add only one LEVEL.FIRST DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0);
        //DevSpace1
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1);
        //DevSpace2
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2);

        //Testing that player can add only one LEVEL.SECOND DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0);
        //DevSpace1
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1);
        //DevSpace2
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2);

        //Testing that player can add only one LEVEL.THIRD DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0);
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        //DevSpace1
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1);
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        //DevSpace2
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        assertTrue(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
        tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2);
        assertFalse(tester.developmentCardCanBeAdded(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
    }

    @Test
    public void testSwapWareHouse(){
        Player tester = new Player("tester");

        //Testing the swapping between Standard Warehouses empty
        assertEquals(0, tester.swapWarehouses(0, 1));
        assertEquals(0, tester.swapWarehouses(2, 1));
        assertEquals(0, tester.swapWarehouses(0, 2));

        //Testing the swapping between Standard Warehouses not empty
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN),0);
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD),1);
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT),2);
        assertEquals(2,tester.swapWarehouses(0,1));
        // 0) SHIELD 1) COIN 2)SERVANT
        assertTrue(tester.getWarehousesStandard().get(0).getResource(Resource.getInstance(ResourceType.SHIELD)));
        assertTrue(tester.getWarehousesStandard().get(1).getResource(Resource.getInstance(ResourceType.COIN)));
        assertEquals(2,tester.swapWarehouses(0,2));
        // 0) SERVANT 1) COIN 2)SHIELD
        assertTrue(tester.getWarehousesStandard().get(0).getResource(Resource.getInstance(ResourceType.SERVANT)));
        assertTrue(tester.getWarehousesStandard().get(2).getResource(Resource.getInstance(ResourceType.SHIELD)));

    }
}



