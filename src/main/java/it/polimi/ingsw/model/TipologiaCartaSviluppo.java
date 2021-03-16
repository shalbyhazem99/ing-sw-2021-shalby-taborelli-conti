package it.polimi.ingsw.model;

public enum TipologiaCartaSviluppo {
    VERDE(0),
    BLU(1),
    GIALLO(2),
    VIOLA(3);
    public final int label;

    private TipologiaCartaSviluppo(int label) {
        this.label = label;
    }
}
