package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

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

    /**
     * Method to convert the {@link ResourcesCount} class to an {@link ArrayList} of {@link Resource}
     * @return a {@link ArrayList} of {@link Resource} equivalent to the object
     */
    public ArrayList<Resource> toArrayListResources()
    {
        return new ArrayList<Resource>() {{
            for(int i =0;i<count;i++)
            {
                add(Resource.getInstance(type));
            }
        }};
    }

    public void addCount()
    {
        this.count ++;
    }

    @Override
    public String toString() {
        return "#"+count+" "+type.toString();
    }
}
