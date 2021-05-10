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


public class LeaderCardColorTest extends TestCase {
    public LeaderCardColorTest(){
    }

    /**
     * Class used to test the correct getting points from a {@link LeaderCardColor}
     */
    @Test
    public void testGetPoints(){
        Player tester = new Player("tester");
        LeaderCardColor leaderCardColor_null = new LeaderCardColor(2, null, null, null);
        //Testing I can't get points from a disable Card
        assertEquals(0, leaderCardColor_null.getPoints());
        tester.addLeaderCard(leaderCardColor_null);
        leaderCardColor_null.active(tester);
        //A card without any power can assign points
        assertEquals(2,leaderCardColor_null.getPoints());

        //Testing correct assignment of the card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.PURPLE );
        tester.addDevelopmentCard(developmentCard, 0);
        ArrayList<DevelopmentCardNeeded> developmentCardNeeded = new ArrayList<>();
        DevelopmentCardNeeded cardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        developmentCardNeeded.add(cardNeeded);
        LeaderCardColor leaderCardColor = new LeaderCardColor(2, ResourceType.STONE, null, developmentCardNeeded);
        leaderCardColor.active(tester);
        assertEquals(2, leaderCardColor.getPoints());

    }

    /**
     * Class used to test if the {@link Player} can activate the {@link LeaderCardColor}
     */
    @Test
    public void testIsActionable(){
        Player tester = new Player("Tester");

        //Testing a null card can always be activated
        LeaderCardColor card_null = new LeaderCardColor(2, null, null, null);
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
        LeaderCardColor cardColor1 = new LeaderCardColor(0, null, null, devCardList);
        assertTrue(cardColor1.isActionable(tester));

        //Testing more than one card needed
        devCardList.add(developmentCardNeeded2);
        LeaderCardColor cardColor2 = new LeaderCardColor(0, null, null, devCardList);
        assertTrue(cardColor2.isActionable(tester));

        //Testing negative case
        DevelopmentCardNeeded developmentCardNeeded1Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devCardListWrong = new ArrayList<>();
        devCardListWrong.add(developmentCardNeeded1Wrong);
        LeaderCardColor cardColorWrong1 = new LeaderCardColor(1, null, null, devCardListWrong);

        assertFalse(cardColorWrong1.isActionable(tester));

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

        LeaderCardColor cardColor3 = new LeaderCardColor(0, null, resourcesNeeded, null);
        assertTrue(cardColor3.isActionable(tester));

        //Testing more type Resources needed
        resourcesNeeded.add(resources2);
        LeaderCardColor cardColor4 = new LeaderCardColor(0, null, resourcesNeeded, null);
        assertTrue(cardColor4.isActionable(tester));
    }

    /**
     * Class used to test the correct activation of the {@link LeaderCardColor}
     */

    @Test
    public void testActive(){
        Player tester = new Player("tester");

        //testing that a card with no power is activated
        LeaderCardColor leaderCardColor_null = new LeaderCardColor(0, null, null, null);
        tester.addLeaderCard(leaderCardColor_null);
        assertTrue(leaderCardColor_null.active(tester));

        //testing the activation of a normal card
        DevelopmentCard developmentCard = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.YELLOW, 0, null, null);
        tester.addDevelopmentCard(developmentCard, 0);
        DevelopmentCardNeeded developmentCardNeeded = new DevelopmentCardNeeded(1, DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded = new ArrayList<>();
        devListNeeded.add(developmentCardNeeded);
        LeaderCardColor leaderCardColor = new LeaderCardColor(5, ResourceType.COIN, null, devListNeeded);
        tester.addLeaderCard(leaderCardColor);
        assertTrue(leaderCardColor.active(tester));

        //testing a card is not activated two times
        assertFalse(leaderCardColor.active(tester));

        //Testing the if i can't activate a card that card won't be activate
        DevelopmentCardNeeded developmentCardNeeded_Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devListNeeded_Wrong = new ArrayList<>();
        devListNeeded_Wrong.add(developmentCardNeeded_Wrong);
        LeaderCardColor leaderCardColor_Wrong = new LeaderCardColor(5, ResourceType.COIN, null, devListNeeded_Wrong);
        tester.addLeaderCard(leaderCardColor_Wrong);
        assertFalse(leaderCardColor_Wrong.active(tester));
    }
}