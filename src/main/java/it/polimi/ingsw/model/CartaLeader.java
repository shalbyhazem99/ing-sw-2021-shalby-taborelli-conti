package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class CartaLeader {
    private boolean attiva;
    private int puntiVittoria;
    private java.util.ArrayList<CountRisorse> risorseRichieste;
    private java.util.ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste;

    public CartaLeader(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        this.puntiVittoria = puntiVittoria;
        this.risorseRichieste = risorseRichieste;
        this.carteSviluppoRichieste = carteSviluppoRichieste;
        this.attiva=false;
    }

    private boolean attivabile(Giocatore giocatore){
        //Todo: verificare se attivabile dal giocatore
        return false;
    }

    public abstract boolean attiva(Giocatore gioctore);

    public Boolean isAttiva(){
        return attiva;
    }
}
