package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class EnableLeaderCardPlayerMove implements PlayerMove {
    private LeaderCard leaderCard;
    private Player player;

    public EnableLeaderCardPlayerMove(LeaderCard leaderCard, Player player){
        this.leaderCard = leaderCard;
        this.player = player;
    }


    @Override
    public void exectute(Match match) {
        try {
            player.e;
        }catch (NotEnoughResources e){
            e.printStackTrace();
        }
    }
}
