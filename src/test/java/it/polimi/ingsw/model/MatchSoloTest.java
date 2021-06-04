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
    public void testMatchSolo() {
        MatchSolo matchSolo = new MatchSolo();

        assertEquals(1, matchSolo.getPlayers().size());
        assertEquals(0, matchSolo.getPosBlackCross());
    }

    @Test
    public void testGenerateActionTokens() {
        MatchSolo matchSolo = new MatchSolo();

        LinkedList<ActionToken> actionTokenLinkedList = matchSolo.generateActionTokens();

        assertEquals(6, actionTokenLinkedList.size());
        //Checking there are 4 DISCARD type and that are of different type
        assertEquals(4, actionTokenLinkedList.stream().filter(actionToken -> actionToken.getAction() == MarkerType.DISCARD).count());
        assertEquals(1, actionTokenLinkedList.stream().filter(actionToken -> actionToken.getCardToReject() == DevelopmentCardType.BLUE).count());
        assertEquals(1, actionTokenLinkedList.stream().filter(actionToken -> actionToken.getCardToReject() == DevelopmentCardType.YELLOW).count());
        assertEquals(1, actionTokenLinkedList.stream().filter(actionToken -> actionToken.getCardToReject() == DevelopmentCardType.GREEN).count());
        assertEquals(1, actionTokenLinkedList.stream().filter(actionToken -> actionToken.getCardToReject() == DevelopmentCardType.PURPLE).count());
        //Checking there are 2 MOVE and that are of different type
        assertEquals(2, actionTokenLinkedList.stream().filter(actionToken -> actionToken.getAction() == MarkerType.MOVE).count());
        assertEquals(1, actionTokenLinkedList.stream().filter(actionToken -> (actionToken.getAction() == MarkerType.MOVE && actionToken.getCount() == 2)).count());
        assertEquals(1, actionTokenLinkedList.stream().filter(actionToken -> (actionToken.getAction() == MarkerType.MOVE && actionToken.getCount() == 1)).count());
    }


    @Test
    public void testPickActionToken() {
        MatchSolo matchSolo = new MatchSolo();
        LinkedList<ActionToken> actionTokenLinkedList = new LinkedList<>();
        for (int i = 0; i < matchSolo.getActionTokens().size(); i++) {
            actionTokenLinkedList.add(matchSolo.getActionTokens().get(i));
        }

        ActionToken actionToken = matchSolo.pickActionToken();
        //Checking the ActionToken picked is now in the last position
        assertTrue(matchSolo.getActionTokens().indexOf(actionToken) == 5);
        //Checking the order of the linked list
        for (int i = 0; i < 5; i++) {
            assertEquals(actionTokenLinkedList.get(i + 1).toString(), matchSolo.getActionTokens().get(i).toString());
        }
    }

    /**
     * Testing the Movement of the Black Cross
     */
    @Test
    public void testMoveAheadBlackCross() {
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
    public void testDiscardDevelopmentCards() {

    }

    /**
     * Testing the method returns always true, because there is only one {@link Player} in play
     */
    @Test
    public void testIsMyTurn() {
        MatchSolo matchSolo = new MatchSolo();
        assertTrue(matchSolo.isMyTurn(matchSolo.getCurrentPlayer()));
    }

    //todo: inventarsi qualcosa per testare
    @Test
    public void testEndInteraction() {

    }

    /**
     * Testing the possible moves of Lorenzo il Magnifico
     */
    @Test
    public void testExecuteAction() {
        MatchSolo matchSolo = new MatchSolo();
        matchSolo.addPlayer(Player.getInstance("PlayerSolo"));

        //CASE: DISCARD

        //Discarding all the DevelopmentCard using only the moves of "Lorenzo il Magnifico"
        ActionToken actionTokenDiscardPurple = new ActionToken(MarkerType.DISCARD, 2, DevelopmentCardType.PURPLE);
        for (int i = 0; i < 3; i++) {
            String result = matchSolo.executeAction(actionTokenDiscardPurple, matchSolo.getCurrentPlayer(), true);
            assertEquals("Lorenzo discarded " + actionTokenDiscardPurple.getCount() + " " + actionTokenDiscardPurple.getCardToReject().toString() + " cards\n", result);
            assertEquals(2, matchSolo.getDevelopmentCards()[i][actionTokenDiscardPurple.getCardToReject().label].size());

            result = matchSolo.executeAction(actionTokenDiscardPurple, matchSolo.getCurrentPlayer(), true);
            assertEquals("Lorenzo discarded " + actionTokenDiscardPurple.getCount() + " " + actionTokenDiscardPurple.getCardToReject().toString() + " cards\n", result);
            assertEquals(0, matchSolo.getDevelopmentCards()[i][actionTokenDiscardPurple.getCardToReject().label].size());
        }

        //Testing the correct process when the DevelopmentCards ard discarded between two levels
        ActionToken actionTokenDiscardYellow = new ActionToken(MarkerType.DISCARD, 2, DevelopmentCardType.YELLOW);
        matchSolo.getDevelopmentCards()[0][actionTokenDiscardYellow.getCardToReject().label].removeElementAt(0);
        String result = matchSolo.executeAction(actionTokenDiscardYellow, matchSolo.getCurrentPlayer(), true);
        assertEquals("Lorenzo discarded " + actionTokenDiscardYellow.getCount() + " " + actionTokenDiscardYellow.getCardToReject().toString() + " cards\n", result);
        assertEquals(1, matchSolo.getDevelopmentCards()[0][actionTokenDiscardYellow.getCardToReject().label].size());

        result = matchSolo.executeAction(actionTokenDiscardYellow, matchSolo.getCurrentPlayer(), true);
        assertEquals("Lorenzo discarded " + actionTokenDiscardYellow.getCount() + " " + actionTokenDiscardYellow.getCardToReject().toString() + " cards\n", result);
        assertEquals(0, matchSolo.getDevelopmentCards()[0][actionTokenDiscardYellow.getCardToReject().label].size());
        assertEquals(3, matchSolo.getDevelopmentCards()[1][actionTokenDiscardYellow.getCardToReject().label].size());

        //CASE: MOVE
        int hashCode;

        //Testing when the blackCross moves ahead and it is in a activation area
        assertEquals(0, matchSolo.getPosBlackCross());
        assertEquals("Lorenzo move ahead of " + 2 + " passes\n", matchSolo.executeAction(ActionToken.getInstance(MarkerType.MOVE, 2, null), matchSolo.getCurrentPlayer(), true));
        assertEquals(2, matchSolo.getPosBlackCross());
        hashCode = matchSolo.getActionTokens().hashCode();
        assertEquals("Lorenzo move ahead of " + 1 + " passes\n", matchSolo.executeAction(ActionToken.getInstance(MarkerType.MOVE, 1, null), matchSolo.getCurrentPlayer(), true));
        assertEquals(3, matchSolo.getPosBlackCross());
        //Checking that the order of the item is changed. Because if the order changes also the hash code changes too.
        assertTrue(hashCode != matchSolo.getActionTokens().hashCode());

        //testing when the blackCross activates the Pope's Tale of the Player

        //CASE: Deactivation
        matchSolo.moveAheadBlackCross(4);
        assertEquals("Lorenzo move ahead of " + 2 + " passes\n", matchSolo.executeAction(ActionToken.getInstance(MarkerType.MOVE, 2, null), matchSolo.getCurrentPlayer(), true));
        assertEquals(9, matchSolo.getPosBlackCross());
        assertEquals(null, matchSolo.getCurrentPlayer().getPopeFavorTiles().get(0));

        //CASE:ACTIVATION
        matchSolo.getCurrentPlayer().moveAheadFaith(13);
        matchSolo.moveAheadBlackCross(5);
        assertEquals("Lorenzo move ahead of " + 2 + " passes\n", matchSolo.executeAction(ActionToken.getInstance(MarkerType.MOVE, 2, null), matchSolo.getCurrentPlayer(), true));
        assertEquals(16, matchSolo.getPosBlackCross());
        assertEquals(3, matchSolo.getCurrentPlayer().getPopeFavorTiles().get(1).getPoints());
    }

    @Test
    public void testHasLose(){
        MatchSolo matchSolo = new MatchSolo();
        matchSolo.addPlayer(Player.getInstance("Player0"));
        assertFalse(matchSolo.hasLose());

        for (int i = 0; i < 3; i++) {
            assertFalse(matchSolo.hasLose());
            matchSolo.executeAction(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.PURPLE), matchSolo.getCurrentPlayer(), true);
            assertFalse(matchSolo.hasLose());
            matchSolo.executeAction(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.PURPLE), matchSolo.getCurrentPlayer(), true);
        }
        assertTrue(matchSolo.hasLose());
    }
}