package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import junit.framework.TestCase;
import org.junit.Test;


/**
 * Class used to test {@link Resource}
 */

public class ResourceTest extends TestCase {

    @Test
    public void testResource(){
        ResourceType resourceType = ResourceType.COIN;
        Resource resourceA = Resource.getInstance(ResourceType.COIN);
        Resource resourceB= Resource.getInstance(ResourceType.SHIELD);
        Resource resourceC= Resource.getInstance(ResourceType.COIN);
        assertEquals(resourceType, resourceA.getType());
        assertTrue(resourceA.equals(resourceC));
        assertFalse(resourceA.equals(resourceB));
        assertFalse(resourceA.equals(null));
        assertTrue(resourceA.equals(resourceA.clone()));
    }

    @Test
    public void testEquals() {
        ResourceType type = ResourceType.COIN;
        assertEquals(ResourceType.COIN, type);
    }
}