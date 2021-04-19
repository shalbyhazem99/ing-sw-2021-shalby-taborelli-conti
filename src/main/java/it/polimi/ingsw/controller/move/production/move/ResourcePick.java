package it.polimi.ingsw.controller.move.production.move;

import it.polimi.ingsw.model.ResourcesCount;

public class ResourcePick {
    private ResourceWarehouseType resourceWarehouseType; //warehouse or strongbox
    private int warehousePosition; //if a warehouse used
    private ResourcesCount resourcesCount; // the resources to get

    public ResourcePick(ResourceWarehouseType resourceWarehouseType, int warehousePosition, ResourcesCount resourcesCount) {
        this.resourceWarehouseType = resourceWarehouseType;
        this.warehousePosition = warehousePosition;
        this.resourcesCount = resourcesCount;
    }

    public static ResourcePick getInstance(ResourceWarehouseType resourceWarehouseType, int warehousePosition, ResourcesCount resourcesCount) {
        return new ResourcePick(resourceWarehouseType, warehousePosition, resourcesCount);
    }

    public ResourceWarehouseType getResourceWarehouseType() {
        return resourceWarehouseType;
    }

    public int getWarehousePosition() {
        return warehousePosition;
    }

    public ResourcesCount getResourcesCount() {
        return resourcesCount;
    }
}
