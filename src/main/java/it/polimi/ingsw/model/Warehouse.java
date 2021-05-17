package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Warehouse implements Serializable, Cloneable {
    private int spaceAvailable;
    private ResourceType resourceType;
    private java.util.ArrayList<Resource> resources;


    /**
     * This method set the values of the class warehouse
     * @param spaceAvailable it's the amount of resources that a player can store
     * @param resourceType it's the type of the resource that can be stored inside
     */
    public Warehouse(int spaceAvailable, ResourceType resourceType) {
        this.spaceAvailable = spaceAvailable;
        this.resourceType = resourceType;
        this.resources = new ArrayList<>();
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * This method is used to create a new space for the warehouse
     * @param spaceAvailable it's the amount of resources that a player can stor
     * @param resourceType it's the type of the resource that can be stored inside
     * @return the new warehouse obj created
     */
    public static Warehouse getInstance(int spaceAvailable, ResourceType resourceType){
        return new Warehouse(spaceAvailable,resourceType);
    }

    /**
     *
     * @param resource is what the player wants to add to the warehouse
     * @return a boolean state if it works correctly
     */
    public boolean addResource(Resource resource){
        if(spaceAvailable==0)
        {
            return false;
        }
        if (resources.size() > 0){ //something is already stored in the box
            if(resource.getType()==ResourceType.ANY || this.resourceType!=resource.getType()) //no match about the type
            {
                return false;
            }
            resources.add(resource);
            this.spaceAvailable= this.spaceAvailable -1;
        }
        else { //the element we're storing is the first of the box
            resourceType=resource.getType();
            resources.add(resource);
            this.spaceAvailable= this.spaceAvailable -1;
        }
        return true;
    }

    /**
     *
     * @param newAvailability number of available slots at the end of the change
     * @return true when it has finished to work
     */
    public boolean changeAvailability (int newAvailability){
        spaceAvailable = newAvailability;
        return true;
    }

    /**
     *
     * @param resourcesCount object of the items I wanted to remove
     * @return if the method worked correctly
     */
    public boolean getResources(ResourcesCount resourcesCount) {

        if(resourcesCount.getType()!= resourceType)
        {
            return false;
        }
        if (resourcesCount.getCount()> resources.size()){
            return false;
        }
        else {
            for (int i=0; i<resourcesCount.getCount(); i++)
            {
                if(resources.remove(1)!=null); //TODO: perchè 1 e non 0???
            }
        }

        return true;
    }

    public void changeResources(ArrayList<Resource> temp)
    {
        this.resources = temp;
    }

    public boolean getResource(Resource resource)
    {
        if(resources.remove(resource))
        {
            spaceAvailable++;
            if(resources.size()==0){
                resourceType=ResourceType.ANY;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Space: "+spaceAvailable+"/Type: "+resourceType.toString()+"/Res:"+resources.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
