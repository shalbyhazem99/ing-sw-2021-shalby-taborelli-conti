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
    //TODO: va rimosso
    public Warehouse(int spaceAvailable, ResourceType resourceType,ArrayList<Resource> r) {
        this.spaceAvailable = spaceAvailable;
        this.resourceType = resourceType;
        this.resources = (ArrayList<Resource>) r.clone();
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
}
