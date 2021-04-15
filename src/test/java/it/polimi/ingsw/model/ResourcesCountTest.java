package it.polimi.ingsw.model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

class ResourcesCountTest {


    @Test
    public void testResourceCount(){
        final int count = 1;
        ResourceType type = ResourceType.COIN;

        ResourcesCount resourcesCount = new ResourcesCount(count, type);
        assertEquals(count, resourcesCount.getCount());
        assertEquals(type, resourcesCount.getType());
    }

    //é giusto la doppia parentesi graffa?
    @Test
    public void toArrayListResources() {
    }
}