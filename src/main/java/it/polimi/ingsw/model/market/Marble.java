package it.polimi.ingsw.model.market;

import java.io.Serializable;

/**
 * Class that represent the Marbles of the {@link MarketBoard}
 */
public class Marble implements Serializable,Cloneable {
    private final MarbleColor color;

    public Marble(MarbleColor color) {
        this.color = color;
    }

    public static Marble getInstance(MarbleColor color) {
        return new Marble(color);
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return getInstance(getColor());
    }
}
