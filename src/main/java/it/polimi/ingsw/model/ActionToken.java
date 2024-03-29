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
     * Return an instance of {@link ActionToken}
     * @param action {@link MarkerType} action
     * @param count it represents how many cards tha player must discard or how many cells the BlackCross must run across
     * @param cardToReject it represents the {@link DevelopmentCardType} the player must discard
     * @return the instance of {@link ActionToken}
     */
    public static ActionToken getInstance(MarkerType action, int count, DevelopmentCardType cardToReject) {
        return new ActionToken(action,count,cardToReject);
    }

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
        return cardToReject;
    }

    /**
     * Default toString
     * @return the parameters of {@link ActionToken}
     */
    @Override
    public String toString() {
        return "ActionToken{" +
                "action=" + action +
                ", count=" + count +
                ", cardToReject=" + cardToReject +
                '}';
    }
}
