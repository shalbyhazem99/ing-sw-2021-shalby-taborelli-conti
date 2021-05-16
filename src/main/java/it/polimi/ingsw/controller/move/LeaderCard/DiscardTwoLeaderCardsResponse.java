package it.polimi.ingsw.controller.move.LeaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketMarbleConversionMove;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesPlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.Scanner;

public class DiscardTwoLeaderCardsResponse extends MoveResponse {
    /**
     * Default constructor
     */
    public DiscardTwoLeaderCardsResponse(ArrayList<Player> players) {
        super(players);
    }

    public static DiscardTwoLeaderCardsResponse getInstance(ArrayList<Player> players) {
        return new DiscardTwoLeaderCardsResponse(players);
    }


    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        //todo: do some controls on user inputs
        System.out.println("You must discard two leader card!");
        System.out.println("Insert the position of the first one");
        int first = stdin.nextInt();
        System.out.println("Insert the position of the second one");
        int second = stdin.nextInt();
        return DiscardTwoLeaderCardsPlayerMove.getInstance(first, second);
    }
}
