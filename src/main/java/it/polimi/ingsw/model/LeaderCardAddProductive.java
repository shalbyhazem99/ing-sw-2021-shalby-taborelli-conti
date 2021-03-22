package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardAddProductive extends LeaderCard implements Serializable {
    public LeaderCardAddProductive(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    public static LeaderCardAddProductive getInstance(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardAddProductive(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }
    @Override
    public boolean attiva(Player giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
