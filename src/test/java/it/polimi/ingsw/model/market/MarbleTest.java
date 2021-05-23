package it.polimi.ingsw.model.market;

import junit.framework.TestCase;

public class MarbleTest extends TestCase {

    public void testEquals() {
        Marble marbleA = Marble.getInstance(MarbleColor.BLUE);
        Marble marbleB = Marble.getInstance(MarbleColor.BLUE);
        Marble marbleC = Marble.getInstance(MarbleColor.YELLOW);
        assertTrue(marbleA.equals(marbleB));
        assertFalse(marbleA.equals(null));
        assertFalse(marbleB.equals(marbleC));
    }
}