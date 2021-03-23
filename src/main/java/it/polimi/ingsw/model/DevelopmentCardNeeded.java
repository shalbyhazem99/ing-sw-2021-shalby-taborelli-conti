package it.polimi.ingsw.model;

import java.io.Serializable;

public class DevelopmentCardNeeded implements Serializable {
    private int count;
    private DevelopmentCardType type;
    private DevelopmentCardLevel level;

    public int getCount() {
        return count;
    }

    public DevelopmentCardType getType() {
        return type;
    }

    public DevelopmentCardLevel getLevel() {
        return level;
    }
}
