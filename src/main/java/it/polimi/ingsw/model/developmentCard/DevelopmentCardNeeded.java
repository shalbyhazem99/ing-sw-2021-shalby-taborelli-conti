package it.polimi.ingsw.model.developmentCard;

import java.io.Serializable;

public class DevelopmentCardNeeded implements Serializable {
    private int count;
    private DevelopmentCardType type;
    private DevelopmentCardLevel level;

    public DevelopmentCardNeeded(int count, DevelopmentCardType type, DevelopmentCardLevel level) {
        this.count = count;
        this.type = type;
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

    @Override
    public String toString() {
        String l = "";
        if(level!=null)
        {
            l = level.toString();
        }
        return count+" "+l+" "+type.toString();
    }
}
