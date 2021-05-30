package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import junit.framework.TestCase;

import org.junit.Test;
import java.util.LinkedList;


public class MatchSoloTest extends TestCase {

    /**
     * Testing the constructor of {@link MatchSolo}
     */
    @Test
    public void testMatchSolo(){
        MatchSolo matchSolo = new MatchSolo();

        assertEquals(1, matchSolo.getPlayers().size());
        assertEquals(0, matchSolo.getPosBlackCross());
    }

    @Test
    public void testGenerateActionTokens(){
        MatchSolo matchSolo = new MatchSolo();

        LinkedList<ActionToken> actionTokenLinkedList = matchSolo.generateActionTokens();
        assertEquals(6, actionTokenLinkedList.size());
        //assertEquals(1,actionTokenLinkedList.stream().filter(elem -> elem.equals(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.GREEN))).count());
        //ripetere per ogni carta
    }


    //todo: finire di guardare
    @Test
    public void testPickActionToken(){
        MatchSolo matchSolo = new MatchSolo();
        ActionToken actionToken;
        LinkedList<ActionToken> actionTokenLinkedList = matchSolo.getActionTokens();

        actionToken = matchSolo.pickActionToken();

        for (int i = 0; i < 6; i++) {
            if(i!=5){
                assertTrue(matchSolo.getActionTokens().get(i).equals(actionTokenLinkedList.get(i+1)));
            } else {
                assertEquals(actionTokenLinkedList.get(0), matchSolo.getActionTokens().get(i));
            }
        }





    }
    /**
     * Testing the Movement of the Black Cross
     */
    @Test
    public void testMoveAheadBlackCross(){
        MatchSolo matchSolo = new MatchSolo();
        assertEquals(0, matchSolo.getPosBlackCross());

        //Testing the movement between the tales 1 and 24
        matchSolo.moveAheadBlackCross(2);
        assertEquals(2, matchSolo.getPosBlackCross());

        //Testing when the Black Cross goes further than the 24
        matchSolo.moveAheadBlackCross(23);
        assertEquals(24, matchSolo.getPosBlackCross());
    }

    //todo: controllare che sia veramente utilizzata
    @Test
    public void testDiscardDevelopmentCards(){

    }

    /**
     * Testing the method returns always true, because there is only one {@link Player} in play
     */
    @Test
    public void testIsMyTurn(){
        MatchSolo matchSolo = new MatchSolo();
        assertTrue(matchSolo.isMyTurn(matchSolo.getCurrentPlayer()));
    }

    //todo: inventarsi qualcosa per testare
    @Test
    public void testEndInteraction(){

    }

    /**
     * Testing the possible moves of Lorenzo il Magnifico
     */
    @Test
    public void testExecuteAction(){
        MatchSolo matchSolo = new MatchSolo();
        matchSolo.addPlayer(Player.getInstance("PlayerSolo"));

        //CASE: DISCARD

        //Discarding all the DevelopmentCard using only the moves of "Lorenzo il Magnifico"
        ActionToken actionTokenDiscardPurple = new  ActionToken(MarkerType.DISCARD, 2, DevelopmentCardType.PURPLE);
        for (int i = 0; i < 3; i++) {
            String result = matchSolo.executeAction(actionTokenDiscardPurple, matchSolo.getCurrentPlayer(), true);
            assertEquals("Lorenzo discarded " + actionTokenDiscardPurple.getCount() + " "+ actionTokenDiscardPurple.getCardToReject().toString() + " cards\n", result);
            assertEquals(2, matchSolo.getDevelopmentCards()[i][actionTokenDiscardPurple.getCardToReject().label].size());

            result = matchSolo.executeAction(actionTokenDiscardPurple, matchSolo.getCurrentPlayer(), true);
            assertEquals("Lorenzo discarded " + actionTokenDiscardPurple.getCount() + " "+ actionTokenDiscardPurple.getCardToReject().toString() + " cards\n", result);
            assertEquals(0, matchSolo.getDevelopmentCards()[i][actionTokenDiscardPurple.getCardToReject().label].size());
        }

        //Testing the correct process when the DevelopmentCards ard discarded between two levels
        ActionToken actionTokenDiscardYellow = new  ActionToken(MarkerType.DISCARD, 2, DevelopmentCardType.YELLOW);
        matchSolo.getDevelopmentCards()[0][actionTokenDiscardYellow.getCardToReject().label].removeElementAt(0);
        String result = matchSolo.executeAction(actionTokenDiscardYellow, matchSolo.getCurrentPlayer(), true);
        assertEquals("Lorenzo discarded " + actionTokenDiscardYellow.getCount() + " "+ actionTokenDiscardYellow.getCardToReject().toString() + " cards\n", result);
        assertEquals(1, matchSolo.getDevelopmentCards()[0][actionTokenDiscardYellow.getCardToReject().label].size());

        result =matchSolo.executeAction(actionTokenDiscardYellow, matchSolo.getCurrentPlayer(), true);
        assertEquals("Lorenzo discarded " + actionTokenDiscardYellow.getCount() + " "+ actionTokenDiscardYellow.getCardToReject().toString() + " cards\n", result);
        assertEquals(0, matchSolo.getDevelopmentCards()[0][actionTokenDiscardYellow.getCardToReject().label].size());
        assertEquals(3, matchSolo.getDevelopmentCards()[1][actionTokenDiscardYellow.getCardToReject().label].size());

        //CASE: MOVE

        //todo:finish
    }
}