package it.polimi.ingsw.model;

import java.io.Serializable;

public enum DevelopmentCardType implements Serializable {
    GREEN(0),
    BLUE(1),
    YELLOW(2),
    PURPLE(3);
    public final int label;

    private DevelopmentCardType(int label) {
        this.label = label;
    }
}
