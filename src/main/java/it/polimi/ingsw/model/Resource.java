package it.polimi.ingsw.model;


import java.io.Serializable;

/**
 * @generated
 */
public class Resource implements Serializable {
    private ResourceType type;
    public Resource(ResourceType type) {
        this.type = type;
    }

    public static Resource getInstance(ResourceType resourceType){
        return new Resource(resourceType);
    }

    public ResourceType getType() {
        return type;
    }
}
