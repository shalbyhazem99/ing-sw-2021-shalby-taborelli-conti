package it.polimi.ingsw.model.developmentCard;


import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
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
        DevelopmentCardLevel level = DevelopmentCardLevel.THIRD;

        DevelopmentCardType type = DevelopmentCardType.GREEN;

        final int points = 4;

        ResourcesCount resourcesCount = new ResourcesCount(1,ResourceType.COIN);
        ArrayList<ResourcesCount> costs = new ArrayList<>();
        costs.add(resourcesCount);

        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(resourcesCount);
        Resource resource = new Resource(ResourceType.COIN);
        ArrayList<Resource> to = new ArrayList<>();
        to.add(resource);
        ProductivePower power = new ProductivePower(from, to);

        DevelopmentCard card = new DevelopmentCard(level, type, points, costs, power);

        assertEquals(DevelopmentCardLevel.THIRD, card.getLevel());
        assertEquals(DevelopmentCardType.GREEN,card.getType());
        assertEquals(4,card.getEquivalentPoint());
        //assertEquals(costs,card.getCosts());todo: here you must pass the player to verify if some discount is available
        assertEquals(power, card.getPowers());
    }
}