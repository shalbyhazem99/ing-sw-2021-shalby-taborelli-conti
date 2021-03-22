package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Stack;

public class Partita {
    private int posCalamaio;
    private java.util.ArrayList<Player> giocatori;
    private java.util.ArrayList<CartaLeader> carteLeader;
    private Stack<CartaSviluppo>[][] carteSviluppo;
    private StrutturaMercato mercato;
    private ArrayList<SegnalinoAzione> segnaliniAzione;
    private boolean solitario;
    private int posCroceNera;


    public void buyCard(Player player, TipologiaCartaSviluppo tipologiaCartaSviluppo, int livello, int posSpazio){
        //todo:add card to the palyer
    }
}
