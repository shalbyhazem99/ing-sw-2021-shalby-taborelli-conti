package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Class used to test the class {@link LeaderCardAddProductive}
 */

public class LeaderCardAddProductiveTest extends TestCase {

    public LeaderCardAddProductiveTest(){}


    /**
     * Class used to test the correct activation of the {@link LeaderCardAddProductive}
     */
    @Test
    public void testActive(){
        //Testing that a LeaderCard that has no power is activated
        Player tester = new Player("tester");
        LeaderCardAddProductive leaderCardAddProductive_null = new LeaderCardAddProductive(2, null, null, null);
        tester.addLeaderCard(leaderCardAddProductive_null);
        assertTrue(leaderCardAddProductive_null.active(tester));

        //Testing that a card is activated
        DevelopmentCard developmentCard1 = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.PURPLE, 0, null, null);
        DevelopmentCard developmentCard2 = new DevelopmentCard(DevelopmentCardLevel.SECOND, DevelopmentCardType.PURPLE, 0, null, null);
        tester.addDevelopmentCard(developmentCard1,0);
        tester.addDevelopmentCard(developmentCard2,0);
        ArrayList<DevelopmentCardNeeded> developmentCardNeededs = new ArrayList<>();
        DevelopmentCardNeeded devNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.SECOND );
        developmentCardNeededs.add(devNeeded);
        LeaderCardAddProductive leaderCardAddProductive = new LeaderCardAddProductive(5, ResourceType.STONE, null, developmentCardNeededs);
        tester.addLeaderCard(leaderCardAddProductive);
        assertTrue(leaderCardAddProductive.active(tester));

        //testing that a card already activated is not reactivated
        assertFalse(leaderCardAddProductive.active(tester));

        //Testing the if i can't activate a card that card won't be activate
        DevelopmentCardNeeded developmentCardNeeded_Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded_Wrong = new ArrayList<>();
        devListNeeded_Wrong.add(developmentCardNeeded_Wrong);
        LeaderCardAddProductive leaderCardAddProductive_Wrong = new LeaderCardAddProductive(5, ResourceType.COIN, null, devListNeeded_Wrong);
        tester.addLeaderCard(leaderCardAddProductive_Wrong);
        assertFalse(leaderCardAddProductive_Wrong.active(tester));
    }


    /**
     * Class used to test the correct getting points from a {@link LeaderCardAddProductive}
     */
    @Test
    public void testGetPoints(){
        Player tester = new Player("tester");
        LeaderCardAddProductive leaderCardAddProductive_null = new LeaderCardAddProductive(2, null, null, null);
        //Testing I can't get points from a disable Card
        assertEquals(0, leaderCardAddProductive_null.getPoints());
        tester.addLeaderCard(leaderCardAddProductive_null);
        leaderCardAddProductive_null.active(tester);
        //A card without any power can assign points
        assertEquals(2,leaderCardAddProductive_null.getPoints());

        //Testing correct assignment of the card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.PURPLE );
        tester.addDevelopmentCard(developmentCard, 0);
        ArrayList<DevelopmentCardNeeded> developmentCardNeeded = new ArrayList<>();
        DevelopmentCardNeeded cardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        developmentCardNeeded.add(cardNeeded);
        LeaderCardAddProductive leaderCardAddProductive = new LeaderCardAddProductive(2, ResourceType.STONE, null, developmentCardNeeded);
        leaderCardAddProductive.active(tester);
        assertEquals(2, leaderCardAddProductive.getPoints());

    }


    /**
     * Class used to test if the {@link LeaderCardAddProductive} can be activated by the player
     */
    @Test
    public void testIsActionable(){
        Player tester = new Player("Tester");

        //Testing a null card can always be activated
        LeaderCardAddProductive card_null = new LeaderCardAddProductive(2, null, null, null);
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
        LeaderCardAddProductive cardAddProductive1 = new LeaderCardAddProductive(0, null, null, devCardList);
        assertTrue(cardAddProductive1.isActionable(tester));

        //Testing more than one card needed
        devCardList.add(developmentCardNeeded2);
        LeaderCardAddProductive cardAddProductive2 = new LeaderCardAddProductive(0, null, null, devCardList);
        assertTrue(cardAddProductive2.isActionable(tester));

        //Testing negative case
        DevelopmentCardNeeded developmentCardNeeded1Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devCardListWrong = new ArrayList<>();
        devCardListWrong.add(developmentCardNeeded1Wrong);
        LeaderCardAddProductive cardAddProductiveWrong1 = new LeaderCardAddProductive(1, null, null, devCardListWrong);

        assertFalse(cardAddProductiveWrong1.isActionable(tester));

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

        LeaderCardAddProductive cardAddProductive3 = new LeaderCardAddProductive(0, null, resourcesNeeded, null);
        assertTrue(cardAddProductive3.isActionable(tester));

        //Testing more type Resources needed
        resourcesNeeded.add(resources2);
        LeaderCardAddProductive cardAddProductive4 = new LeaderCardAddProductive(0, null, resourcesNeeded, null);
        assertTrue(cardAddProductive4.isActionable(tester));
    }
}