package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

import java.util.Scanner;

public class DiscardLeaderCardPlayerResponse extends MoveResponse {
    /**
     * class used to response of the system when the {@link it.polimi.ingsw.model.Player} interacts with the ProductionSystem
     */

    private LeaderCard leaderCardActivated;

    /**
     * Default constructor
     * @param leaderCardActivated
     */
    public DiscardLeaderCardPlayerResponse(LeaderCard leaderCardActivated) {
        this.leaderCardActivated = leaderCardActivated;
    }

    public static DiscardLeaderCardPlayerResponse getInstance(LeaderCard leaderCardActivated){
        return new DiscardLeaderCardPlayerResponse(leaderCardActivated);
    }

    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }
}
