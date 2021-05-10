package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;


public class LeaderCardAddWarehouseTest extends TestCase {

    public LeaderCardAddWarehouseTest (){
    }

    /**
     * Class used to test the correct activation of a {@link LeaderCardAddWarehouse}
     */
    @Test
    public void testActive() {
        Player tester = new Player("tester");

        //testing that a card with no power is activated
        LeaderCardAddWarehouse leaderCardAddWarehouse_null = new LeaderCardAddWarehouse(0, null, null, null);
        tester.addLeaderCard(leaderCardAddWarehouse_null);
        assertTrue(leaderCardAddWarehouse_null.active(tester));

        //testing the activation of a normal card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.YELLOW, 0, null, null);
        tester.addDevelopmentCard(developmentCard, 0);
        DevelopmentCardNeeded developmentCardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded = new ArrayList<>();
        devListNeeded.add(developmentCardNeeded);
        LeaderCardAddWarehouse leaderCardAddWarehouse = new LeaderCardAddWarehouse(5, ResourceType.COIN, null, devListNeeded);
        tester.addLeaderCard(leaderCardAddWarehouse);
        assertTrue(leaderCardAddWarehouse.active(tester));

        //testing a card is not activated two times
        assertFalse(leaderCardAddWarehouse.active(tester));

        //Testing the if i can't activate a card that card won't be activate
        DevelopmentCardNeeded developmentCardNeeded_Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded_Wrong = new ArrayList<>();
        devListNeeded_Wrong.add(developmentCardNeeded_Wrong);
        LeaderCardAddWarehouse leaderCardAddWarehouse_Wrong = new LeaderCardAddWarehouse(5, ResourceType.COIN, null, devListNeeded_Wrong);
        tester.addLeaderCard(leaderCardAddWarehouse_Wrong);
        assertFalse(leaderCardAddWarehouse_Wrong.active(tester));

    }

    /**
     * Class used to test if the {@link LeaderCardAddWarehouse} can be activated from the player
     */
    @Test
    public void testIsActionable(){
        Player tester = new Player("Tester");

        //Testing a null card can always be activated
        LeaderCardAddWarehouse card_null = new LeaderCardAddWarehouse(2, null, null, null);
        assertTrue(card_null.isActionable(tester));

        //Testing a card that need development cards can be activated

        DevelopmentCard card1 = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.GREEN, 0, null, null);
        DevelopmentCard card2 = new DevelopmentCard(DevelopmentCardLevel.SECOND, DevelopmentCardType.PURPLE, 0, null, null);
        DevelopmentCardNeeded developmentCardNeeded1 = new DevelopmentCardNeeded(1, DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST);
        DevelopmentCardNeeded developmentCardNeeded2 = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.SECOND);
        tester.addDevelopmentCard(card1,0);
        tester.addDevelopmentCard(card2, 0);
        ArrayList<DevelopmentCardNeeded> devCardList = new ArrayList<>();
        devCardList.add(developmentCardNeeded1);
        LeaderCardAddWarehouse cardAddWarehouse1 = new LeaderCardAddWarehouse(0, null, null, devCardList);
        assertTrue(cardAddWarehouse1.isActionable(tester));

        //Testing more than one card needed
        devCardList.add(developmentCardNeeded2);
        LeaderCardAddWarehouse cardAddRename2 = new LeaderCardAddWarehouse(0, null, null, devCardList);
        assertTrue(cardAddRename2.isActionable(tester));

        //Testing negative case
        DevelopmentCardNeeded developmentCardNeeded1Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devCardListWrong = new ArrayList<>();
        devCardListWrong.add(developmentCardNeeded1Wrong);
        LeaderCardAddWarehouse cardAddWarehouseWrong1 = new LeaderCardAddWarehouse(1, null, null, devCardListWrong);

        assertFalse(cardAddWarehouseWrong1.isActionable(tester));

        //Testing a Resource is needed
        ArrayList<ResourcesCount> resourcesNeeded = new ArrayList<>();
        ResourcesCount resources1 = new ResourcesCount(2, ResourceType.STONE);
        ResourcesCount resources2 = new ResourcesCount(4, ResourceType.FAITH);
        resourcesNeeded.add(resources1);
        Resource stone = new Resource(ResourceType.STONE);
        Resource faith = new Resource(ResourceType.FAITH);

        tester.addResourceToStrongBox(stone);
        tester.addResourceToStrongBox(stone);
        tester.addResourceToStrongBox(faith);
        tester.addResourceToStrongBox(faith);
        tester.addResourceToStrongBox(faith);
        tester.addResourceToStrongBox(faith);

        LeaderCardAddWarehouse cardAddWarehouse3 = new LeaderCardAddWarehouse(0, null, resourcesNeeded, null);
        assertTrue(cardAddWarehouse3.isActionable(tester));

        //Testing more type Resources needed
        resourcesNeeded.add(resources2);
        LeaderCardAddWarehouse cardAddWarehouse4 = new LeaderCardAddWarehouse(0, null, resourcesNeeded, null);
        assertTrue(cardAddWarehouse4.isActionable(tester));
    }

    /**
     * Class used to test the correct getting of points from a {@link LeaderCardAddWarehouse}
     */
    @Test
    public void testGetPoints(){
        Player tester = new Player("tester");
        LeaderCardAddWarehouse leaderCardAddWarehouse_null = new LeaderCardAddWarehouse(2, null, null, null);
        //Testing I can't get points from a disable Card
        assertEquals(0, leaderCardAddWarehouse_null.getPoints());
        tester.addLeaderCard(leaderCardAddWarehouse_null);
        leaderCardAddWarehouse_null.active(tester);
        //A card without any power can assign points
        assertEquals(2,leaderCardAddWarehouse_null.getPoints());

        //Testing correct assignment of the card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.PURPLE );
        tester.addDevelopmentCard(developmentCard, 0);
        ArrayList<DevelopmentCardNeeded> developmentCardNeeded = new ArrayList<>();
        DevelopmentCardNeeded cardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        developmentCardNeeded.add(cardNeeded);
        LeaderCardAddWarehouse leaderCardAddWarehouse = new LeaderCardAddWarehouse(2, ResourceType.STONE, null, developmentCardNeeded);
        leaderCardAddWarehouse.active(tester);
        assertEquals(2, leaderCardAddWarehouse.getPoints());

    }
}