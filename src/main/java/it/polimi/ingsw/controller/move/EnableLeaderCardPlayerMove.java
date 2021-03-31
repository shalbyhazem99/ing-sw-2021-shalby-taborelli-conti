package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class EnableLeaderCardPlayerMove extends PlayerMove {
    private LeaderCard leaderCard;
    @Override
    public void execute(Match match) {
        try {
            match.enableLeaderCard(leaderCard);
        }catch (NotEnoughResources e){
            e.printStackTrace();
        }
    }
    public EnableLeaderCardPlayerMove(String name_of_user)
    {
        super(name_of_user);
    }
}
