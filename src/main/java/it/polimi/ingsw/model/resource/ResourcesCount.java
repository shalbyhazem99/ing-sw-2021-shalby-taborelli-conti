package it.polimi.ingsw.model.resource;

import java.io.Serializable;
import java.util.ArrayList;

public class ResourcesCount implements Serializable, Cloneable {
    private int count;
    private ResourceType type;

    public ResourcesCount(int count, ResourceType type) {
        this.count = count;
        this.type = type;
    }

    public static ResourcesCount getInstance(int count, ResourceType type) {
        return new ResourcesCount(count, type);
    }

    public int getCount() {
        return count;
    }

    public void decreaseCount() {
        count -= 1;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    /**
     * Method to convert the {@link ResourcesCount} class to an {@link ArrayList} of {@link Resource}
     *
     * @return a {@link ArrayList} of {@link Resource} equivalent to the object
     */
    public ArrayList<Resource> toArrayListResources() {
        return new ArrayList<>() {{
            for (int i = 0; i < count; i++) {
                add(Resource.getInstance(type));
            }
        }};
    }

    public static ArrayList<ResourcesCount> cloneList(ArrayList<ResourcesCount> list) {
     ArrayList<ResourcesCount> clone = new ArrayList<>();
        for (ResourcesCount res:list) {
            clone.add(ResourcesCount.getInstance(res.getCount(), res.getType()));
        }
        return clone;
    }

    public void addCount() {
        this.count++;
    }

    @Override
    public String toString() {
        return "#" + count + " " + type.toString();
    }
}
