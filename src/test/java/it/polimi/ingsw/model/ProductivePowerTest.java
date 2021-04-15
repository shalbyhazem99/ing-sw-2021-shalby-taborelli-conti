package it.polimi.ingsw.model;


import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

class ProductivePowerTest {

    /**
     * Test for constructor
     */
    @Test
    public void ProductivePower(){
        final int count = 1;
        ResourceType type = ResourceType.COIN;
        ResourcesCount resourcesCount = new ResourcesCount(count, type);
        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(resourcesCount);

        Resource resource = new Resource(ResourceType.COIN);
        ArrayList<Resource> to = new ArrayList<>();
        to.add(resource);

        ProductivePower productivePower = new ProductivePower(from, to);

        assertSame(from, productivePower.getFrom());
        assertSame(to, productivePower.getTo());
    }
}