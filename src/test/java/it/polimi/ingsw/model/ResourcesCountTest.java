package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Class used to test {@link ResourcesCount}
 */

public class ResourcesCountTest extends TestCase {

    public ResourcesCountTest() {
    }


    @Test
    public void testResourceCount(){
        final int count = 1;
        ResourceType type = ResourceType.COIN;

        ResourcesCount resourcesCount = new ResourcesCount(count, type);
        assertEquals(count, resourcesCount.getCount());
        assertEquals(type, resourcesCount.getType());
    }

}