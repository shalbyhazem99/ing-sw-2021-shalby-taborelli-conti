package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Warehouse implements Serializable {
    private int spaceAvailable;
    private ResourceType resourceType;
    private java.util.ArrayList<Resource> resources;

    public Warehouse(int spaceAvailable, ResourceType resourceType) {
        this.spaceAvailable = spaceAvailable;
        this.resourceType = resourceType;
        this.resources = new ArrayList<>();
    }

    public static Warehouse getInstance(int spaceAvailable, ResourceType resourceType){
        return new Warehouse(spaceAvailable,resourceType);
    }
    public boolean addResource(Resource resource){
        //TODO: Aggiungi risorsa a deposito
        return false;
    }
    //lo implementa tabo

    public ArrayList<Resource> getResources() {
        return (ArrayList<Resource>)resources.clone();
    }


    //TODO: metodi per scambiare le risorse da un deposito all'altro

    //TODO: Sposta , aggiungi risorse, preleva risorse
}
