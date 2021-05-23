package it.polimi.ingsw.model.developmentCard;


import junit.framework.TestCase;
import org.junit.Test;


/**
 *  Class used to test the { @link DevelopmentCardSpace}
 */

public class DevelopmentCardSpaceTest extends TestCase {
    public DevelopmentCardSpaceTest() {
    }

    /**
     * Class used to test if canBeAdd works properly
     *
     * 1) Checks cards with level > 1 won't be added
     * 2) Checks cards with level 1 won't be added again
     * 3) Checks cards with level > 2 won't be added
     * 4) Checks cards with level 2 won't added again
     * 5) Checks cards with level = 1 won't be added
     * 6) Checks cards with level 3 won't be added again
     * 7) Checks cards with level < 3 won't be added
     */
    @Test
    public void testAdd() {
        DevelopmentCardLevel level1 = DevelopmentCardLevel.FIRST;
        DevelopmentCardLevel level2 = DevelopmentCardLevel.SECOND;
        DevelopmentCardLevel level3 = DevelopmentCardLevel.THIRD;


        DevelopmentCard card1_1 = new DevelopmentCard(level1, null, 0, null, null);
        DevelopmentCard card1_2 = new DevelopmentCard(level1, null, 0, null, null);
        DevelopmentCard card2_1 = new DevelopmentCard(level2, null, 0, null, null);
        DevelopmentCard card2_2 = new DevelopmentCard(level2, null, 0, null, null);
        DevelopmentCard card3_1 = new DevelopmentCard(level3, null, 0, null, null);
        DevelopmentCard card3_2 = new DevelopmentCard(level3, null, 0, null, null);

        DevelopmentCardSpace developmentCardSpace = new DevelopmentCardSpace();

        //Checking cards with level > 1 won't be added
        assertFalse( developmentCardSpace.add(card2_1));
        assertFalse( developmentCardSpace.add(card3_1));

        assertTrue( developmentCardSpace.add(card1_1) );

        //Checking cards with level 1 won't be added again
        assertFalse( developmentCardSpace.add(card1_2));

        //Checking cards with level > 2 won't be added
        assertFalse( developmentCardSpace.add(card3_1));

        assertTrue( developmentCardSpace.add(card2_1) );

        //Checking cards with level 2 won't be added again
        assertFalse( developmentCardSpace.add(card2_2));

        //Checking cards with level = 1 won't be added
        assertFalse( developmentCardSpace.add(card1_1));

        assertTrue(developmentCardSpace.add(card3_1));

        //Checking cards with level 3 won't be added again
        assertFalse( developmentCardSpace.add(card3_2));

        //Checking cards with level < 3 won't be added
        assertFalse( developmentCardSpace.add(card2_1));
        assertFalse( developmentCardSpace.add(card1_1));

    }

    /**
     * Class used to test if canBeAdd works properly
     *
     * 1) Checks cards with level > 1 can't be added
     * 2) Checks cards with level 1 can be added again
     * 3) Checks cards with level > 2 can't be added
     * 4) Checks cards with level 2 can be added again
     * 5) Checks cards with level = 1 can't be added
     * 6) Checks cards with level 3 can be added again
     * 7) Checks cards with level < 3 can't be added
     */
    @Test
    public void testCanBeAdded() {
        DevelopmentCardLevel level1 = DevelopmentCardLevel.FIRST;
        DevelopmentCardLevel level2 = DevelopmentCardLevel.SECOND;
        DevelopmentCardLevel level3 = DevelopmentCardLevel.THIRD;


        DevelopmentCard card1_1 = new DevelopmentCard(level1, null, 0, null, null);
        DevelopmentCard card1_2 = new DevelopmentCard(level1, null, 0, null, null);
        DevelopmentCard card2_1 = new DevelopmentCard(level2, null, 0, null, null);
        DevelopmentCard card2_2 = new DevelopmentCard(level2, null, 0, null, null);
        DevelopmentCard card3_1 = new DevelopmentCard(level3, null, 0, null, null);
        DevelopmentCard card3_2 = new DevelopmentCard(level3, null, 0, null, null);

        DevelopmentCardSpace developmentCardSpace = new DevelopmentCardSpace();

        //Checking cards with level > 1 can't be added
        assertFalse( developmentCardSpace.canBeAdded(card2_1));
        assertFalse( developmentCardSpace.canBeAdded(card3_1));

        assertTrue( developmentCardSpace.canBeAdded(card1_1) );
        developmentCardSpace.add(card1_1);

        //Checking cards with level 1 can't be added again
        assertFalse( developmentCardSpace.canBeAdded(card1_2));

        //Checking cards with level > 2 can't be added
        assertFalse( developmentCardSpace.canBeAdded(card3_1));

        assertTrue( developmentCardSpace.canBeAdded(card2_1) );

        developmentCardSpace.add(card2_1);

        //Checking cards with level 2 can be added again
        assertFalse( developmentCardSpace.canBeAdded(card2_2));

        //Checking cards with level = 1 can't be added
        assertFalse( developmentCardSpace.canBeAdded(card1_1));

        assertTrue(developmentCardSpace.canBeAdded(card3_1));
        developmentCardSpace.add(card3_1);

        //Checking cards with level 3 can be added again
        assertFalse( developmentCardSpace.canBeAdded(card3_2));

        //Checking cards with level < 3 can't be added
        assertFalse( developmentCardSpace.canBeAdded(card2_1));
        assertFalse( developmentCardSpace.canBeAdded(card1_1));
    }

    public void testPickTopCard() {
        DevelopmentCardSpace developmentCardSpace = DevelopmentCardSpace.getInstance();
        assertEquals(null, developmentCardSpace.pickTopCard());
        DevelopmentCard developmentCardA = DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST,DevelopmentCardType.GREEN);
        DevelopmentCard developmentCardB = DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST,DevelopmentCardType.BLUE);
        DevelopmentCard developmentCardC = DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND,DevelopmentCardType.YELLOW);
        developmentCardSpace.add(developmentCardA);
        assertEquals(developmentCardA,developmentCardSpace.pickTopCard());
        assertEquals(1,developmentCardSpace.linearize().size());
        developmentCardSpace.add(developmentCardB);
        assertEquals(developmentCardA,developmentCardSpace.pickTopCard());
        assertEquals(1,developmentCardSpace.linearize().size());
        developmentCardSpace.add(developmentCardC);
        assertEquals(developmentCardC,developmentCardSpace.pickTopCard());
        assertEquals(2,developmentCardSpace.linearize().size());

    }
}