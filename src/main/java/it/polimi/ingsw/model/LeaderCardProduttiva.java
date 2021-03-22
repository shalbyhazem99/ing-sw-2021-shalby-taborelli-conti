package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderCardProduttiva extends LeaderCard implements Serializable {
    public LeaderCardProduttiva(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }

    public static LeaderCardProduttiva getInstance(int puntiVittoria, ArrayList<ResourcesCount> risorseRichieste, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardProduttiva(puntiVittoria, risorseRichieste, developmentCardNeeded);
    }
    @Override
    public boolean attiva(Player giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
