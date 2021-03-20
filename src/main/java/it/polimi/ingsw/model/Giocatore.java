package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Giocatore {
    private int posizionePercorso;
    private java.util.ArrayList<TesseraPapale> tesserePapali;
    private java.util.ArrayList<CartaLeader> carteLeader;
    private java.util.ArrayList<SpazioCarteSviluppo> spazioSviluppo;
    private java.util.ArrayList<Deposito> depositiStandard;
    private ArrayList<Risorsa> forziere;
    private java.util.ArrayList<Deposito> depositiAggiuntivi;
    private ArrayList<CountRisorse> sconti;
    private ArrayList<TipoRisorsa> strategieConversione;
    private ArrayList<PotereProduttivo> poteriAggiuntivi;

    public static Giocatore generatePlayer(){
        //TODO:genera n giocatore
        return null;
    }

    public void procediPerorsoFede(int numPosizioni){
        //TODO: va avanti
    }

    public Boolean addCartaSiluppo(CartaSviluppo cartaSviluppo){
        //TODO: fare il controllo se i pò agiungere e aggiungerla. bisogna vedere come ricevere lo spazio sviluppo in cui provare ad aggiungerla
        return false;
    }
    public void addRisorsaForziere(Risorsa risorsa){
        //TODO:Aahhiunge una risorsa al forziere
    }

}
