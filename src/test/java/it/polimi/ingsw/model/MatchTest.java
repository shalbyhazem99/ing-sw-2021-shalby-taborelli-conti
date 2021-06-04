package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leaderCard.LeaderCardAddWarehouse;
import it.polimi.ingsw.model.leaderCard.LeaderCardColor;
import it.polimi.ingsw.model.leaderCard.LeaderCardDiscount;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;


public class MatchTest extends TestCase {

    /**
     * Testing the evaluation of the Winner
     */
    @Test
    public void testWhoIsTheWinner1(){
        Match match = new MatchMulti(4);
        //Setting the different Players
        for (int i = 0; i < 4; i++) {
            match.addPlayer(Player.getInstance("Player"+i));
        }
        int i=-1;
        for (Player player: match.players){
            i++;
            switch (i){
                case 0:
                    //Position
                    player.moveAheadFaith(13);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().get(1).active();
                    player.getPopeFavorTiles().set(2, null);
                    //LeaderCards
                    player.addLeaderCard(LeaderCardAddWarehouse.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardAddWarehouse.getInstance(3, ResourceType.COIN, null, null));
                    player.getLeaderCards().get(1).active(player);
                    //Resources
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN),0);
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN),0);
                    break;
                case 1:
                    //Position
                    player.moveAheadFaith(24);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().set(1,null);
                    player.getPopeFavorTiles().get(2).active();
                    //LeaderCards
                    player.addLeaderCard(LeaderCardDiscount.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardColor.getInstance(3, ResourceType.COIN, null, null));
                    player.getLeaderCards().get(0).active(player);
                    player.getLeaderCards().get(1).active(player);
                    //Resources
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    break;
                case 2:
                    //Position
                    player.moveAheadFaith(18);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().get(1).active();
                    player.getPopeFavorTiles().get(2).active();
                    //LeaderCards
                    player.addLeaderCard(LeaderCardDiscount.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardColor.getInstance(3, ResourceType.COIN, null, null));
                    //Resources
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.STONE));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
                    break;
                default:
                    //Position
                    player.moveAheadFaith(24);
                    //Pope's Tales
                    player.getPopeFavorTiles().set(0,null);
                    player.getPopeFavorTiles().set(1,null);
                    player.getPopeFavorTiles().get(2).active();
                    //LeaderCards
                    player.addLeaderCard(LeaderCardDiscount.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardColor.getInstance(3, ResourceType.COIN, null, null));
                    player.getLeaderCards().get(0).active(player);
                    player.getLeaderCards().get(1).active(player);
                    //Resources
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
            }
        }
        ArrayList<Winner> winners = match.whoIsWinner();
        assertEquals(1, winners.size());
        assertEquals("Player1", winners.get(0).getName());
        assertEquals(33, winners.get(0).getPoints());
        assertEquals(6, winners.get(0).getTotalResources());
    }

    /**
     * Testing the evaluation of the Winner. 2 {@link Player} win
     */
    @Test
    public void testWhoIsTheWinner2(){
        Match match = new MatchMulti(4);
        //Setting the different Players
        for (int i = 0; i < 4; i++) {
            match.addPlayer(Player.getInstance("Player"+i));
        }
        int i=-1;
        for (Player player: match.players){
            i++;
            switch (i){
                case 0:
                    //Position
                    player.moveAheadFaith(10);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().get(1).active();
                    player.getPopeFavorTiles().set(2, null);
                    //LeaderCards
                    player.addLeaderCard(LeaderCardAddWarehouse.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardAddWarehouse.getInstance(3, ResourceType.COIN, null, null));
                    player.getLeaderCards().get(1).active(player);
                    //Resources
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN),0);
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN),0);
                    break;
                case 1:
                    //Position
                    player.moveAheadFaith(24);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().set(1,null);
                    player.getPopeFavorTiles().get(2).active();
                    //LeaderCards
                    player.addLeaderCard(LeaderCardDiscount.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardColor.getInstance(3, ResourceType.COIN, null, null));
                    player.getLeaderCards().get(0).active(player);
                    player.getLeaderCards().get(1).active(player);
                    //Resources
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 2);
                    break;
                case 2:
                    //Position
                    player.moveAheadFaith(3);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().get(1).active();
                    player.getPopeFavorTiles().get(2).active();
                    //LeaderCards
                    player.addLeaderCard(LeaderCardDiscount.getInstance(3, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardColor.getInstance(3, ResourceType.COIN, null, null));
                    //Resources
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.STONE));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.SHIELD));
                    break;
                default:
                    //Position
                    player.moveAheadFaith(23);
                    //Pope's Tales
                    player.getPopeFavorTiles().set(0,null);
                    player.getPopeFavorTiles().get(1).active();
                    player.getPopeFavorTiles().get(2).active();
                    //LeaderCards
                    player.addLeaderCard(LeaderCardDiscount.getInstance(5, ResourceType.SERVANT, null, null));
                    player.addLeaderCard(LeaderCardAddWarehouse.getInstance(4, ResourceType.COIN, null, null));
                    player.getLeaderCards().get(0).active(player);
                    player.getLeaderCards().get(1).active(player);
                    //Resources
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 1);
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.ANY));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
                    player.addResourceToStrongBox(Resource.getInstance(ResourceType.COIN));
            }
        }
        ArrayList<Winner> winners = match.whoIsWinner();
        assertEquals(2, winners.size());
        assertEquals("Player1", winners.get(0).getName());
        assertEquals(33, winners.get(0).getPoints());
        assertEquals(6, winners.get(0).getTotalResources());
        assertEquals("Player3", winners.get(1).getName());
        assertEquals(33, winners.get(1).getPoints());
        assertEquals(6, winners.get(1).getTotalResources());
    }
}