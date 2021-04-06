package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class DiscardLeaderCardPlayerMove extends PlayerMove {
    private LeaderCard leaderCard;

    /**
     *classic setter
     * @param leaderCard
     */
    public DiscardLeaderCardPlayerMove(LeaderCard leaderCard){
        this.leaderCard = leaderCard;
    }

    @Override
    public void execute(Match match) {
        player.discardLeaderCard(leaderCard);
    }
}
