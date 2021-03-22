package it.polimi.ingsw.model;


/**
 * @generated
 */
public class Resource {
    private ResourceType resourceType;
    public Resource(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public static Resource getInstance(ResourceType resourceType){
        return new Resource(resourceType);
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
