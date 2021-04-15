package it.polimi.ingsw.model;

import org.junit.Test;


import static junit.framework.TestCase.*;

class PopeFavorTilesTest {

    @Test
    void active() {
        final int count = 1;
        PopeFavorTiles popeFavorTiles = new PopeFavorTiles(count);

        assertEquals(1,popeFavorTiles.getPoints());
        assertFalse(popeFavorTiles.getActive());
    }
}