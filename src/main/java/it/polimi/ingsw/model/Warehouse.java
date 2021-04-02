package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Warehouse implements Serializable {
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
    //TODO: NON MANCA UN CONTROLLO PER VEDERE IL TIPO DELLA RISORSA
    public boolean addResource(Resource resource){
        if (resources.size() > 0){
            resources.add(resource);
            spaceAvailable=spaceAvailable-1;
        }
        else {
            resourceType=resource.getType();
            resources.add(resource);
            spaceAvailable=spaceAvailable-1;
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
     * @param donator is the other warehouse that player wants to swap
     * @return true when it has finished to work
     */

    //Check the behavioral of the warehouses in view mode when something is changed
    public boolean changeResources (Warehouse donator){

        int temp = donator.spaceAvailable;
        donator.changeAvailability(this.spaceAvailable);
        this.spaceAvailable=temp;

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

    public boolean getResource(Resource resource)
    {
        //Dovrebbe funzionare correttamente avendo ridefinito equals di resource
        if(resources.remove(resource))
        {
            spaceAvailable--;
            return true;
        }
        return false;
    }
}
