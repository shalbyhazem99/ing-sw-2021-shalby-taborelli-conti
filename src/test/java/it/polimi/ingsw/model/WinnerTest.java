package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;


public class WinnerTest extends TestCase {

    @Test
    public void testGetName() {
        Winner winner = new Winner("Winner", 3, 4);
        assertEquals("Winner", winner.getName());
    }

    @Test
    public void getPoints() {
        Winner winner = new Winner("Winner", 3, 4);
        assertEquals(3, winner.getPoints());
    }

    @Test
    public void getTotalResources() {
        Winner winner = new Winner("Winner", 3, 4);
        assertEquals(4, winner.getTotalResources());
    }
}