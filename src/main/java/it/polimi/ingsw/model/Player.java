package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private int posizionePercorso;
    private ArrayList<TesseraPapale> tesserePapali;
    private ArrayList<CartaLeader> carteLeader;
    private ArrayList<SpazioCarteSviluppo> spazioSviluppo;
    private ArrayList<Deposito> depositiStandard;
    private ArrayList<Risorsa> forziere;
    private ArrayList<Deposito> depositiAggiuntivi;
    private ArrayList<CountRisorse> sconti;
    private ArrayList<TipoRisorsa> strategieConversione;
    private ArrayList<PotereProduttivo> poteriAggiuntivi;

    //todo: it has a power?
    public static Player generatePlayer(){
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
