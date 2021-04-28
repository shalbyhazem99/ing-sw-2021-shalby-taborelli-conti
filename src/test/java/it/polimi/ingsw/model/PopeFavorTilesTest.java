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
        assertFalse(popeFavorTiles.getActive());
    }
}