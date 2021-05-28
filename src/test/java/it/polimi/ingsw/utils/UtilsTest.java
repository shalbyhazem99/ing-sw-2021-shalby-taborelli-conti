package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class UtilsTest extends TestCase {

    @Test
    public void testApplyDiscount() {
        ArrayList<ResourcesCount> resourceNeeded = new ArrayList<>();
        ArrayList<ResourcesCount> discount = new ArrayList<>();
        ArrayList<ResourcesCount> discounted;

        //create the resource needed
        resourceNeeded.add(ResourcesCount.getInstance(2, ResourceType.COIN));
        resourceNeeded.add(ResourcesCount.getInstance(4, ResourceType.SHIELD));
        resourceNeeded.add(ResourcesCount.getInstance(2, ResourceType.SERVANT));
        resourceNeeded.add(ResourcesCount.getInstance(1, ResourceType.STONE));

        //create the discount
        discount.add(ResourcesCount.getInstance(1, ResourceType.COIN));
        discount.add(ResourcesCount.getInstance(1, ResourceType.STONE));
        discounted = Utils.applyDiscount(resourceNeeded,discount);

        //asserts on discounted
        assertEquals(1,discounted.get(0).getCount());
        assertEquals(ResourceType.COIN,discounted.get(0).getType());
        assertEquals(4,discounted.get(1).getCount());
        assertEquals(ResourceType.SHIELD,discounted.get(1).getType());
        assertEquals(2,discounted.get(2).getCount());
        assertEquals(ResourceType.SERVANT,discounted.get(2).getType());
        assertEquals(3,discounted.size());

        //asserts on resNeeded didn't change
        assertEquals(2,resourceNeeded.get(0).getCount());
        assertEquals(ResourceType.COIN,resourceNeeded.get(0).getType());
        assertEquals(4,resourceNeeded.get(1).getCount());
        assertEquals(ResourceType.SHIELD,resourceNeeded.get(1).getType());
        assertEquals(2,resourceNeeded.get(2).getCount());
        assertEquals(ResourceType.SERVANT,resourceNeeded.get(2).getType());
        assertEquals(1,resourceNeeded.get(3).getCount());
        assertEquals(ResourceType.STONE,resourceNeeded.get(3).getType());
        assertEquals(4,resourceNeeded.size());

        //assert the the discount didn't change
        assertEquals(1,discount.get(0).getCount());
        assertEquals(ResourceType.COIN,discount.get(0).getType());
        assertEquals(1,discount.get(1).getCount());
        assertEquals(ResourceType.STONE,discount.get(1).getType());
        assertEquals(2,discount.size());
    }

    @Test
    public void testCompareResources() {

        ArrayList<ResourcesCount> resourceNeeded = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        //base case
        assertFalse(Utils.compareResources(null,null));
        assertFalse(Utils.compareResources(null,resourceNeeded));
        assertFalse(Utils.compareResources(resources,null));
        assertTrue(Utils.compareResources(resources,resourceNeeded));
        //create the resource needed
        resourceNeeded.add(ResourcesCount.getInstance(2, ResourceType.COIN));
        resourceNeeded.add(ResourcesCount.getInstance(4, ResourceType.SHIELD));
        resourceNeeded.add(ResourcesCount.getInstance(2, ResourceType.SERVANT));
        resourceNeeded.add(ResourcesCount.getInstance(1, ResourceType.STONE));

        //create the resources list
        resources.add(Resource.getInstance(ResourceType.COIN));
        resources.add(Resource.getInstance(ResourceType.COIN));

        assertFalse(Utils.compareResources(resources,resourceNeeded));

        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.SHIELD));

        assertFalse(Utils.compareResources(resources,resourceNeeded));

        resources.add(Resource.getInstance(ResourceType.SERVANT));
        resources.add(Resource.getInstance(ResourceType.SERVANT));

        assertFalse(Utils.compareResources(resources,resourceNeeded));

        resources.add(Resource.getInstance(ResourceType.STONE));

        assertTrue(Utils.compareResources(resources,resourceNeeded));

        resources.add(Resource.getInstance(ResourceType.SHIELD));

        assertTrue(Utils.compareResources(resources,resourceNeeded));
    }

    @Test
    public void testFromResourcesToResourceCount() {
        ArrayList<Resource> resources = new ArrayList<>();
        int count =0;
        //create the resources list // 2 coin, 5 shield, 2 servant, 1 stone
        resources.add(Resource.getInstance(ResourceType.COIN));
        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.SERVANT));
        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.ANY));
        resources.add(Resource.getInstance(ResourceType.SHIELD));
        resources.add(Resource.getInstance(ResourceType.SERVANT));
        resources.add(Resource.getInstance(ResourceType.STONE));
        resources.add(Resource.getInstance(ResourceType.COIN));
        resources.add(Resource.getInstance(ResourceType.FAITH));
        resources.add(Resource.getInstance(ResourceType.SHIELD));


        //null case
        ArrayList<ResourcesCount> resourcesCounts = Utils.fromResourcesToResourceCount(null);
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.COIN)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 shield
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.SHIELD)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 servant
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.SERVANT)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 stone
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.STONE)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 faith
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.FAITH)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 all
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.ANY)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //size assert
        assertEquals(6,resourcesCounts.size());



        //empty case
        resourcesCounts = Utils.fromResourcesToResourceCount(new ArrayList<>());
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.COIN)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 shield
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.SHIELD)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 servant
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.SERVANT)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 stone
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.STONE)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 faith
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.FAITH)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //0 all
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.ANY)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(0, count);
        //size assert
        assertEquals(6,resourcesCounts.size());



        //normal case
        resourcesCounts = Utils.fromResourcesToResourceCount(resources);
        //2 coin
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.COIN)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(2, count);
        //5 shield
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.SHIELD)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(5, count);
        //2 servant
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.SERVANT)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(2, count);
        //1 stone
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.STONE)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(1, count);
        //1 faith
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.FAITH)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(1, count);
        //1 all
        count= resourcesCounts.stream().filter(elem->elem.getType().equals(ResourceType.ANY)).map(ResourcesCount::getCount).findFirst().get();
        assertEquals(1, count);
        //size assert
        assertEquals(6,resourcesCounts.size());
    }

}