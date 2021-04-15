package it.polimi.ingsw.model.developmentCard;


import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

class DevelopmentCardTest {

    /**
     * Class used to test the {@link DevelopmentCard}
     */


    /**
     * Test for constructor
     */
    @Test
    public void testDevelopmentCard() {
        DevelopmentCardLevel level = DevelopmentCardLevel.THIRD;

        DevelopmentCardType type = DevelopmentCardType.GREEN;

        final int points = 4;

        ResourcesCount resourcesCount = new ResourcesCount(1,ResourceType.COIN);
        ArrayList<ResourcesCount> costs = new ArrayList<ResourcesCount>();
        costs.add(resourcesCount);

        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(resourcesCount);
        Resource resource = new Resource(ResourceType.COIN);
        ArrayList<Resource> to = new ArrayList<>();
        to.add(resource);
        ProductivePower power = new ProductivePower(from, to);

        DevelopmentCard card = new DevelopmentCard(level, type, points, costs, power);

        assertSame(DevelopmentCardLevel.THIRD, card.getLevel());
        assertSame(DevelopmentCardType.GREEN,card.getType());
        assertEquals(4,card.getEquivalentPoint());
        assertSame(costs,card.getCosts());
        assertSame(power, card.getPowers());



    }
}