package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.io.Serializable;

public class ActionToken implements Serializable {
    private MarkerType action;
    private int count;
    private DevelopmentCardType cardToReject;


    /**
     * Default constructor
     * @param action {@link MarkerType} action
     * @param count it represents how many cards tha player must discard or how many cells the BlackCross must run across
     * @param cardToReject it represents the {@link DevelopmentCardType} the player must discard
     */
    public ActionToken(MarkerType action, int count, DevelopmentCardType cardToReject) {
        this.action = action;
        this.count = count;
        this.cardToReject = cardToReject;
    }

    /**
     * Return an istance of {@link ActionToken}
     * @param action {@link MarkerType} action
     * @param count it represents how many cards tha player must discard or how many cells the BlackCross must run across
     * @param cardToReject it represents the {@link DevelopmentCardType} the player must discard
     * @return the istance of {@link ActionToken}
     */
    public static ActionToken getInstance(MarkerType action, int count, DevelopmentCardType cardToReject) {
        return new ActionToken(action,count,cardToReject);
    }
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
