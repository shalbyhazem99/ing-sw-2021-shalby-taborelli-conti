package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

/**
 * Class used to test {@link Resource}
 */

public class ResourceTest extends TestCase {
    public ResourceTest() {
    }


    @org.junit.jupiter.api.Test
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