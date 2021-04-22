package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;

public class DiscardLeaderCardPlayerMove extends PlayerMove {
    private int leaderCardPosition;

    public DiscardLeaderCardPlayerMove(int leaderCardPosition) {
        this.leaderCardPosition = leaderCardPosition;
    }

    public static DiscardLeaderCardPlayerMove getInstance(int leaderCardPosition) {
        return new DiscardLeaderCardPlayerMove(leaderCardPosition);
    }

    @Override
    public void execute(Match match) {
        match.discardLeaderCardInteraction(leaderCardPosition,getPlayer());
    }

}
