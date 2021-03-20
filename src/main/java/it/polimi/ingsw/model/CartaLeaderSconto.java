package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CartaLeaderSconto extends CartaLeader {
    public CartaLeaderSconto(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        super(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    public static CartaLeaderSconto getInstance(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        return new CartaLeaderSconto(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    @Override
    public boolean attiva(Giocatore giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
