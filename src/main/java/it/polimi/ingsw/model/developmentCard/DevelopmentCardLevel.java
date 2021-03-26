package it.polimi.ingsw.model.developmentCard;

import java.io.Serializable;

public enum DevelopmentCardLevel implements Serializable {
    FIRST(2),
    SECOND(1),
    THIRD (0);
    public final int label;

    private DevelopmentCardLevel(int label) {
        this.label = label;
    }
}
