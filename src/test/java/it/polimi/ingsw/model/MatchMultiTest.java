package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;


public class MatchMultiTest extends TestCase {

    /**
     * Testing that a match multi is created, the correct number of {@link Player} is created and the position of the Inkwell is set to -1
     */
    @Test
    public void testMatchMulti() {
        //Testing the correct creation of a MatchMulti with two players
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);

            assertEquals(-1, matchMulti.getPosInkwell());
            assertEquals(i, matchMulti.getPlayers().size());
        }
    }

    //todo: how can I test it?
    @Test
    public void testEndRoundInteraction() {

    }

    /**
     * Testing for any number of {@link Player} in the {@link MatchMulti} the position of the Inkwell
     */
    @Test
    public void testRandomlyPickInkwellPlayer() {
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            assertEquals(-1, matchMulti.getPosInkwell());

            int position = matchMulti.randomlyPickInkwellPlayer();

            assertEquals(position, matchMulti.getPosInkwell());
            assertTrue(matchMulti.getPosInkwell() < i);
        }
    }

    /**
     * Testing the {@link MatchMulti} starts and  each {@link Player} gets 4 {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
     */
    @Test
    public void testStartMatch() {
        for (int i = 2; i <5 ; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            int position = matchMulti.randomlyPickInkwellPlayer();
            assertTrue(position != -1);

            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("g"+i));
            }
            matchMulti.startMatch();

            for (Player player : matchMulti.getPlayers()){
                assertEquals(4,player.getLeaderCards().size());
            }
        }
    }


    @Test
    public void testAskForDiscardLeaderCard(){
        for (int i = 2; i <5 ; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("g"+j));
            }
            matchMulti.startMatch();

            matchMulti.askForDiscardLeaderCard();
        }
    }

    @Test
    public void testDiscardTwoLeaderCardInteraction(){
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+j));
            }
            matchMulti.startMatch();


        }

    }


}