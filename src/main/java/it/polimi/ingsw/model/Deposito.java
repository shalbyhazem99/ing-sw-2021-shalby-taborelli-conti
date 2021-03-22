package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Warehouse {
    private int availableSpace;
    private ResourceType resourceType;
    private java.util.ArrayList<Resource> resources;

    public Warehouse(int availableSpace, ResourceType resourceType) {
        this.availableSpace = availableSpace;
        this.resourceType = resourceType;
        this.resources = new ArrayList<>();
    }

    public static Warehouse getInstance(int availableSpace, ResourceType resourceType){
        return new Warehouse(availableSpace,resourceType);
    }
    public boolean addResource(Resource resource){
        //TODO: Add resource to warehouse
        return false;
    }

    //TODO: methods to change th resources in the warehouses
}
