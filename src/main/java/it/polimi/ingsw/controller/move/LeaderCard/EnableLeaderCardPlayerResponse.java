package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class EnableLeaderCardPlayerResponse implements MoveResponse {

    LeaderCard leaderCardActivated;

    /**
     * default constructor
     * @param leaderCardActivated
     */
    public void setLeaderCardActivated(LeaderCard leaderCardActivated) {
        this.leaderCardActivated = leaderCardActivated;
    }

    /**
     * Default getInstance
     * @param leaderCardActivated
     * @return
     */
    public static EnableLeaderCardPlayerResponse getInstance(LeaderCard leaderCardActivated){
        return new EnableLeaderCardPlayerResponse(leaderCardActivated);
    }
}
