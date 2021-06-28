package it.polimi.ingsw.model.developmentCard;

import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.*;

/**
 * class that create the {@link DevelopmentCardSpace}
 */
public class DevelopmentCardSpace implements Serializable {
    private Stack<DevelopmentCard> developmentCards;

    /**
     * constructor of {@link DevelopmentCardSpace}
     */
    public DevelopmentCardSpace() {
        this.developmentCards = new Stack<>();
    }

    /**
     * create an istance of {@link DevelopmentCardSpace}
     *
     * @return an istance of {@link DevelopmentCardSpace}
     */
    public static DevelopmentCardSpace getInstance() {
        return new DevelopmentCardSpace();
    }

    /**
     * add a {@link DevelopmentCard} to the {@link DevelopmentCardSpace}
     *
     * @param developmentCard the {@link DevelopmentCard} to add
     * @return true is successful, false otherwise
     */
    public boolean add(DevelopmentCard developmentCard) {
        if (canBeAdded(developmentCard)) {
            return developmentCards.push(developmentCard) != null;
        }
        return false;
    }

    /**
     * Method to know if a {@link DevelopmentCard} can be added in this {@link DevelopmentCardSpace}
     *
     * @param developmentCard the {@link DevelopmentCard} to check if it can be added
     * @return true <==> the {@link DevelopmentCard} can be added
     */
    public boolean canBeAdded(DevelopmentCard developmentCard) {
        return developmentCards.size() == developmentCard.getLevel().label;
    }

    /**
     * transform the stack of {@link DevelopmentCard} to an {@link ArrayList}
     *
     * @return an {@link ArrayList} of {@link DevelopmentCard}
     */
    public ArrayList<DevelopmentCard> linearize() {
        return new ArrayList<>(developmentCards);
    }

    /**
     * Method used to get (not remove) the card placed on the top
     *
     * @return the {@link DevelopmentCard} placed on the top of the {@link Stack} of the {@link DevelopmentCardSpace}
     */
    public DevelopmentCard pickTopCard() {
        if(!developmentCards.isEmpty()) {
            return developmentCards.peek();
        }
        return null;
    }


    @Override
    public String toString() {
        String temp = "";
        int max = 30;
        if (!developmentCards.isEmpty()) {
            temp += ("        | P:| " + Integer.valueOf(developmentCards.peek().getEquivalentPoint()).toString() + Utils.fillSpaces(max, Integer.valueOf(developmentCards.peek().getEquivalentPoint()).toString().length()));
            temp += ("LVL = " + 1 + " | C:| " + developmentCards.peek().getCostsFormatted() + Utils.fillSpaces(max, developmentCards.peek().getCostsFormatted().length()));
            temp += ("        |PP:| " + developmentCards.peek().getPowersFormatted() + Utils.fillSpaces(max, developmentCards.peek().getPowersFormatted().length()));
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
        return temp;
    }
}
