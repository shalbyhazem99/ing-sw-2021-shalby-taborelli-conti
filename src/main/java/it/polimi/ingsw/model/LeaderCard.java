package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class LeaderCard implements Serializable {
    private boolean attiva;
    private final int point;
    //private final TipoRisorsa tipoRisorsa;
    private final ArrayList<ResourcesCount> ResourcesNeeded; //todo: to remove
    private final ArrayList<DevelopmentCardNeeded> developmentCardNeeded;

    public LeaderCard(int point, ArrayList<ResourcesCount> ResourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        this.point = point;
        this.ResourcesNeeded = ResourcesNeeded;
        this.developmentCardNeeded = developmentCardNeeded;
        this.attiva=false;
    }

    private boolean attivabile(Player player){
        //Todo: verificare se attivabile dal giocatore
        return false;
    }

    public abstract boolean attiva(Player gioctore);

    public Boolean isAttiva(){
        return attiva;
    }
}
