package it.polimi.ingsw.controller.move.leaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DiscardTwoLeaderCardsResponse extends MoveResponse {
    /**
     * Default constructor
     */
    public DiscardTwoLeaderCardsResponse(ArrayList<Player> players,int executePlayerPos) {
        super(players,executePlayerPos);
    }

    public static DiscardTwoLeaderCardsResponse getInstance(ArrayList<Player> players,int executePlayerPos) {
        return new DiscardTwoLeaderCardsResponse(players,executePlayerPos);
    }


    public static DiscardTwoLeaderCardsResponse getInstance(Player player,int executePlayerPos) {
        return new DiscardTwoLeaderCardsResponse(new ArrayList<>(Arrays.asList(player)),executePlayerPos);
    }

    @Override
    public void updateLocalMatch(Match match) {

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
