package it.polimi.ingsw.model;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Class used to test the {@link ProductivePower}
 */

public class ProductivePowerTest extends TestCase {

    public ProductivePowerTest() {
    }

    /**
     * Test for constructor
     */
    @Test
    public void testProductivePower(){
        final int count = 1;
        ResourceType type = ResourceType.COIN;
        ResourcesCount resourcesCount = ResourcesCount.getInstance(count, type);
        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(resourcesCount);

        Resource resource = new Resource(ResourceType.COIN);
        ArrayList<Resource> to = new ArrayList<>();
        to.add(resource);

        ProductivePower productivePower = ProductivePower.getInstance(from, to);

        assertEquals(from, productivePower.getFrom());
        assertEquals(to, productivePower.getTo());
    }
}