package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Partita {
    private int posCalamaio;
    private Riserva riserva;
    private java.util.ArrayList<Giocatore> giocatori;
    private java.util.ArrayList<CartaLeader> carteLeader;
    private java.util.ArrayList<CartaSviluppo>[][] carteSviluppo;
    private StrutturaMercato mercato;
    private ArrayList<SegnalinoAzione> segnaliniAzione;
    private boolean solitario;
}
