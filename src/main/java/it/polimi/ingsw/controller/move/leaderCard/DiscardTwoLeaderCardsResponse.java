package it.polimi.ingsw.controller.move.leaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DiscardTwoLeaderCardsResponse extends MoveResponse {
    /**
     * Default constructor
     */
    public DiscardTwoLeaderCardsResponse(ArrayList<Player> players,int executePlayerPos,int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
    }

    public static DiscardTwoLeaderCardsResponse getInstance(ArrayList<Player> players,int executePlayerPos,int hashToVerify) {
        return new DiscardTwoLeaderCardsResponse(players,executePlayerPos,hashToVerify);
    }


    public static DiscardTwoLeaderCardsResponse getInstance(Player player,int executePlayerPos,int hashToVerify) {
        return new DiscardTwoLeaderCardsResponse(new ArrayList<>(Arrays.asList(player)),executePlayerPos,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        //todo: do some controls on user inputs
        System.out.println("You must discard two leader card!");
        boolean param_correct = false;
        int first,second;
        do {
            System.out.println("Insert the position of the first one");
            first = stdin.nextInt();
            if(first>=0&&first<4)
            {
                param_correct = true;
            }
        }while (!param_correct);
        param_correct = false;
        do {
            System.out.println("Insert the position of the second one");
            second = stdin.nextInt();
            if(second>=0&&second<4&&second!=first)
            {
                param_correct = true;
            }
        }while (!param_correct);
        return DiscardTwoLeaderCardsPlayerMove.getInstance(first, second);
    }
}
