package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

import java.util.Scanner;

public class EnableLeaderCardPlayerResponse extends MoveResponse {

    LeaderCard leaderCardActivated;

    public EnableLeaderCardPlayerResponse(LeaderCard leaderCardActivated) {
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

    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }
}
