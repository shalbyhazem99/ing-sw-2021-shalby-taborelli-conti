package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


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
    private LinkedList<ActionToken> actionTokens;
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
        if (numberOfPasses != 0 || numberOfPasses != 1) {
            return posBlackCross;
        }
        posBlackCross += posBlackCross;
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
     * @param numberOfCardsToDiscard how many card we have to discard
     * @param cardType which {@link DevelopmentCardType} we have to focus on
     * @return the {@link ArrayList} of {@link DevelopmentCard} that got discarded (it can be empty if no compatible cards are found
     */
    public ArrayList<DevelopmentCard> discardDevelopmentCards(int numberOfCardsToDiscard, DevelopmentCardType cardType)
    {
        /*
            COLUMNS:        GREEN / BLUE / YELLOW / PURPLE
            ROWS: LVL 3->
                  LVL 2->
                  LVL 1->
         */
        ArrayList<DevelopmentCard> temp = new ArrayList<>();
        DevelopmentCardLevel cardLevel = DevelopmentCardLevel.FIRST;
        do
        {
            if(developmentCards[cardLevel.label][cardType.label].size()!=0) //if the stack is empty try the next level otherwise discard
            {
                temp.add(developmentCards[cardLevel.label][cardType.label].pop());
                numberOfCardsToDiscard--;
            }
            else //if the stack we're operating on is empty we try to move a stack of an higher level
            {
                switch(cardLevel.label)
                {
                    case 0 : { cardLevel = null; break;} //it means we are int highest row on the third level, we cannot discard anything else
                    case 1 : { cardLevel = DevelopmentCardLevel.THIRD; break;} //LVL 2 --> LVL 3
                    case 2 : { cardLevel = DevelopmentCardLevel.SECOND; break;} //LVL 1 --> LVL 2
                }
            }
        } while(numberOfCardsToDiscard!=0 && cardLevel!=null); //stop if the correct amount of cards is discarded or if there are no more cards of that color left
        return temp;
    }
}
