package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardDiscount extends LeaderCard implements Serializable {
    public LeaderCardDiscount(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    public static LeaderCardDiscount getInstance(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardDiscount(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    @Override
    public boolean attiva(Player giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
