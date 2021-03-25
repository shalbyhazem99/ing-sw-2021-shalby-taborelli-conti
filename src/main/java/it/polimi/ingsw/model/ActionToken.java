package it.polimi.ingsw.model;

import java.io.Serializable;

public class ActionToken implements Serializable {
    /**
     * Class representing the actionTokens involved into Solo Matches
     */

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
}
