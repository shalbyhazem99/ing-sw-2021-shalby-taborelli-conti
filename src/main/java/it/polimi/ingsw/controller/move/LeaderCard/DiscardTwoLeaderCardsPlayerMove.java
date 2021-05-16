package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;

public class DiscardTwoLeaderCardsPlayerMove extends PlayerMove {
    private int posFirst;
    private int posSecond;

    public DiscardTwoLeaderCardsPlayerMove(int posFirst, int posSecond) {
        this.posFirst = posFirst;
        this.posSecond = posSecond;
    }

    public static DiscardTwoLeaderCardsPlayerMove getInstance(int posFirst, int posSecond) {
        return new DiscardTwoLeaderCardsPlayerMove(posFirst, posSecond);
    }

    @Override
    public void execute(Match match) {
        match.discardTwoLeaderCardInteraction(posFirst,posSecond,getPlayer());
    }

}
