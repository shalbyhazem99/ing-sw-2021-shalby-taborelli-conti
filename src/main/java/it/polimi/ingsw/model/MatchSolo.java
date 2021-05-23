package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.endMatch.EndMatchSoloResponse;
import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.endRound.EndRoundSoloResponse;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.exceptions.EndRoundException;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;


public class MatchSolo extends Match implements Serializable {
    /**
     * Class concerning a Match of one player vs CPU "Lorenzo il Magnifico"
     */

    /*
    Ad ogni turno l'utente fa un pickActionToken(), poi sulla base del tipo dell'action token controller chiama
    - moveAheadBlackCross(2)
    - moveAheadBlackCross(1) e shuffle()
    - discardDevelopmentCards(numero,tipo)
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
        if (posBlackCross > 20) {
            posBlackCross = 20;
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
        Collections.shuffle(actionTokens);
    }

    /**
     * This method will discard from the {@link DevelopmentCard} matrix the requested amount of cards of a certain {@link DevelopmentCardType}
     * the method will try to discard LVL 1 cards, otherwise LVL 2 cards otherwise LVL 3 cards
     *
     * @param numberOfCardsToDiscard how many card we have to discard
     * @param cardType               which {@link DevelopmentCardType} we have to focus on
     * @return the {@link ArrayList} of {@link DevelopmentCard} that got discarded (it can be empty if no compatible cards are found
     */
    public ArrayList<DevelopmentCard> discardDevelopmentCards(int numberOfCardsToDiscard, DevelopmentCardType cardType) {
        /*
            COLUMNS:        GREEN / BLUE / YELLOW / PURPLE
            ROWS: LVL 3->
                  LVL 2->
                  LVL 1->
         */
        ArrayList<DevelopmentCard> temp = new ArrayList<>();
        DevelopmentCardLevel cardLevel = DevelopmentCardLevel.FIRST;
        do {
            if (developmentCards[cardLevel.label][cardType.label].size() != 0) //if the stack is empty try the next level otherwise discard
            {
                temp.add(developmentCards[cardLevel.label][cardType.label].pop());
                numberOfCardsToDiscard--;
            } else //if the stack we're operating on is empty we try to move a stack of an higher level
            {
                switch (cardLevel.label) {
                    case 0: {
                        cardLevel = null;
                        break;
                    } //it means we are int highest row on the third level, we cannot discard anything else
                    case 1: {
                        cardLevel = DevelopmentCardLevel.THIRD;
                        break;
                    } //LVL 2 --> LVL 3
                    case 2: {
                        cardLevel = DevelopmentCardLevel.SECOND;
                        break;
                    } //LVL 1 --> LVL 2
                }
            }
        } while (numberOfCardsToDiscard != 0 && cardLevel != null); //stop if the correct amount of cards is discarded or if there are no more cards of that color left
        return temp;
    }

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
     * @throws EndRoundException {@link EndRoundException} thrown when an error occurs
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
                }else {
                    notify(EndRoundSoloResponse.getInstance(getPlayers(), getPlayers().indexOf(player), true, this.hashCode(), messageToSend, actionToken));
                    askForMove();
                }
            }
        }
    }

    public String executeAction(ActionToken action, Player player, boolean noControl) {
        setCanChangeTurn(false, player);
        pendingMarketResources = new ArrayList<>();
        String u = "";
        switch (action.getAction()) {
            case MOVE:
                u = "Lorenzo move ahead of " + action.getCount() + " passes\n";
                if (action.getCount() == 1) {
                    moveAheadBlackCross(1);
                    if (!noControl)
                        shuffleActionTokens();
                } else //count=2
                {
                    moveAheadBlackCross(2);
                }
                //control tailes
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

    @Override
    public Player getCurrentPlayer() {
        return players.get(0);
    }

    @Override
    public void startMatch() {
        super.startMatch();
        //askForMove();
    }

    @Override
    public void discardTwoLeaderCardInteraction(int posFirst, int posSecond, Player player, ResourceType resourceTypeFirst, ResourceType resourceTypeSecond) {
        super.discardTwoLeaderCardInteraction(posFirst, posSecond, player,resourceTypeFirst,resourceTypeSecond);
        notifyModel();
        if (numPlayerWhoDiscard == players.size()) {
            //notifyModel();
            askForMove();
        }
    }

    @Override
    public void notifyModel() {
        for (int i = 0; i < players.size(); i++) {
            notify(SendModel.getInstance(this, players.get(i), i, this.hashCode()));
        }
    }

    public boolean hasLose() {
        //control the development card (if the development card of a certain typology has end)
        if (Arrays.stream(DevelopmentCardType.values()).anyMatch(developmentCardType ->
                Arrays.stream(developmentCards).mapToInt(row -> row[developmentCardType.label].size()).sum() == 0
        )) return true;
        //if lorenzo finish the faith path before you
        if (posBlackCross >= 24) return true;
        return false;
    }

    public boolean hasWon() {
        return (players.get(0).getDevelopmentCards().size() >= 7 || players.get(0).getPosFaithMarker() >= 24);
    }

    public String toString() {
        return super.toString() +
                ("LORENZO IL MAGNIFICO ==> Faith pos: " + posBlackCross + "\n") +
                ("--------------------------------------------------------------------------------------------------------");
    }

}
