package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

/**
 * Class used to test the {@link ActionToken}
 */
public class ActionTokenTest extends TestCase {

    public ActionTokenTest() {
    }

    /**
     * Test for constructor
     */
    @Test
    public void testActionToken() {
        MarkerType action = MarkerType.MOVE;
        final int count = 1;
        DevelopmentCardType cardToReject = DevelopmentCardType.GREEN;
        ActionToken actionToken = new ActionToken(action, count, cardToReject);

        assertEquals(action, actionToken.getAction());
        assertEquals(count, actionToken.getCount());
        assertEquals(cardToReject, actionToken.getCardToReject());
    }
}
