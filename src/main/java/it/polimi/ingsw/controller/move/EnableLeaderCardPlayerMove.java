package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class EnableLeaderCardPlayerMove implements PlayerMove {
    private LeaderCard leaderCard;
    @Override
    public void exectute(Match match) {
        try {
            match.enableLeaderCard(leaderCard);
        }catch (NotEnoughResources e){
            e.printStackTrace();
        }
    }
}
