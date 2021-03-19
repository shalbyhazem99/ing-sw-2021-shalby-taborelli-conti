package it.polimi.ingsw.model;

public class Biglia {
    private ColoreBiglia colore;

    public Biglia(ColoreBiglia colore) {
        this.colore = colore;
    }

    public ColoreBiglia getColore() {
        return colore;
    }

    @Override
    public String toString() {
        return colore.toString();
    }
}
