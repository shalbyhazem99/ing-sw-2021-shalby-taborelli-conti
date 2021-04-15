package it.polimi.ingsw.model;

import org.junit.Test;


import static junit.framework.TestCase.*;

class ResourceTest {

    @Test
    public void testResource(){
        ResourceType resourceType = ResourceType.COIN;
        Resource resource = new Resource(resourceType);

        assertEquals(resourceType, resource.getType());
    }

    //Così funziona?
    /*
    public boolean equals(Object obj) {
        if (!(obj instanceof Resource)) {
            return false;
        }
        return type.equals(((Resource) obj).getType());
    }
     */

    @Test
    public void testEquals() {
        ResourceType type = ResourceType.COIN;

        assertTrue(type.equals(ResourceType.COIN));
    }
}