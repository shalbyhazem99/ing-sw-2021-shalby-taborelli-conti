package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Class used to test the class {@link LeaderCardAddProductive}
 */

public class LeaderCardAddProductiveTest extends TestCase {


    @Test
    public void testGetPoints(){
        Player tester = new Player("tester");
        LeaderCardAddProductive leaderCardAddProductive = new LeaderCardAddProductive(2, null, null, null);
        //Testing I can't get points from a disable Card
        assertEquals(0, leaderCardAddProductive.getPoints());
        tester.addLeaderCard(leaderCardAddProductive);
        leaderCardAddProductive.active(tester);
        //assertEquals(2,leaderCardAddProductive.getPoints());

    }


    @Test
    public void testIsActivable(){
        Player tester = new Player("Tester");

        //Testing a null card can always be activated
        LeaderCardAddProductive card_null = new LeaderCardAddProductive(2, null, null, null);
        assertTrue(card_null.isActivable(tester));

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
        assertTrue(cardAddProductive1.isActivable(tester));

        //Testing more than one card needed
        devCardList.add(developmentCardNeeded2);
        LeaderCardAddProductive cardAddProductive2 = new LeaderCardAddProductive(0, null, null, devCardList);
        assertTrue(cardAddProductive2.isActivable(tester));

        //Testing negative case
        DevelopmentCardNeeded developmentCardNeeded1Wrong = new DevelopmentCardNeeded(1, DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        ArrayList<DevelopmentCardNeeded> devCardListWrong = new ArrayList<>();
        devCardListWrong.add(developmentCardNeeded1Wrong);
        LeaderCardAddProductive cardAddProductiveWrong1 = new LeaderCardAddProductive(1, null, null, devCardListWrong);

        // Non dovrebbe funzionare?
        //assertFalse(cardAddProductiveWrong1.isActivable(tester));

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
        assertTrue(cardAddProductive3.isActivable(tester));

        //Testing more type Resources needed
        resourcesNeeded.add(resources2);
        LeaderCardAddProductive cardAddProductive4 = new LeaderCardAddProductive(0, null, resourcesNeeded, null);
        assertTrue(cardAddProductive4.isActivable(tester));

    }
}