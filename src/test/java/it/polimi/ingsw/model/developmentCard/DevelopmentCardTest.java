package it.polimi.ingsw.model.developmentCard;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Class used to test {@link DevelopmentCard}
 */

public class DevelopmentCardTest extends TestCase {
    public DevelopmentCardTest() {
    }


    /**
     * Test for constructor
     */
    @Test
    public void testDevelopmentCard() {
        Player tester = Player.getInstance();
       //Without discount
        DevelopmentCardLevel level = DevelopmentCardLevel.THIRD;

        DevelopmentCardType type = DevelopmentCardType.GREEN;

        final int points = 4;

        ResourcesCount resourcesCount = new ResourcesCount(3,ResourceType.SERVANT);
        ArrayList<ResourcesCount> costs = new ArrayList<>();
        costs.add(resourcesCount);

        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(resourcesCount);
        Resource resource = new Resource(ResourceType.COIN);
        ArrayList<Resource> to = new ArrayList<>();
        to.add(resource);
        ProductivePower power = new ProductivePower(from, to);



        DevelopmentCard card = DevelopmentCard.getInstance(level, type, points, costs, power);

        assertEquals(DevelopmentCardLevel.THIRD, card.getLevel());
        assertEquals(DevelopmentCardType.GREEN,card.getType());
        assertEquals(4,card.getEquivalentPoint());
        assertEquals(1,card.getCosts(tester).size());
        assertEquals(ResourceType.SERVANT,card.getCosts(tester).get(0).getType());
        assertEquals(3,card.getCosts(tester).get(0).getCount());
        assertEquals(power, card.getPowers());

        //with discount
        tester.addDiscount(ResourcesCount.getInstance(1,ResourceType.SERVANT));
        assertEquals(DevelopmentCardLevel.THIRD, card.getLevel());
        assertEquals(DevelopmentCardType.GREEN,card.getType());
        assertEquals(4,card.getEquivalentPoint());
        assertEquals(1,card.getCosts(tester).size());
        assertEquals(ResourceType.SERVANT,card.getCosts(tester).get(0).getType());
        assertEquals(2,card.getCosts(tester).get(0).getCount());
        assertEquals(power, card.getPowers());
    }
}