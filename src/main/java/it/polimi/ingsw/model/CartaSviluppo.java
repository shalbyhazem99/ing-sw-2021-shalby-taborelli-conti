package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CartaSviluppo {
    private LivelloCartaSviluppo livello;
    private TipologiaCartaSviluppo tipologia;
    private int puntiVittoria;
    private java.util.ArrayList<CountRisorse> costi;
    private PotereProduttivo potere;

    public CartaSviluppo(LivelloCartaSviluppo livello, TipologiaCartaSviluppo tipologia, int puntiVittoria, ArrayList<CountRisorse> costi, PotereProduttivo potere) {
        this.livello = livello;
        this.tipologia = tipologia;
        this.puntiVittoria = puntiVittoria;
        this.costi = costi;
        this.potere = potere;
    }

    public static CartaSviluppo getInstance(LivelloCartaSviluppo livello, TipologiaCartaSviluppo tipologia, int puntiVittoria, ArrayList<CountRisorse> costi, PotereProduttivo potere) {
        return new CartaSviluppo(livello, tipologia, puntiVittoria, costi, potere);
    }

    public boolean isBuyableFrom(Giocatore giocatore){
        //TODO: CONTROLO SUI COSTI
        return false;
    }

    public LivelloCartaSviluppo getLivello() {
        return livello;
    }

    public TipologiaCartaSviluppo getTipologia() {
        return tipologia;
    }

    public int getPuntiVittoria() {
        return puntiVittoria;
    }

    public PotereProduttivo getPotere() {
        return potere;
    }
}
