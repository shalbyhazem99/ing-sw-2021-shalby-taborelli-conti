package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class LeaderCardDiscountTest extends TestCase {
    public LeaderCardDiscountTest(){
    }

    @Test
    public void testGetPoints() {
        Player tester = new Player("tester");
        LeaderCardDiscount leaderCardDiscount_null = LeaderCardDiscount.getInstance(3, null, null, null);
        //Testing I can't get points from a disable Card
        assertEquals(0, leaderCardDiscount_null.getPoints());
        tester.addLeaderCard(leaderCardDiscount_null);
        leaderCardDiscount_null.active(tester);
        //A card without any power can assign points
        assertEquals(3,leaderCardDiscount_null.getPoints());

        //Testing correct assignment of the card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.PURPLE );
        tester.addDevelopmentCard(developmentCard, 0);
        ArrayList<DevelopmentCardNeeded> developmentCardNeeded = new ArrayList<>();
        DevelopmentCardNeeded cardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        developmentCardNeeded.add(cardNeeded);
        LeaderCardDiscount leaderCardDiscount = LeaderCardDiscount.getInstance(2, ResourceType.STONE, null, developmentCardNeeded);
        leaderCardDiscount.active(tester);
        assertEquals(2, leaderCardDiscount.getPoints());
    }

    @Test
    public void testIsActionable(){
        Player tester = new Player("Tester");

        //Testing a null card can always be activated
        LeaderCardDiscount card_null = LeaderCardDiscount.getInstance(2, null, null, null);
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
        LeaderCardDiscount cardDiscount1 = LeaderCardDiscount.getInstance(0, null, null, devCardList);
        assertTrue(cardDiscount1.isActionable(tester));

        //Testing more than one card needed
        devCardList.add(developmentCardNeeded2);
        LeaderCardDiscount cardDiscount2 = LeaderCardDiscount.getInstance(0, null, null, devCardList);
        assertTrue(cardDiscount2.isActionable(tester));

        //Testing negative case
        DevelopmentCardNeeded developmentCardNeeded1Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devCardListWrong = new ArrayList<>();
        devCardListWrong.add(developmentCardNeeded1Wrong);
        LeaderCardDiscount cardDiscountWrong1 = LeaderCardDiscount.getInstance(1, null, null, devCardListWrong);

        assertFalse(cardDiscountWrong1.isActionable(tester));

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

        LeaderCardDiscount cardDiscount3 = LeaderCardDiscount.getInstance(0, null, resourcesNeeded, null);
        assertTrue(cardDiscount3.isActionable(tester));

        //Testing more type Resources needed
        resourcesNeeded.add(resources2);
        LeaderCardDiscount cardDiscount4 = LeaderCardDiscount.getInstance(0, null, resourcesNeeded, null);
        assertTrue(cardDiscount4.isActionable(tester));

    }

    @Test
    public void testActive(){
        Player tester = new Player("tester");

        //testing that a card with no power is activated
        LeaderCardDiscount leaderCardDiscount_null = LeaderCardDiscount.getInstance(0, null, null, null);
        tester.addLeaderCard(leaderCardDiscount_null);
        assertTrue(leaderCardDiscount_null.active(tester));

        //testing the activation of a normal card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.YELLOW, 0, null, null);
        tester.addDevelopmentCard(developmentCard, 0);
        DevelopmentCardNeeded developmentCardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded = new ArrayList<>();
        devListNeeded.add(developmentCardNeeded);
        LeaderCardDiscount leaderCardDiscount = LeaderCardDiscount.getInstance(5, ResourceType.COIN, null, devListNeeded);
        tester.addLeaderCard(leaderCardDiscount);
        assertTrue(leaderCardDiscount.active(tester));


        //testing a card is not activated two times
        assertFalse(leaderCardDiscount.active(tester));

        //Testing the if i can't activate a card that card won't be activate
        DevelopmentCardNeeded developmentCardNeeded_Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded_Wrong = new ArrayList<>();
        devListNeeded_Wrong.add(developmentCardNeeded_Wrong);
        LeaderCardDiscount leaderCardDiscount_Wrong = LeaderCardDiscount.getInstance(5, ResourceType.COIN, null, devListNeeded_Wrong);
        tester.addLeaderCard(leaderCardDiscount_Wrong);
        assertFalse(leaderCardDiscount_Wrong.active(tester));
    }
}