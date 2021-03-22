package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardDeposito extends LeaderCard implements Serializable {
    public LeaderCardDeposito(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    public static LeaderCardDeposito getInstance(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardDeposito(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    @Override
    public boolean attiva(Player giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
