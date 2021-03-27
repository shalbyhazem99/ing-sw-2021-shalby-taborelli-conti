package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

public class DiscardLeaderCardPlayerMove implements PlayerMove {
    private LeaderCard leaderCard;
    @Override
    public void exectute(Match match) {
        match.discardLeaderCard(leaderCard); //todo: the leader card is not the same instance so equal must be written
    }
}
