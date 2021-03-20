package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Deposito {
    private int spazioDisponibile;
    private TipoRisorsa tipoRisorse;
    private java.util.ArrayList<Risorsa> risorse;

    public Deposito(int spazioDisponibile, TipoRisorsa tipoRisorse) {
        this.spazioDisponibile = spazioDisponibile;
        this.tipoRisorse = tipoRisorse;
        this.risorse = new ArrayList<>();
    }

    public static Deposito getInstance(int spazioDisponibile, TipoRisorsa tipoRisorse){
        return new Deposito(spazioDisponibile,tipoRisorse);
    }
    public boolean addRisorsa(Risorsa risorsa){
        //TODO: Aggiungi risorsa a deposito
        return false;
    }

    //TODO: metodi per scambiare le risorse da un deposito all'altro
}
