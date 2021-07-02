package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.endMatch.EndMatchSoloResponse;
import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.endRound.EndRoundSoloResponse;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.exceptions.EndRoundException;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.*;

/**
 * Class that represent a Solo Match
 */
public class MatchSolo extends Match implements Serializable {
    /**
     * Class concerning a Match of one player vs CPU "Lorenzo il Magnifico"
     */

    /*
    Every turn the Player makes a pickActionToken(), based on the type of the Action token picked the controller invokes:
    - moveAheadBlackCross(2)
    - moveAheadBlackCross(1) e shuffle()
    - discardDevelopmentCards(number,type)
     */
    private transient LinkedList<ActionToken> actionTokens;
    private int posBlackCross;

    /**
     * Class' constructor, it calls the super method and in addition in initialize the {@link ActionToken} {@link java.util.ArrayList} and the posBlackCross
     */
    public MatchSolo() {
        super(1);
        posBlackCross = 0;
        actionTokens = generateActionTokens();
    }

    /**
     * The method generate the {@link ArrayDeque} of {@link ActionToken} required for a solo match
     *
     * @return {@link ArrayDeque} of {@link ActionToken} required for a solo match
     */
    public static LinkedList<ActionToken> generateActionTokens() {
        LinkedList<ActionToken> temp = new LinkedList<>();
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.GREEN));
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.BLUE));
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.YELLOW));
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.PURPLE));
        temp.add(ActionToken.getInstance(MarkerType.MOVE, 2, null));
        temp.add(ActionToken.getInstance(MarkerType.MOVE, 1, null));
        Collections.shuffle(temp);
        return temp;
    }

    /**
     * The method pick the {@link ActionToken} (which will be re-inserted in the tail of the queue)
     *
     * @return the {@link ActionToken} polled
     */
    public ActionToken pickActionToken() {
        ActionToken temp = actionTokens.poll();
        if (temp == null) //an error occured
        {
            actionTokens = generateActionTokens(); //rebuild the queue
            return null;
        }
        actionTokens.add(temp);
        return temp;
    }

    /**
     * The method move the black cross ahead in the Faith's path
     *
     * @param numberOfPasses how many cells the black cross needs to go ahead, numberOfPasses = {1,2}
     * @return the position of the blackCross, position € {0,1,2,...,20}
     */
    public int moveAheadBlackCross(int numberOfPasses) {
        posBlackCross = posBlackCross + numberOfPasses;
        if (posBlackCross > Utils.FAITH_LENGTH) {
            posBlackCross = Utils.FAITH_LENGTH;
        }
        return posBlackCross;
    }

    /**
     * Method used to retrieve the black cross' position
     *
     * @return the position of the black cross
     */
    public int getPosBlackCross() {
        return posBlackCross;
    }

    /**
     * The method shuffles the action tokens list
     */
    public void shuffleActionTokens() {
        try {
            Collections.shuffle(actionTokens);
        } catch (Exception e) {
        }
    }

    /**
     * @param player {@link Player} to check if is his turn
     * @return always true, because the turn of Lorenzo il Magnifico is processed immediately
     */
    @Override
    public boolean isMyTurn(Player player) {
        return true;
    }

    @Override
    public void setCanChangeTurn(boolean canChangeTurn, Player player) {
        this.canChangeTurn = canChangeTurn;
    }

    /**
     * Method used by the {@link Player} to end a {@link MatchSolo} round
     *
     * @param player {@link Player} who wants to end the round
     */
    public void endRoundInteraction(Player player, boolean noControl) {
        if (!noControl && (!isMyTurn(player) || pendingMarketResources.size() != 0 || !getCanChangeTurn())) {
            notify(EndRoundResponse.getInstance(new ArrayList<>(Arrays.asList(player)), getPlayers().indexOf(player), false, this.hashCode()));
            askForMove();
            return;
        }
        super.endRoundInteraction(player, noControl);
        //execute lorenzo action
        if (!noControl) {
            if (hasWon()) {
                notify(EndMatchSoloResponse.getInstance(players, 0, this.hashCode(), false));
            } else {
                ActionToken actionToken = pickActionToken();
                String messageToSend = executeAction(actionToken, player, false);
                if (hasLose()) {
                    notify(EndMatchSoloResponse.getInstance(players, 0, this.hashCode(), true));
                } else {
                    notify(EndRoundSoloResponse.getInstance(getPlayers(), getPlayers().indexOf(player), true, this.hashCode(), messageToSend, actionToken));
                    askForMove();
                }
            }
        }
    }

    @Override
    public void updateTurn() {

    }

    /**
     * Method that execute the move of Lorenzo il Magnifico
     *
     * @param action    {@link ActionToken} to execute
     * @param player    {@link Player} is playing
     * @param noControl boolean parameter if the method needs to check everything
     * @return the description of what Lorenzo il Magnifico has done
     */
    public String executeAction(ActionToken action, Player player, boolean noControl) {
        setCanChangeTurn(false, player);
        pendingMarketResources = new ArrayList<>();
        String u = "";
        switch (action.getAction()) {
            case MOVE:
                u = "Lorenzo move ahead of " + action.getCount() + " passes\n";
                if (action.getCount() == 1) {
                    moveAheadBlackCross(1);
                    shuffleActionTokens();
                } else //count=2
                {
                    moveAheadBlackCross(2);
                }
                //control tales
                if (posBlackCross == 24) {
                    if (getCurrentPlayer().getPosFaithMarker() >= 19) {
                        if (getCurrentPlayer().getPopeFavorTiles().get(2) != null) {
                            getCurrentPlayer().getPopeFavorTiles().get(2).active();
                        }
                    } else {
                        getCurrentPlayer().getPopeFavorTiles().set(2, null);
                    }
                } else if (posBlackCross >= 16) {
                    if (getCurrentPlayer().getPosFaithMarker() >= 12) {
                        if (getCurrentPlayer().getPopeFavorTiles().get(1) != null) {
                            getCurrentPlayer().getPopeFavorTiles().get(1).active();
                        }
                    } else {
                        getCurrentPlayer().getPopeFavorTiles().set(1, null);
                    }
                } else if (posBlackCross >= 8) {
                    if (getCurrentPlayer().getPosFaithMarker() >= 5) {
                        if (getCurrentPlayer().getPopeFavorTiles().get(0) != null) {
                            getCurrentPlayer().getPopeFavorTiles().get(0).active();
                        }
                    } else {
                        getCurrentPlayer().getPopeFavorTiles().set(0, null);
                    }
                }
                break;
            case DISCARD:
                int lvl = 0;
                int to_discard = action.getCount();
                u = "Lorenzo discarded " + to_discard + " " + action.getCardToReject().toString() + " cards\n";
                while (to_discard != 0 && lvl != 3) //I have to discard x cards
                {
                    if (!developmentCards[lvl][action.getCardToReject().label].isEmpty()) //If the stack is not empty
                    {
                        developmentCards[lvl][action.getCardToReject().label].pop(); //discard the card
                        to_discard--;
                    } else //if the stack is empty change the level
                    {
                        lvl++;
                    }
                }
                break;
        }
        return u;
    }

    /**
     * @return always the {@link Player} is playing
     */
    @Override
    public Player getCurrentPlayer() {
        return players.get(0);
    }

    /**
     * Method to start the match and deals the {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
     */
    @Override
    public void startMatch() {
        super.startMatch();
        moveAheadBlackCross(23);
        //askForMove();
    }

    /**
     * Method to discard 2 {@link LeaderCard} when a {@link Match} starts
     * @param posFirst index of the first {@link LeaderCard} to discard
     * @param posSecond index of the second {@link LeaderCard} to discard
     * @param player that is discarding the 2 cards
     * @param resourceTypeFirst {@link ResourceType} that a {@link Player} can choose, based on the order of the turn
     * @param resourceTypeSecond {@link ResourceType} that a {@link Player} can choose, based on the order of the turn
     */
    @Override
    public void discardTwoLeaderCardInteraction(int posFirst, int posSecond, Player player, ResourceType resourceTypeFirst, ResourceType resourceTypeSecond) {
        super.discardTwoLeaderCardInteraction(posFirst, posSecond, player, resourceTypeFirst, resourceTypeSecond);
        notifyModel();
        if (numPlayerWhoDiscard == players.size()) {
            //notifyModel();
            askForMove();
        }
    }

    /**
     *
     * @return the ArrayList of {@link ActionToken} in the order of stack
     */
    public LinkedList<ActionToken> getActionTokens() {
        return actionTokens;
    }

    @Override
    public void notifyModel() {
        for (int i = 0; i < players.size(); i++) {
            notify(SendModel.getInstance(this, players.get(i), i, this.hashCode()));
        }
    }

    /**
     *
     * @return true if the {@link Player} has lost, true otherwise
     */
    public boolean hasLose() {
        //control the development card (if the development card of a certain typology has end)
        if (Arrays.stream(DevelopmentCardType.values()).anyMatch(developmentCardType ->
                Arrays.stream(developmentCards).mapToInt(row -> row[developmentCardType.label].size()).sum() == 0
        )) return true;
        //if lorenzo finish the faith path before you
        if (posBlackCross >= 24) return true;
        return false;
    }

    /**
     *
     * @return if the {@link Player} has bought 7 {@link DevelopmentCard} or has reached the FaithMarker number 24
     */
    public boolean hasWon() {
        return (players.get(0).getDevelopmentCards().size() >= 7 || players.get(0).getPosFaithMarker() >= 24);
    }

    public String toString() {
        return super.toString() +
                ("LORENZO IL MAGNIFICO ==> Faith pos: " + posBlackCross + "\n") +
                ("--------------------------------------------------------------------------------------------------------");
    }

}
