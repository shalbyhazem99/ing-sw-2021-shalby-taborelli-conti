package it.polimi.ingsw.model.developmentCard;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;


/**
 * Class used to Test {@link DevelopmentCardNeeded}
 */
public class DevelopmentCardNeededTest extends TestCase {
    public DevelopmentCardNeededTest() {
    }

    @Test
    public void testDevelopmentCarNeeded(){
        final int count = 0;
        DevelopmentCardType type = DevelopmentCardType.GREEN;
        DevelopmentCardLevel level = DevelopmentCardLevel.FIRST;
        DevelopmentCardNeeded developmentCardNeeded = new DevelopmentCardNeeded(count, type, level);
        
        assertEquals(0, developmentCardNeeded.getCount());
        assertEquals(DevelopmentCardType.GREEN, developmentCardNeeded.getType());
        assertEquals(DevelopmentCardLevel.FIRST, developmentCardNeeded.getLevel());
    }
}