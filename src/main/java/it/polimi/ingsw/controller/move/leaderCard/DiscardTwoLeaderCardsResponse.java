package it.polimi.ingsw.controller.move.leaderCard;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DiscardTwoLeaderCardsResponse extends MoveResponse {
    /**
     * Default constructor
     */
    private int numOfResource;

    public DiscardTwoLeaderCardsResponse(ArrayList<Player> players, int executePlayerPos, int hashToVerify, int numOfResource) {
        super(players, executePlayerPos, hashToVerify);
        this.numOfResource = numOfResource;
    }

    public static DiscardTwoLeaderCardsResponse getInstance(ArrayList<Player> players, int executePlayerPos, int hashToVerify, int numOfResource) {
        return new DiscardTwoLeaderCardsResponse(players, executePlayerPos, hashToVerify, numOfResource);
    }


    public static DiscardTwoLeaderCardsResponse getInstance(Player player, int executePlayerPos, int hashToVerify, int numOfResource) {
        return new DiscardTwoLeaderCardsResponse(new ArrayList<>(Arrays.asList(player)), executePlayerPos, hashToVerify, numOfResource);
    }

    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.askToDiscardTwoLeader(numOfResource,getExecutePlayerPos());
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {

        System.out.println("You must discard two leader card!");
        boolean param_correct = false;
        int first, second;
        do {
            System.out.println("Insert the position of the first one");
            first = stdin.nextInt();
            if (first >= 0 && first < 4) {
                param_correct = true;
            }
        } while (!param_correct);
        param_correct = false;
        do {
            System.out.println("Insert the position of the second one");
            second = stdin.nextInt();
            if (second >= 0 && second < 4 && second != first) {
                param_correct = true;
            }
        } while (!param_correct);
        ///todo: do some controls on user inputs and maybe write it better
        ResourceType[] resourceTypes = Utils.getUsableResourcesType();
        int resFirst=0, resSecond=0;
        if (numOfResource > 0) {
            System.out.print("insert a resource type ( ");
            for (int i = 0; i < resourceTypes.length; i++) {
                System.out.print(i + "->" + Utils.resourceTypeToString(resourceTypes[i]) + " ");
            }
            System.out.println(" )");
            resFirst = stdin.nextInt();
        }
        if (numOfResource > 1) {
            System.out.print("insert a resource type ( ");
            for (int i = 0; i < resourceTypes.length; i++) {
                System.out.print(i + "->" + Utils.resourceTypeToString(resourceTypes[i]) + " ");
            }
            System.out.println(" )");
            resSecond = stdin.nextInt();
        }


        return DiscardTwoLeaderCardsPlayerMove.getInstance(first, second,resourceTypes[resFirst],resourceTypes[resSecond]);
    }
}
