package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Stack;

public class Partita {
    private int posCalamaio;
    private Riserva riserva;
    private java.util.ArrayList<Giocatore> giocatori;
    private java.util.ArrayList<CartaLeader> carteLeader;
    private Stack<CartaSviluppo>[][] carteSviluppo;
    private StrutturaMercato mercato;
    private ArrayList<SegnalinoAzione> segnaliniAzione;
    private boolean solitario;
    private int posCroceNera;
}
