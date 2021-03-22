package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class CartaLeader {
    private boolean attiva;
    private final int puntiVittoria;
    //private final TipoRisorsa tipoRisorsa;
    private final ArrayList<CountRisorse> risorseRichieste; //todo: to remove
    private final ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste;

    public CartaLeader(int puntiVittoria, ArrayList<CountRisorse> risorseRichieste, ArrayList<CarteSviluppoRichieste> carteSviluppoRichieste) {
        this.puntiVittoria = puntiVittoria;
        this.risorseRichieste = risorseRichieste;
        this.carteSviluppoRichieste = carteSviluppoRichieste;
        this.attiva=false;
    }

    private boolean attivabile(Player player){
        //Todo: verificare se attivabile dal giocatore
        return false;
    }

    public abstract boolean attiva(Player gioctore);

    public Boolean isAttiva(){
        return attiva;
    }
}
