package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CartaLeaderProduttiva extends CartaLeader {
    public CartaLeaderProduttiva(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        super(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    public static CartaLeaderProduttiva getInstance(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        return new CartaLeaderProduttiva(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }
    @Override
    public boolean attiva(Giocatore giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
