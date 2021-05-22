package it.polimi.ingsw.controller.move.leaderCard;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ResourceType;

public class DiscardTwoLeaderCardsPlayerMove extends PlayerMove {
    private int posFirst;
    private int posSecond;
    private ResourceType resourceTypeFirst;
    private ResourceType resourceTypeSecond;

    public DiscardTwoLeaderCardsPlayerMove(int posFirst, int posSecond, ResourceType resourceTypeFirst, ResourceType resourceTypeSecond) {
        this.posFirst = posFirst;
        this.posSecond = posSecond;
        this.resourceTypeFirst = resourceTypeFirst;
        this.resourceTypeSecond = resourceTypeSecond;
    }

    public static DiscardTwoLeaderCardsPlayerMove getInstance(int posFirst, int posSecond, ResourceType resourceTypeFirst, ResourceType resourceTypeSecond) {
        return new DiscardTwoLeaderCardsPlayerMove(posFirst, posSecond,resourceTypeFirst,resourceTypeSecond);
    }

    @Override
    public void execute(Match match) {
        match.discardTwoLeaderCardInteraction(posFirst,posSecond,getPlayer(),resourceTypeFirst,resourceTypeSecond);
    }

}
