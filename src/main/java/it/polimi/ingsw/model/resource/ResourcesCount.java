package it.polimi.ingsw.model.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents a group of same {@link Resource}
 */
public class ResourcesCount implements Serializable, Cloneable {
    private int count;
    private ResourceType type;

    /**
     * Constructor
      * @param count the number of {@link Resource} inside
     * @param type {@link ResourceType} common for all the {@link Resource} inside
     */
    public ResourcesCount(int count, ResourceType type) {
        this.count = count;
        this.type = type;
    }

    /**
     * Create an instance of ResourceCount
     * @param count the number of {@link Resource} inside
     * @param type @link ResourceType} common for all the {@link Resource} inside
     * @return an instance of ResourceCount
     */
    public static ResourcesCount getInstance(int count, ResourceType type) {
        return new ResourcesCount(count, type);
    }

    /**
     *
     * @return the number of {@link Resource} inside
     */
    public int getCount() {
        return count;
    }

    /**
     * The count of {@link Resource} inside is decreased by 1
     */
    public void decreaseCount() {
        count -= 1;
    }

    /**
     *
     * @return {@link ResourceType} inside
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * It is used to set the {@link ResourceType} of the ResourceCount after the construction
     * @param type {@link ResourceType} inside
     */
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

    /**
     * Method used to increase the count of the ResourceCount by 1
     */
    public void addCount() {
        this.count++;
    }

    @Override
    public String toString() {
        return "#" + count + " " + type.toString();
    }


    @Override
    public boolean equals(Object obj) {
        return type.equals(((ResourcesCount) obj).getType()) && count ==((ResourcesCount) obj).getCount();
    }
}
