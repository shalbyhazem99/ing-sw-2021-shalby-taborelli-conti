package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class EnableLeaderCardPlayerMove extends PlayerMove {

    private LeaderCard leaderCard;
    private Player player;

    /**
     * default constructor
     * @param leaderCard
     * @param player
     */

    public EnableLeaderCardPlayerMove(LeaderCard leaderCard, Player player){
        this.leaderCard = leaderCard;
        this.player = player;
    }

    @Override
    public void execute(Match match) {
        leaderCard.active(player);
    }
}
