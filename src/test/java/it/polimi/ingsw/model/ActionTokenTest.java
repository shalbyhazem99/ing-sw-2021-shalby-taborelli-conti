package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import org.junit.Test;


import static junit.framework.TestCase.assertEquals;


class ActionTokenTest {





    /**
     * Test for constructor
     */
    @Test
    public void testDevelopmentCard(){
        MarkerType action = MarkerType.MOVE;
        int count = 1;
        DevelopmentCardType cardToReject = DevelopmentCardType.PURPLE;
        ActionToken actionToken = new ActionToken(action,count, cardToReject);
        assertEquals(MarkerType.MOVE, actionToken.getAction());
        assertEquals(1, actionToken.getCount());
        assertEquals(cardToReject, actionToken.getCardToReject());
    }

}