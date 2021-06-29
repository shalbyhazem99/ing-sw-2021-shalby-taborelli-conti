package it.polimi.ingsw.model.developmentCard;

import java.io.Serializable;

public class DevelopmentCardNeeded implements Serializable {
    private int count;
    private DevelopmentCardType type;
    private DevelopmentCardLevel level;

    /**
     * Default constructor
     * @param count it is the number of {@link DevelopmentCard} wanted
     * @param type it is  the type of {@link DevelopmentCard} wanted
     * @param level it is the level of {@link DevelopmentCard} wanted
     */
    public DevelopmentCardNeeded(int count, DevelopmentCardType type, DevelopmentCardLevel level) {
        this.count = count;
        this.type = type;
        this.level = level;
    }

    /**
     *
     * @return the number of DevelopmentCard needed
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return {@link DevelopmentCardType} of the DevelopmentCard needed
     */
    public DevelopmentCardType getType() {
        return type;
    }

    /**
     *
     * @return {@link DevelopmentCardLevel} of the DevelopmentCard needed
     */
    public DevelopmentCardLevel getLevel() {
        return level;
    }

    /**
     *
     * @return the conversion in string
     */
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
