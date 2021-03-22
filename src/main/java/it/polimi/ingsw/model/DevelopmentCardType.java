package it.polimi.ingsw.model;

import java.io.Serializable;

public enum DevelopmentCardType implements Serializable {
    VERDE(0),
    BLU(1),
    GIALLO(2),
    VIOLA(3);
    public final int label;

    private DevelopmentCardType(int label) {
        this.label = label;
    }
}
