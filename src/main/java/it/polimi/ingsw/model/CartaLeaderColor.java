package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CartaLeaderColor extends CartaLeader {

    public CartaLeaderColor(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        super(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    public static CartaLeaderColor getInstance(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste){
        return new CartaLeaderColor(puntiVittoria, risorseRichieste, carteSviluppoRichieste);
    }

    @Override
    public boolean attiva(Giocatore giocatore) {
        //TODO: ATTIVA se attivabile e sistema paramtro giocatore
        return false;
    }
}
