package it.polimi.ingsw.model.market;

import java.io.Serializable;

public class Marble implements Serializable {
    private final MarbleColor color;

    public Marble(MarbleColor color) {
        this.color = color;
    }

    public static Marble getInstance(MarbleColor colore) {
        return new Marble(colore);
    }

    public MarbleColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Marble)) {
            return false;
        }
        Marble c = (Marble) obj;
        return this.getColor().equals(c.getColor());
    }
}
