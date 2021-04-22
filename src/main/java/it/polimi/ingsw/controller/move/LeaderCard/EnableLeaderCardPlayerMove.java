package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Match;

public class EnableLeaderCardPlayerMove extends PlayerMove {
    private int leaderCardPosition;

    public EnableLeaderCardPlayerMove(int leaderCardPosition) {
        this.leaderCardPosition = leaderCardPosition;
    }

    public static EnableLeaderCardPlayerMove getInstance(int leaderCardPosition) {
        return new EnableLeaderCardPlayerMove(leaderCardPosition);
    }

    @Override
    public void execute(Match match) {
        match.enableLeaderCardInteraction(leaderCardPosition, getPlayer());
    }

}
