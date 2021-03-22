package it.polimi.ingsw.model;

public class CountRisorse {
    private int count;
    private TipoRisorsa tipo;

    public CountRisorse(int count, TipoRisorsa tipo) {
        this.count = count;
        this.tipo = tipo;
    }

    public static CountRisorse getInstance(int count, TipoRisorsa tipo){
        return new CountRisorse(count, tipo);
    }

    public int getCount() {
        return count;
    }

    public TipoRisorsa getTipo() {
        return tipo;
    }
}
