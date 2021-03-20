package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CartaLeaderDeposito extends CartaLeader {
    public CartaLeaderDeposito(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        super(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    public static CartaLeaderDeposito getInstance(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        return new CartaLeaderDeposito(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    @Override
    public boolean attiva(Giocatore giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
