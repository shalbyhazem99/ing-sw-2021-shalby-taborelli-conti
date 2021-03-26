package it.polimi.ingsw.model;

import java.io.Serializable;

public class ActionToken implements Serializable {
    private MarkerType action;
    private int count;
    private DevelopmentCardType CardToReject;

    /**
     *
     * @return the type of the action
     */
    public MarkerType getAction() {
        return action;
    }

    /**
     *
     * @return the amount of the victory points
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return the card that will be rejected
     */
    public DevelopmentCardType getCardToReject() {
        return CardToReject;
    }
}
