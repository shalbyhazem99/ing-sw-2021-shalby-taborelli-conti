package it.polimi.ingsw.model.developmentCard;

import java.io.Serializable;

public class DevelopmentCardNeeded implements Serializable {
    private int count;
    private DevelopmentCardType type;
    private DevelopmentCardLevel level;

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(DevelopmentCardType type) {
        this.type = type;
    }

    public void setLevel(DevelopmentCardLevel level) {
        this.level = level;
    }

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
