package it.polimi.ingsw.model;

public class Biglia {
    private ColoreBiglia colore;

    public Biglia(ColoreBiglia colore) {
        this.colore = colore;
    }

    public static Biglia getInstance(ColoreBiglia colore) {
        return new Biglia(colore);
    }

    public ColoreBiglia getColore() {
        return colore;
    }

    @Override
    public String toString() {
        return colore.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Biglia)) {
            return false;
        }
        Biglia c = (Biglia) obj;
        return this.getColore().equals(c.getColore());
    }
}
