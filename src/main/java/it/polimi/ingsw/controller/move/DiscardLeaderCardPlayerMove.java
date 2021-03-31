package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class DiscardLeaderCardPlayerMove extends PlayerMove {
    private LeaderCard leaderCard;
    @Override
    public void execute(Match match) {
        match.discardLeaderCard(leaderCard); //todo: the leader card is not the same instance so equal must be written
    }
    public DiscardLeaderCardPlayerMove(String name_of_user)
    {
        super(name_of_user);
    }
}
