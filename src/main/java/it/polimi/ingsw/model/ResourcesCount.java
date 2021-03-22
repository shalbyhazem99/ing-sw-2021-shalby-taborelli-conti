package it.polimi.ingsw.model;

import java.io.Serializable;

public class ResourcesCount implements Serializable {
    private int count;
    private ResourceType type;

    public ResourcesCount(int count, ResourceType type) {
        this.count = count;
        this.type = type;
    }

    public static ResourcesCount getInstance(int count, ResourceType type){
        return new ResourcesCount(count, type);
    }

    public int getCount() {
        return count;
    }

    public ResourceType getType() {
        return type;
    }
}
