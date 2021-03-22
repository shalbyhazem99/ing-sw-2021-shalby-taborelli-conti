package it.polimi.ingsw.model;

public class CountResources {
    private int count;
    private ResourceType type;

    public CountResources(int count, ResourceType type) {
        this.count = count;
        this.type = type;
    }

    public static CountResources getInstance(int count, ResourceType resourceType){
        return new CountResources(count, resourceType);
    }

    public int getCount() {
        return count;
    }

    public ResourceType getType() {
        return type;
    }
}
