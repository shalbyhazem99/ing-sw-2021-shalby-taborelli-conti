package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.Player;
import junit.framework.TestCase;

/**
 * Class used to test the {@link LeaderCard}
 */
public class LeaderCardTest extends TestCase {

    public void testIsActivable() {
    }

    public void testActive() {
    }

    public void testIsActive() {
    }

    public void testGetPoints() {
        LeaderCard leaderCard = new LeaderCard(2, null, null, null) {
            @Override
            public boolean active(Player player) {
                return false;
            }
        };
        assertEquals(2, leaderCard.getPoints());
    }
}