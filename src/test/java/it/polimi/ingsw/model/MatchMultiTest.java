package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.leaderCard.LeaderCardAddWarehouse;
import it.polimi.ingsw.model.leaderCard.LeaderCardColor;
import it.polimi.ingsw.model.leaderCard.LeaderCardDiscount;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;


public class MatchMultiTest extends TestCase {

    /**
     * Testing that a match multi is created, the correct number of {@link Player} is created and the position of the Inkwell is set to -1
     */
    @Test
    public void testMatchMulti() {
        //Testing the correct creation of a MatchMulti with two players
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);

            assertEquals(-1, matchMulti.getPosInkwell());
            assertEquals(i, matchMulti.getPlayers().size());
        }
    }

    /**
     * Testing for any number of {@link Player} in the {@link MatchMulti} the position of the Inkwell
     */
    @Test
    public void testRandomlyPickInkwellPlayer() {
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            assertEquals(-1, matchMulti.getPosInkwell());

            int position = matchMulti.randomlyPickInkwellPlayer();

            assertEquals(position, matchMulti.getPosInkwell());
            assertTrue(matchMulti.getPosInkwell() < i);
        }
    }

    /**
     * Testing the {@link MatchMulti} starts and  each {@link Player} gets 4 {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
     */
    @Test
    public void testStartMatch() {
        for (int i = 2; i <5 ; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            int position = matchMulti.randomlyPickInkwellPlayer();
            assertTrue(position != -1);

            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("g"+i));
            }
            matchMulti.startMatch();

            for (Player player : matchMulti.getPlayers()){
                assertEquals(4,player.getLeaderCards().size());
            }
        }
    }

    /**
     * Testing the number of Playing of the {@link Player}
     */
    @Test
    public void testGetdistPlayerFromInkwell(){
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+j));
            }
            matchMulti.startMatch();
            int inkwell = matchMulti.getPosInkwell();
            for (int j = 0; j < i; j++) {
                if (j>=inkwell){
                    assertEquals(j-inkwell, matchMulti.getdistPlayerFromInkwell(j));
                } else {
                    assertEquals(i+j-inkwell, matchMulti.getdistPlayerFromInkwell(j));
                }
            }
        }
    }


    /**
     * Testing the action of discardin a {@link LeaderCard}
     */
    @Test
    public void testAskForDiscardLeaderCard(){
        for (int i = 2; i <5 ; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("g"+j));
            }
            matchMulti.startMatch();

            matchMulti.askForDiscardLeaderCard();
        }
    }


    /**
     * Testing the correct process of discarding LeaderCard and the assigment of Resources depending the order of play
     */
    @Test
    public void testDiscardTwoLeaderCardInteraction(){
        //Testing for every type of MatchMulti
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+j));
            }
            matchMulti.startMatch();

            //Testing wrong input
            //Case: Same index of discarding
            matchMulti.discardTwoLeaderCardInteraction(0, 0, matchMulti.getCurrentPlayer(), ResourceType.COIN, ResourceType.SERVANT);
            assertEquals(0, matchMulti.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
            assertEquals(ResourceType.ANY, matchMulti.getCurrentPlayer().getWarehousesStandard().get(0).getResourceType());
            assertEquals(0, matchMulti.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
            assertEquals(ResourceType.ANY, matchMulti.getCurrentPlayer().getWarehousesStandard().get(1).getResourceType());
            assertEquals(0, matchMulti.getCurrentPlayer().getPosFaithMarker());
            assertEquals(4, matchMulti.getCurrentPlayer().getLeaderCards().size());

            //Case: One Index>4
            matchMulti.discardTwoLeaderCardInteraction(0, 99, matchMulti.getCurrentPlayer(), ResourceType.COIN, ResourceType.SERVANT);
            assertEquals(0, matchMulti.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
            assertEquals(ResourceType.ANY, matchMulti.getCurrentPlayer().getWarehousesStandard().get(0).getResourceType());
            assertEquals(0, matchMulti.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
            assertEquals(ResourceType.ANY, matchMulti.getCurrentPlayer().getWarehousesStandard().get(1).getResourceType());
            assertEquals(0, matchMulti.getCurrentPlayer().getPosFaithMarker());
            assertEquals(4, matchMulti.getCurrentPlayer().getLeaderCards().size());

            //Case: Both with index>4
            matchMulti.discardTwoLeaderCardInteraction(0, 0, matchMulti.getCurrentPlayer(), ResourceType.COIN, ResourceType.SERVANT);
            assertEquals(0, matchMulti.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
            assertEquals(ResourceType.ANY, matchMulti.getCurrentPlayer().getWarehousesStandard().get(0).getResourceType());
            assertEquals(0, matchMulti.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
            assertEquals(ResourceType.ANY, matchMulti.getCurrentPlayer().getWarehousesStandard().get(1).getResourceType());
            assertEquals(0, matchMulti.getCurrentPlayer().getPosFaithMarker());
            assertEquals(4, matchMulti.getCurrentPlayer().getLeaderCards().size());
            
            for(Player player : matchMulti.players){
                /*
                Asserting that the last player player in a match multi with 4 players, could choose 2 resources of the same type and they will
                be allocated in the WarehouseStandard 1
                 */
                if (matchMulti.players.size()==4){
                    matchMulti.discardTwoLeaderCardInteraction(0, 1, player, ResourceType.COIN, ResourceType.COIN);
                    assertEquals(2, player.getLeaderCards().size());
                    switch (matchMulti.getdistPlayerFromInkwell(matchMulti.getPlayers().indexOf(player))){ // get the turn position
                        case 1: // Is the second to play One Resource is assigned
                            assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(0, player.getPosFaithMarker());
                            break;
                        case 2: // Is the third to play One Resource and one Position Faith are assigned
                            assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(1, player.getPosFaithMarker());
                            break;
                        case 3: // Is the last to play. Two Resources and one Position Faith are assigned
                            assertEquals(0, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(2, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(1, player.getPosFaithMarker());
                            break;
                        default: //The first to play. No Resources of Position Faith are assigned
                            assertEquals(0, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(0, player.getPosFaithMarker());
                            break;
                    }
                }

                else {
                    matchMulti.discardTwoLeaderCardInteraction(0, 1, player, ResourceType.COIN, ResourceType.SHIELD);
                    assertEquals(2, player.getLeaderCards().size());
                    switch (matchMulti.getdistPlayerFromInkwell(matchMulti.getPlayers().indexOf(player))){ // get the turn position
                        case 1: // Is the second to play One Resource is assigned
                            assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(0, player.getPosFaithMarker());
                            break;
                        case 2: // Is the third to play One Resource and one Position Faith are assigned
                            assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(1, player.getPosFaithMarker());
                            break;
                        case 3: // Is the last to play. Two Resources and one Position Faith are assigned
                            assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(1, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.SHIELD, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(1, player.getPosFaithMarker());
                            break;
                        default: //The first to play. No Resources of Position Faith are assigned
                            assertEquals(0, player.getWarehousesStandard().get(0).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(0).getResourceType());
                            assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                            assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                            assertEquals(0, player.getPosFaithMarker());
                            break;
                    }
                }
            }
        }
        //Testing the other case of 4 player with 2 different resources
        MatchMulti matchMulti = new MatchMulti(4);
        matchMulti.addPlayer(Player.getInstance("Player0"));
        matchMulti.addPlayer(Player.getInstance("Player1"));
        matchMulti.addPlayer(Player.getInstance("Player2"));
        matchMulti.addPlayer(Player.getInstance("Player3"));
        matchMulti.startMatch();
        for (Player player:matchMulti.getPlayers()){
            matchMulti.discardTwoLeaderCardInteraction(0, 1, player, ResourceType.COIN, ResourceType.SHIELD);
            assertEquals(2, player.getLeaderCards().size());
            switch (matchMulti.getdistPlayerFromInkwell(matchMulti.getPlayers().indexOf(player))){ // get the turn position
                case 1: // Is the second to play One Resource is assigned
                    assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                    assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                    assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                    assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                    assertEquals(0, player.getPosFaithMarker());
                    break;
                case 2: // Is the third to play One Resource and one Position Faith are assigned
                    assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                    assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                    assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                    assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                    assertEquals(1, player.getPosFaithMarker());
                    break;
                case 3: // Is the last to play. Two Resources and one Position Faith are assigned
                    assertEquals(1, player.getWarehousesStandard().get(0).getResources().size());
                    assertEquals(ResourceType.COIN, player.getWarehousesStandard().get(0).getResourceType());
                    assertEquals(1, player.getWarehousesStandard().get(1).getResources().size());
                    assertEquals(ResourceType.SHIELD, player.getWarehousesStandard().get(1).getResourceType());
                    assertEquals(1, player.getPosFaithMarker());
                    break;
                default: //The first to play. No Resources of Position Faith are assigned
                    assertEquals(0, player.getWarehousesStandard().get(0).getResources().size());
                    assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(0).getResourceType());
                    assertEquals(0, player.getWarehousesStandard().get(1).getResources().size());
                    assertEquals(ResourceType.ANY, player.getWarehousesStandard().get(1).getResourceType());
                    assertEquals(0, player.getPosFaithMarker());
                    break;
            }
        }


    }


    /**
     * Testing the correct process of changing of the turn, whether all the {@link Player} are online or one is offline
     */
    @Test
    public void testUpdateTurn(){
        int turn;
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+j));
            }
            matchMulti.startMatch();


            //Testing when all the players are online
            turn=matchMulti.getTurn();
            matchMulti.updateTurn();
            if (turn== i-1){
                turn=0;
            } else {
                turn++;
            }
            assertEquals(turn, matchMulti.getTurn());

            //Setting the next Player offline
            if(turn == i-1){
                matchMulti.getPlayers().get(0).setOffline();
            } else {
                matchMulti.getPlayers().get(matchMulti.getTurn()+1).setOffline();
            }

            matchMulti.updateTurn();
            if (turn == i-1){
                assertEquals(1, matchMulti.getTurn());
            } else if ( turn == i -2){
                assertEquals(0, matchMulti.getTurn());
            } else {
                turn+=2;
                assertEquals(turn, matchMulti.getTurn());
            }
        }
    }

    /**
     * Testing the method returns the {@link Player} who is playing
     */
    @Test
    public void testGetCurrentPlayer(){
        for (int i = 2; i < 5; i++) {
            MatchMulti matchMulti = new MatchMulti(i);

            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+j));
            }

            matchMulti.startMatch();
            assertTrue(matchMulti.getPlayers().contains(matchMulti.getCurrentPlayer()));
        }
    }

    /**
     * Testing the correct checking of the turn
     */
    @Test
    public void testIsMyTurn(){
        int inkWell;
        for (int i = 2; i < 4; i++) {
            MatchMulti matchMulti = new MatchMulti(i);

            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+j));
            }
            matchMulti.startMatch();
            for (int j = 0; j < i; j++) {
                matchMulti.discardTwoLeaderCardInteraction(0, 1, matchMulti.getPlayers().get(j), ResourceType.COIN, ResourceType.SERVANT);
            }
            inkWell= matchMulti.getPosInkwell();
            for (int j = 0; j < i; j++) {

                if (j==inkWell){
                    assertTrue(matchMulti.isMyTurn(matchMulti.getPlayers().get(j)));
                } else {
                    assertFalse(matchMulti.isMyTurn(matchMulti.getPlayers().get(j)));
                }
            }
        }
    }


    /**
     * Testing if a {@link Player} can change a turn
     */
    @Test
    public void testSetCanChangeTurn(){
        for (int i = 2; i < 4; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+i));
            }
            //Setting the match
            matchMulti.startMatch();
            for (int j = 0; j < i; j++) {
                matchMulti.discardTwoLeaderCardInteraction(0,1, matchMulti.getPlayers().get(j), ResourceType.COIN, ResourceType.SERVANT);
            }

            for (int j = 0; j < i; j++) {
                matchMulti.setCanChangeTurn(true, matchMulti.getPlayers().get(j));
                if(j != matchMulti.getPosInkwell()){
                    assertFalse(matchMulti.getCanChangeTurn());
                } else {
                    assertTrue(matchMulti.getCanChangeTurn());
                    matchMulti.setCanChangeTurn(false, matchMulti.getPlayers().get(j));
                }
            }
        }
    }

    /**
     * Testing the end game method
     */
    @Test
    public void testHasWon(){
        for (int i = 2; i < 4; i++) {
            MatchMulti matchMulti = new MatchMulti(i);
            for (int j = 0; j < i; j++) {
                matchMulti.addPlayer(Player.getInstance("Player"+i));
            }
            matchMulti.startMatch();
            for (int j = 0; j < i; j++) {
                matchMulti.discardTwoLeaderCardInteraction(0,1, matchMulti.getPlayers().get(j), ResourceType.COIN, ResourceType.SERVANT);
            }

            assertFalse(matchMulti.hasWon());
            assertFalse(matchMulti.getIsTheFinalTurn());

            matchMulti.getPlayers().get(matchMulti.getPosInkwell()).moveAheadFaith(24);
            assertFalse(matchMulti.hasWon());
            assertTrue(matchMulti.getIsTheFinalTurn());
            //Testing that all the players play and it returns true only when the first who played plays again
            for (int j = 0; j < i; j++) {
                matchMulti.updateTurn();
                if (j!= i-1){
                    assertFalse(matchMulti.hasWon());
                } else {
                    assertTrue(matchMulti.hasWon());
                }
            }
        }
    }
}