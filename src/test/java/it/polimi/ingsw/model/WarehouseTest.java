package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

class WarehouseTest {

    @Test
    public void testWarehouse(){
        int spaceAvailable =1;
        ResourceType resourceType = ResourceType.COIN;
        ArrayList<Resource> resources = new ArrayList<>();
        Warehouse warehouse = new Warehouse(spaceAvailable, resourceType);

        assertEquals(1, warehouse.getSpaceAvailable());
        assertEquals(resourceType, warehouse.getResourceType());
        assertEquals(resources, warehouse.getResources());
    }

    @Test
    public void testAddResource() {

        //testing the first condition
        int spaceAvailable = 0;
        ResourceType resourceType = ResourceType.COIN;
        Warehouse warehouse0 = new Warehouse(spaceAvailable, resourceType);
        Resource resource = new Resource(resourceType);
        assertFalse(warehouse0.addResource(resource));

        //testing the add when the warehouse is empty
        spaceAvailable = 2;
        ArrayList<Resource> resources = new ArrayList<>();
        Warehouse warehouse1 = new Warehouse(spaceAvailable, null);
        resources.add(resource);
        assertTrue(warehouse1.addResource(resource));
        assertSame(resources, warehouse1.getResources());

        //testing when you add a different resource
        Resource white = new Resource(ResourceType.ANY);
        Resource blue = new Resource(ResourceType.SHIELD);
        assertFalse(warehouse1.addResource(white));
        assertFalse(warehouse1.addResource(blue));

        //testing when you add another resource
        resources.add(resource);
        assertTrue(warehouse1.addResource(resource));
        assertSame(resources, warehouse1.getResources());

    }

    @Test
    public void changeAvailability() {
        final int newAvailability = 1;
        final int spaceAvailable = 0;
        ResourceType resourceType = ResourceType.COIN;
        Warehouse warehouse = new Warehouse(spaceAvailable, resourceType);
        assertTrue(warehouse.changeAvailability(newAvailability));
        assertEquals(newAvailability, warehouse.getSpaceAvailable());
    }


    @Test
    public void changeResources() {
        final int spaceAvailable = 0;
        ResourceType resourceType = ResourceType.COIN;
        Warehouse warehouse = new Warehouse(spaceAvailable, resourceType);
        ArrayList<Resource> resources = new ArrayList<>();
        Resource resource = new Resource(ResourceType.SHIELD);
        resources.add(resource);
        warehouse.changeResources(resources);
        assertSame(resources, warehouse.getResources());
    }
}