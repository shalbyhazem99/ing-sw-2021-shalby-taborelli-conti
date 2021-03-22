package it.polimi.ingsw.model;

public class Marble {
    private ColorMarble color;

    public (ColorMarble color) {
        this.color = color;
    }

    public static  getInstance(ColorMarble color) {
        return new (color);
    }

    public ColorMarble getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
