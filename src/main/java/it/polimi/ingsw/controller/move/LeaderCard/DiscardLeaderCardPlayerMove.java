package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class DiscardLeaderCardPlayerMove implements PlayerMove {
    private LeaderCard leaderCard;
    private Player player;

    /**
     *classic setter
     * @param player
     * @param leaderCard
     */
    public DiscardLeaderCardPlayerMove(Player player, LeaderCard leaderCard){
        this.player = player;
        this.leaderCard = leaderCard;
    }

    @Override
    public void exectute(Player player) {
       player.discardLeaderCard(leaderCard);
    }
}
