package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Class used to test the {@link PopeFavorTiles}
 */

public class PopeFavorTilesTest extends TestCase {

    public PopeFavorTilesTest() {
    }


    @Test
    public void testActive() {
        final int count = 1;
        PopeFavorTiles popeFavorTiles = new PopeFavorTiles(count);

        assertEquals(1,popeFavorTiles.getPoints());
        assertFalse(popeFavorTiles.isActive());
    }

    /**
     * Testing that when is not active it does not assign VictoryPoints
     */
    @Test
    public void testGetPoints(){
        Player tester = new Player("tester");
        for (int i = 0; i < 3; i++) {
            assertEquals(0, tester.getPopeFavorTiles().get(i).getPoints());
            tester.getPopeFavorTiles().get(i).active();
            assertEquals(2+i, tester.getPopeFavorTiles().get(i).getPoints());
        }
    }
}