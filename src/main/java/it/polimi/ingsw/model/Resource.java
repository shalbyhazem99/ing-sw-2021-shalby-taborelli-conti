package it.polimi.ingsw.model;


import java.io.Serializable;

/**
 * @generated
 */
public class Resource implements Serializable, Cloneable {
    private final ResourceType type;
    public Resource(ResourceType type) {
        this.type = type;
    }

    public static Resource getInstance(ResourceType resourceType){
        return new Resource(resourceType);
    }

    public ResourceType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Resource)) {
            return false;
        }
        return type.equals(((Resource) obj).getType());
    }

    @Override
    protected Object clone()   {
        return getInstance(type);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
