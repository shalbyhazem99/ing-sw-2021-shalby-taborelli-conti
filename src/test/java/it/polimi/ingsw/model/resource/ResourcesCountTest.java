package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Class used to test {@link ResourcesCount}
 */

public class ResourcesCountTest extends TestCase {

    public ResourcesCountTest() {
    }

    @Test
    public void testSetType(){
        ResourcesCount resourcesCount = new ResourcesCount(1, ResourceType.ANY);

        assertEquals(ResourceType.ANY, resourcesCount.getType());
        resourcesCount.setType(ResourceType.FAITH);
        assertEquals(ResourceType.FAITH, resourcesCount.getType());
    }

    @Test
    public void testResourceCount(){
        final int count = 3;
        ResourceType type = ResourceType.COIN;

        ResourcesCount resourcesCount = ResourcesCount.getInstance(count, type);
        assertEquals(count, resourcesCount.getCount());
        assertEquals(type, resourcesCount.getType());

        // toArrayList of resource
        ArrayList<Resource> resources = resourcesCount.toArrayListResources();
        assertEquals(count,resources.size());
        assertTrue(resources.stream().allMatch(elem->elem.getType().equals(type)));
    }

    @Test
    public void testAddCount(){
        ResourcesCount resourcesCount = new ResourcesCount(1, ResourceType.ANY);

        assertEquals(1, resourcesCount.getCount());
        resourcesCount.addCount();
        assertEquals(2, resourcesCount.getCount());
    }

}