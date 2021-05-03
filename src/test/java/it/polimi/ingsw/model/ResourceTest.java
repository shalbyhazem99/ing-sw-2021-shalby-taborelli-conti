package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;


/**
 * Class used to test {@link Resource}
 */

public class ResourceTest extends TestCase {
    public ResourceTest() {
    }


    @Test
    public void testResource(){
        ResourceType resourceType = ResourceType.COIN;
        Resource resource = new Resource(resourceType);

        assertEquals(resourceType, resource.getType());
    }

    @Test
    public void testEquals() {
        ResourceType type = ResourceType.COIN;
        assertEquals(ResourceType.COIN, type);
    }
}