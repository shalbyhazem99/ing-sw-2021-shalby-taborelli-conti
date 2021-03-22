package it.polimi.ingsw.model;

public class TesseraPapale {
    private final int punti;
    private boolean abilitata;

    public TesseraPapale(int punti) {
        this.punti = punti;
        this.abilitata = false;
    }

    public static TesseraPapale getInstance(int punti){
        return new TesseraPapale(punti);
    }

    public void abilita(){
        if(!abilitata)
            abilitata=true;
    }

}
