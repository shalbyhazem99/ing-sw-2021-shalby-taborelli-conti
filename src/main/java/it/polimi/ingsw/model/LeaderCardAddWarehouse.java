package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardAddWarehouse extends LeaderCard implements Serializable {
    public LeaderCardAddWarehouse(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    public static LeaderCardAddWarehouse getInstance(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardAddWarehouse(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    @Override
    public boolean attiva(Player giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
