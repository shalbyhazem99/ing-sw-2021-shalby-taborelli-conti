package it.polimi.ingsw.model.resource;


import java.io.Serializable;

/**
 * Class that represent the single Resource of the Game
 */
public class Resource implements Serializable, Cloneable {
    private final ResourceType type;

    /**
     * Constructor
     * @param type {@link ResourceType} of the Resource
     */
    public Resource(ResourceType type) {
        this.type = type;
    }

    /**
     * Create an instance of a Resource
     * @param resourceType @link ResourceType} of the Resourc
     * @return an instance of a Resource
     */
    public static Resource getInstance(ResourceType resourceType){
        return new Resource(resourceType);
    }

    /**
     *
     * @return the {@link ResourceType} of the Resource
     */
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
