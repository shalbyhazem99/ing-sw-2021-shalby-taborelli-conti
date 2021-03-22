package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private int posFaithMarker;
    private ArrayList<PopeFavorTiles> popeFavorTiles;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<DevelopmentCardSpace> developmentCardSpaces;
    private ArrayList<Warehouse> warehousesStandard;
    private ArrayList<Resource> strongBox;
    private ArrayList<Warehouse> warehousesAdditional;
    private ArrayList<ResourcesCount> discounts;
    private ArrayList<ResourceType> ConversionStrategies;
    private ArrayList<ProductivePower> addedPower;

    //todo: it has a power?
    public static Player generatePlayer(){
        //TODO:genera n giocatore
        return null;
    }

    public void procediPerorsoFede(int numPosizioni){
        //TODO: va avanti
    }

    public Boolean addCartaSiluppo(DevelopmentCard developmentCard){
        //TODO: fare il controllo se i pò agiungere e aggiungerla. bisogna vedere come ricevere lo spazio sviluppo in cui provare ad aggiungerla
        return false;
    }
    public void addRisorsaForziere(Resource resource){
        //TODO:Aahhiunge una risorsa al forziere
    }

}
