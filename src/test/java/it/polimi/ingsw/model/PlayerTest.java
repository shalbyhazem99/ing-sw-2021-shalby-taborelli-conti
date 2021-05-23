package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.production.move.ResourceWarehouseType;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.leaderCard.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
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
        tester.moveAheadFaith(4);
        assertEquals(4, tester.getPosFaithMarker());
        // 2) Testing the moving ahead when the {@link Player} goes over the 20 points
        tester.moveAheadFaith(80);
        assertEquals(Utils.FAITH_LENGTH, tester.getPosFaithMarker());
    }

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
        /*Player tester = new Player("tester");
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
        assertTrue(tester.isActionable(resourcesCountArrayList));

        //5) testing the case when the player can not afford the price of a card
        resourcesCountArrayList.add(ResourcesCount.getInstance(3, ResourceType.FAITH));
        assertFalse(tester.isActionable(resourcesCountArrayList));
        */

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
    public void testAddDevelopmentCard() {
        Player tester = new Player("tester");

        //Testing when a Development card in a wrong space
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 5));

        //Testing that player add only one LEVEL.FIRST DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        assertEquals(1, tester.getDevelopmentCards().size());
        //DevSpace1
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        assertEquals(2, tester.getDevelopmentCards().size());
        //DevSpace2
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        assertEquals(3, tester.getDevelopmentCards().size());

        //Testing that player add only one LEVEL.SECOND DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        assertEquals(4, tester.getDevelopmentCards().size());
        //DevSpace1
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        assertEquals(5, tester.getDevelopmentCards().size());
        //DevSpace2
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        assertEquals(6, tester.getDevelopmentCards().size());

        //Testing that player add only one LEVEL.THIRD DevelopmentCard per DevelopmentCardSpace

        //DevSpace0
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 0));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 0));
        assertEquals(7, tester.getDevelopmentCards().size());
        //DevSpace1
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 1));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 1));
        assertEquals(8, tester.getDevelopmentCards().size());
        //DevSpace2
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, null, 0, null, null), 2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, null, 0, null, null), 2));
        assertTrue(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
        assertFalse(tester.addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, null, 0, null, null), 2));
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

    public void testTestCanAfford() {
        Player testPlayer = Player.getInstance("Tester");
        //base case: null
        assertFalse(testPlayer.canAfford(null));
        ArrayList<ResourcePick> resourceToUse = new ArrayList<>();
        //base case: empty
        assertTrue(testPlayer.canAfford(resourceToUse));
        //add element to resourceToUse
        // 1 coin, 2 shield from strongbox
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.SHIELD));
        //1 coin, 2 shield from standard warehouse
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 1, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2, ResourceType.SHIELD));

        assertFalse(testPlayer.canAfford(resourceToUse));
        //add Resource to StrongBox
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        assertFalse(testPlayer.canAfford(resourceToUse));
        //add resource to warehouse
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 0);
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertTrue(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));

        //add additional to player

        testPlayer.addAdditionalWarehouse(Warehouse.getInstance(2, ResourceType.COIN));


        //ANOTHER PROVE
        testPlayer = Player.getInstance("Tester");
        resourceToUse = new ArrayList<>();
        //base case: empty
        assertTrue(testPlayer.canAfford(resourceToUse));
        //add element to resourceToUse
        // 1 coin, 2 shield from strongbox
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.SHIELD));
        //1 coin, 2 shield from standard warehouse
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 1, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 3, ResourceType.SERVANT));
        assertFalse(testPlayer.canAfford(resourceToUse));
        //add Resource to StrongBox
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        assertFalse(testPlayer.canAfford(resourceToUse));
        //add resource to warehouse
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 0);
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));

        //add additional to player

        testPlayer.addAdditionalWarehouse(Warehouse.getInstance(2, ResourceType.SERVANT));

        assertFalse(testPlayer.canAfford(resourceToUse));

        testPlayer.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.SERVANT), 0);


        assertTrue(testPlayer.canAfford(resourceToUse));
        assertFalse(testPlayer.canAfford(resourceToUse));


        //ANOTHER PROVE #2
        testPlayer = Player.getInstance("Tester");
        resourceToUse = new ArrayList<>();
        //base case: empty
        assertTrue(testPlayer.canAfford(resourceToUse));
        //add element to resourceToUse
        // 1 coin, 2 shield from strongbox
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, ResourceType.SHIELD));
        //1 coin, 2 shield from standard warehouse
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 1, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 1, ResourceType.COIN));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2, ResourceType.SHIELD));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 3, ResourceType.SERVANT));
        resourceToUse.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 4, ResourceType.SERVANT));
        assertFalse(testPlayer.canAfford(resourceToUse));
        //add Resource to StrongBox
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        testPlayer.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
        assertFalse(testPlayer.canAfford(resourceToUse));
        //add resource to warehouse
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 0);
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));
        testPlayer.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 2);
        assertFalse(testPlayer.canAfford(resourceToUse));

        //add additional to player

        testPlayer.addAdditionalWarehouse(Warehouse.getInstance(2, ResourceType.SERVANT));
        testPlayer.addAdditionalWarehouse(Warehouse.getInstance(2, ResourceType.SERVANT));

        assertFalse(testPlayer.canAfford(resourceToUse));

        testPlayer.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.SERVANT), 0);
        testPlayer.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.SERVANT), 1);


        assertTrue(testPlayer.canAfford(resourceToUse));
        assertFalse(testPlayer.canAfford(resourceToUse));


    }

    public void testAddResourceToWarehouseStandard() {
        Player tester = Player.getInstance();
        //base case
        assertFalse(tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN),22));

        //add to 0
        assertTrue(tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0));
        assertEquals(ResourceType.COIN, tester.getWarehousesStandard().get(0).getResourceType());
        assertEquals(1, tester.getResources().size());
        //try to add with a completed warehouse
        assertFalse(tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0));
        assertEquals(ResourceType.COIN, tester.getWarehousesStandard().get(0).getResourceType());
        assertEquals(1, tester.getResources().size());

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(Resource.getInstance(ResourceType.SERVANT));
        resourcesToAdd.add(Resource.getInstance(ResourceType.SERVANT));
        assertTrue(tester.addResourceToWarehouseStandard(resourcesToAdd, 1));
        assertEquals(ResourceType.SERVANT, tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(3, tester.getResources().size());

        //try to add again
        assertFalse(tester.addResourceToWarehouseStandard(resourcesToAdd, 1));
        assertEquals(ResourceType.SERVANT, tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(3, tester.getResources().size());

        assertFalse(tester.addResourceToWarehouseStandard(resourcesToAdd,2));
        assertEquals(3, tester.getResources().size());
    }

    public void testAddResourceToWarehouseAdditional() {
        Player tester = Player.getInstance();
        //base case
        assertFalse(tester.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN),22));
        tester.addAdditionalWarehouse(Warehouse.getInstance(2,ResourceType.COIN));
        tester.addAdditionalWarehouse(Warehouse.getInstance(2,ResourceType.SERVANT));

        //add to 0
        assertTrue(tester.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN), 0));
        assertEquals(ResourceType.COIN, tester.getWarehousesAdditional().get(0).getResourceType());
        assertEquals(1, tester.getResources().size());
        //try to add with a completed warehouse
        assertTrue(tester.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN), 0));
        assertEquals(ResourceType.COIN, tester.getWarehousesAdditional().get(0).getResourceType());
        assertEquals(2, tester.getResources().size());

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(Resource.getInstance(ResourceType.SERVANT));
        resourcesToAdd.add(Resource.getInstance(ResourceType.SERVANT));
        assertTrue(tester.addResourceToWarehouseAdditional(resourcesToAdd, 1));
        assertEquals(ResourceType.SERVANT, tester.getWarehousesAdditional().get(1).getResourceType());
        assertEquals(4, tester.getResources().size());

        //try to add again
        assertFalse(tester.addResourceToWarehouseAdditional(resourcesToAdd, 1));
        assertEquals(ResourceType.SERVANT, tester.getWarehousesAdditional().get(1).getResourceType());
        assertEquals(4, tester.getResources().size());

        assertFalse(tester.addResourceToWarehouseAdditional(resourcesToAdd,2));
        assertEquals(4, tester.getResources().size());
    }

    public void testTestMoveResources() {
        //TODO: DA FARE ANCORA
    }

    @Test
    public void testMoveResources() {
       /* Player tester = new Player("tester");
        tester.addAdditionalWarehouse(Warehouse.getInstance(2, ResourceType.SERVANT));
        tester.addAdditionalWarehouse(Warehouse.getInstance(2,ResourceType.SHIELD));
        //SITUATION: 0) 1) 2) 3) "SERVANT" 4) "SHIELD"

        //Testing the swapping between the warehouses empty
        assertFalse(tester.moveResources(0, 1, 2));
        assertTrue(tester.moveResources(0, 1, 0));
        assertEquals(ResourceType.ANY,tester.getWarehousesStandard().get(0).getResourceType());
        assertEquals(1, tester.getWarehousesStandard().get(0).getSpaceAvailable());
        assertEquals(0, tester.getWarehousesStandard().get(0).getResources().size());



        //Testing with one Resources and the different cases
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD),0);
        //SITUATION: 0) SHIELD 1) 2) 3) "SERVANT" 4) "SHIELD"

        //No more than one Resource is swapped
        assertFalse(tester.moveResources(0, 1, 2));

        //S<->S
        assertTrue(tester.moveResources(0, 1, 1));
        //SITUATION: 0) 1) SHIELD 2) 3) "SERVANT" 4) "SHIELD"
        assertEquals(ResourceType.ANY,tester.getWarehousesStandard().get(0).getResourceType());
        assertEquals(1, tester.getWarehousesStandard().get(0).getSpaceAvailable());
        assertEquals(0, tester.getWarehousesStandard().get(0).getResources().size());
        assertEquals(ResourceType.SHIELD,tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(1, tester.getWarehousesStandard().get(1).getSpaceAvailable());
        assertTrue(tester.getWarehousesStandard().get(1).getResources().contains(Resource.getInstance(ResourceType.SHIELD)));

        //S <-> A
        assertTrue(tester.moveResources(1, 4, 1));
        //SITUATION: 0) 1) 2) 3) "SERVANT" 4) SHIELD
        assertEquals(ResourceType.ANY,tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(2, tester.getWarehousesStandard().get(1).getSpaceAvailable());
        assertEquals(0, tester.getWarehousesStandard().get(1).getResources().size());
        assertEquals(ResourceType.SHIELD,tester.getWarehousesAdditional().get(1).getResourceType());
        assertEquals(1, tester.getWarehousesAdditional().get(1).getSpaceAvailable());
        assertTrue(tester.getWarehousesAdditional().get(1).getResources().contains(Resource.getInstance(ResourceType.SHIELD)));

        //A <-> S
        assertFalse(tester.moveResources(4, 2, 2));
        assertTrue(tester.moveResources(4, 2, 1));
        //SITUATION: 0) 1) 2) SHIELD 3) "SERVANT" 4) "SHIELD"
        assertEquals(ResourceType.SHIELD,tester.getWarehousesAdditional().get(1).getResourceType());
        assertEquals(2, tester.getWarehousesAdditional().get(1).getSpaceAvailable());
        assertEquals(0, tester.getWarehousesAdditional().get(1).getResources().size());
        assertEquals(ResourceType.SHIELD,tester.getWarehousesStandard().get(2).getResourceType());
        assertEquals(2, tester.getWarehousesStandard().get(2).getSpaceAvailable());
        assertTrue(tester.getWarehousesStandard().get(2).getResources().contains(Resource.getInstance(ResourceType.SHIELD)));

        //Testing that with one Resource per WarehouseStandard they are swapped
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN),0);
        //SITUATION: 0) COIN 1)  2) 3)SHIELD "SERVANT" 4) "SHIELD"
        assertTrue(tester.moveResources(0, 2, 1));
        //SITUATION: 0) SHIELD 1) 2) COIN 3) "SERVANT" 4) "SHIELD"
        assertEquals(ResourceType.SHIELD,tester.getWarehousesStandard().get(0).getResourceType());
        assertEquals(0, tester.getWarehousesStandard().get(0).getSpaceAvailable());
        assertTrue(tester.getWarehousesStandard().get(0).getResources().contains(Resource.getInstance(ResourceType.SHIELD)));
        assertEquals(ResourceType.COIN,tester.getWarehousesStandard().get(2).getResourceType());
        assertEquals(2, tester.getWarehousesStandard().get(2).getSpaceAvailable());
        assertTrue(tester.getWarehousesStandard().get(2).getResources().contains(Resource.getInstance(ResourceType.COIN)));

        //Testing the moving of two Resources in a Warehouse
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 2);
        //SITUATION: 0) SHIELD 1) 2) COIN|COIN 3) "SERVANT" 4) "SHIELD"
        assertFalse(tester.moveResources(2, 1, 1));
        assertFalse(tester.moveResources(2, 3, 2));
        assertFalse(tester.moveResources(2, 0, 2));
        assertTrue(tester.moveResources(2, 1, 2));
        //SITUATION: 0) SHIELD 1) COIN|COIN 2) 3) "SERVANT" 4) "SHIELD"
        assertEquals(ResourceType.COIN,tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(0, tester.getWarehousesStandard().get(1).getSpaceAvailable());
        assertEquals(2, tester.getWarehousesStandard().get(1).getResources().stream().filter(elem -> elem.getType() == ResourceType.COIN).count());
        assertEquals(ResourceType.ANY,tester.getWarehousesStandard().get(2).getResourceType());
        assertEquals(3, tester.getWarehousesStandard().get(2).getSpaceAvailable());
        assertEquals(0, tester.getWarehousesStandard().get(2).getResources().size());

        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
        tester.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
        //SITUATION: 0) SHIELD 1) COIN|COIN 2)SERVANT|SERVANT 3) "SERVANT" 4) "SHIELD"
        assertTrue(tester.moveResources(1, 2, 2));
        //SITUATION: 0) SHIELD 1) SERVANT|SERVANT 2)COIN|COIN 3) "SERVANT" 4) "SHIELD"
        assertEquals(ResourceType.SERVANT,tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(0, tester.getWarehousesStandard().get(1).getSpaceAvailable());
        assertEquals(2, tester.getWarehousesStandard().get(1).getResources().stream().filter(elem -> elem.getType() == ResourceType.SERVANT).count());
        assertEquals(ResourceType.COIN,tester.getWarehousesStandard().get(2).getResourceType());
        assertEquals(1, tester.getWarehousesStandard().get(2).getSpaceAvailable());
        assertEquals(2, tester.getWarehousesStandard().get(2).getResources().stream().filter(elem -> elem.getType() == ResourceType.COIN).count());

        assertTrue(tester.moveResources(1, 3, 1));
        //SITUATION: 0) SHIELD 1) SERVANT 2)COIN|COIN 3) SERVANT 4) "SHIELD"
        assertEquals(ResourceType.SERVANT,tester.getWarehousesStandard().get(1).getResourceType());
        assertEquals(1, tester.getWarehousesStandard().get(1).getSpaceAvailable());
        assertEquals(1, tester.getWarehousesStandard().get(1).getResources().size());
        assertEquals(ResourceType.SERVANT,tester.getWarehousesAdditional().get(0).getResourceType());
        assertEquals(1, tester.getWarehousesAdditional().get(0).getSpaceAvailable());
        assertEquals(1, tester.getWarehousesAdditional().get(0).getResources().size());

        //testing with 3 Resources in the WarehouseStandard
        assertTrue(tester.moveResources(2,1,1));
        tester.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.SERVANT), 0);
        //SITUATION: 0) SHIELD 1) COIN|COIN 2)SERVANT 3) SERVANT|SERVANT 4) "SHIELD"

        assertTrue(tester.moveResources(3,2,2));
        //SITUATION: 0) SHIELD 1) COIN|COIN 2)SERVANT|SERVANT|SERVANT 3) "SERVANT"  4) "SHIELD"
        assertEquals(ResourceType.SERVANT,tester.getWarehousesAdditional().get(0).getResourceType());
        assertEquals(2, tester.getWarehousesAdditional().get(0).getSpaceAvailable());
        assertEquals(0, tester.getWarehousesAdditional().get(0).getResources().size());
        assertEquals(ResourceType.SERVANT,tester.getWarehousesStandard().get(2).getResourceType());
        assertEquals(0, tester.getWarehousesStandard().get(2).getSpaceAvailable());
        assertEquals(3, tester.getWarehousesStandard().get(2).getResources().stream().filter(elem -> elem.getType() == ResourceType.SERVANT).count());

        */


    }
}



