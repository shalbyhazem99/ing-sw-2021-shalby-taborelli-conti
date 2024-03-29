package it.polimi.ingsw.controller.move.production.move;


import it.polimi.ingsw.model.resource.ResourceType;

import java.io.Serializable;

public class ResourcePick implements Serializable {
    private ResourceWarehouseType resourceWarehouseType; //warehouse or strongbox
    private int warehousePosition; //if a warehouse used
    private ResourceType resourceType; // the resources to get

    public ResourcePick(ResourceWarehouseType resourceWarehouseType, int warehousePosition, ResourceType resourceType) {
        this.resourceWarehouseType = resourceWarehouseType;
        this.warehousePosition = warehousePosition;
        this.resourceType = resourceType;
    }

    public static ResourcePick getInstance(ResourceWarehouseType resourceWarehouseType, int warehousePosition, ResourceType resourceType) {
        return new ResourcePick(resourceWarehouseType, warehousePosition, resourceType);
    }

    public ResourceWarehouseType getResourceWarehouseType() {
        return resourceWarehouseType;
    }

    public int getWarehousePosition() {
        return warehousePosition;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setResourceWarehouseType(ResourceWarehouseType resourceWarehouseType) {
        this.resourceWarehouseType = resourceWarehouseType;
    }

    public void setWarehousePosition(int warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public String toString(){
        return warehousePosition+","+resourceType;
    }
}
