package it.polimi.ingsw.model;

import com.sun.jdi.ArrayReference;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.production.move.ResourceWarehouseType;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCardAddProductive;
import it.polimi.ingsw.model.leaderCard.LeaderCardAddWarehouse;
import it.polimi.ingsw.model.leaderCard.LeaderCardColor;
import it.polimi.ingsw.model.leaderCard.LeaderCardDiscount;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Test;

import java.util.ArrayList;


public class MatchTest extends TestCase {

    /**
     * Testing the evaluation of the Winner
     */
    @Test
    public void testWhoIsTheWinner1() {
        Match match = new MatchMulti(4);
        //Setting the different Players
        for (int i = 0; i < 4; i++) {
            match.addPlayer(Player.getInstance("Player" + i));
        }
        int i = -1;
        for (Player player : match.players) {
            i++;
            switch (i) {
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
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN), 0);
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN), 0);
                    break;
                case 1:
                    //Position
                    player.moveAheadFaith(24);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().set(1, null);
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
                    player.getPopeFavorTiles().set(0, null);
                    player.getPopeFavorTiles().set(1, null);
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
    public void testWhoIsTheWinner2() {
        Match match = new MatchMulti(4);
        //Setting the different Players
        for (int i = 0; i < 4; i++) {
            match.addPlayer(Player.getInstance("Player" + i));
        }
        int i = -1;
        for (Player player : match.players) {
            i++;
            switch (i) {
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
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN), 0);
                    player.addResourceToWarehouseAdditional(Resource.getInstance(ResourceType.COIN), 0);
                    break;
                case 1:
                    //Position
                    player.moveAheadFaith(24);
                    //Pope's Tales
                    player.getPopeFavorTiles().get(0).active();
                    player.getPopeFavorTiles().set(1, null);
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
                    player.getPopeFavorTiles().set(0, null);
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

    /**
     * Testing the correct getting of a {@link Player} from is position
     */
    @Test
    public void testGetPlayerFromPosition() {
        Match match = new MatchMulti(2);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));

        assertEquals("Player0", match.getPlayerFromPosition(0).getName());
    }

    /**
     * Testing the correct creation of the DevelopmentCard
     */
    @Test
    public void testGetDevelopmentCard() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player"));
        match.startMatch();
        assertEquals(3, match.getDevelopmentCards().length);
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.FIRST, match.getDevelopmentCards()[0][0].get(i).getLevel());
            assertEquals(DevelopmentCardType.GREEN, match.getDevelopmentCards()[0][0].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.FIRST, match.getDevelopmentCards()[0][1].get(i).getLevel());
            assertEquals(DevelopmentCardType.BLUE, match.getDevelopmentCards()[0][1].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.FIRST, match.getDevelopmentCards()[0][0].get(i).getLevel());
            assertEquals(DevelopmentCardType.YELLOW, match.getDevelopmentCards()[0][2].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.FIRST, match.getDevelopmentCards()[0][1].get(i).getLevel());
            assertEquals(DevelopmentCardType.PURPLE, match.getDevelopmentCards()[0][3].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.SECOND, match.getDevelopmentCards()[1][0].get(i).getLevel());
            assertEquals(DevelopmentCardType.GREEN, match.getDevelopmentCards()[1][0].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.SECOND, match.getDevelopmentCards()[1][1].get(i).getLevel());
            assertEquals(DevelopmentCardType.BLUE, match.getDevelopmentCards()[1][1].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.SECOND, match.getDevelopmentCards()[1][0].get(i).getLevel());
            assertEquals(DevelopmentCardType.YELLOW, match.getDevelopmentCards()[1][2].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.SECOND, match.getDevelopmentCards()[1][1].get(i).getLevel());
            assertEquals(DevelopmentCardType.PURPLE, match.getDevelopmentCards()[1][3].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.THIRD, match.getDevelopmentCards()[2][0].get(i).getLevel());
            assertEquals(DevelopmentCardType.GREEN, match.getDevelopmentCards()[1][0].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.THIRD, match.getDevelopmentCards()[2][1].get(i).getLevel());
            assertEquals(DevelopmentCardType.BLUE, match.getDevelopmentCards()[1][1].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.THIRD, match.getDevelopmentCards()[2][0].get(i).getLevel());
            assertEquals(DevelopmentCardType.YELLOW, match.getDevelopmentCards()[1][2].get(i).getType());
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(DevelopmentCardLevel.THIRD, match.getDevelopmentCards()[2][1].get(i).getLevel());
            assertEquals(DevelopmentCardType.PURPLE, match.getDevelopmentCards()[1][3].get(i).getType());
        }
    }

    /**
     * Testing the correct creation of the {@link it.polimi.ingsw.model.market.MarketBoard}
     */
    @Test
    public void testGetMarketBoard() {
        Match match = new MatchSolo();
        assertEquals(12, match.getMarketBoard().getRow(0).size() + match.getMarketBoard().getRow(1).size() + match.getMarketBoard().getRow(2).size());

        //Looking if Additional Marble is Blue
        if (match.getMarketBoard().getAdditionalMarble().getColor().compareTo(MarbleColor.BLUE) != 0) {
            assertEquals(2, (int) match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.BLUE).count() + (int) match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.BLUE).count() + (int) match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.BLUE).count());
        } else {
            assertEquals(1, (int) match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.BLUE).count() + (int) match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.BLUE).count() + (int) match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.BLUE).count());
        }

        //Looking if Additional Marble is Red
        if (match.getMarketBoard().getAdditionalMarble().getColor().compareTo(MarbleColor.RED) != 0) {
            assertEquals(1, (int) match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.RED).count() + (int) match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.RED).count() + (int) match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.RED).count());
        } else {
            assertEquals(0, (int) match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.RED).count() + (int) match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.RED).count() + (int) match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.RED).count());
        }
        //Looking if Additional Marble is White
        if (match.getMarketBoard().getAdditionalMarble().getColor().compareTo(MarbleColor.WHITE) != 0) {
            assertEquals(4, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.WHITE).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.WHITE).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.WHITE).count());
        } else {
            assertEquals(3, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.WHITE).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.WHITE).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.WHITE).count());
        }
        //Looking if Additional Marble is Grey
        if (match.getMarketBoard().getAdditionalMarble().getColor().compareTo(MarbleColor.GREY) != 0) {
            assertEquals(2, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.GREY).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.GREY).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.GREY).count());
        } else {
            assertEquals(1, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.GREY).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.GREY).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.GREY).count());
        }
        //Looking if Additional Marble is Yellow
        if (match.getMarketBoard().getAdditionalMarble().getColor().compareTo(MarbleColor.YELLOW) != 0) {
            assertEquals(2, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.YELLOW).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.YELLOW).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.YELLOW).count());
        } else {
            assertEquals(1, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.YELLOW).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.YELLOW).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.YELLOW).count());
        }
        //Looking if Additional Marble is Purple
        if (match.getMarketBoard().getAdditionalMarble().getColor().compareTo(MarbleColor.PURPLE) != 0) {
            assertEquals(2, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.PURPLE).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.PURPLE).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.PURPLE).count());
        } else {
            assertEquals(1, match.getMarketBoard().getRow(0).stream().filter(marble -> marble.getColor() == MarbleColor.PURPLE).count() + match.getMarketBoard().getRow(1).stream().filter(marble -> marble.getColor() == MarbleColor.PURPLE).count() + match.getMarketBoard().getRow(2).stream().filter(marble -> marble.getColor() == MarbleColor.PURPLE).count());
        }
    }

    /**
     * Testing the {@link it.polimi.ingsw.model.developmentCard.DevelopmentCard} given for each {@link DevelopmentCardLevel} and {@link DevelopmentCardType}
     */
    @Test
    public void testGetDevelopmentCardOnTop() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();


        assertEquals(match.getDevelopmentCards()[0][0].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[0][1].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.FIRST));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[0][2].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[0][3].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[1][0].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.SECOND));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[1][1].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.SECOND));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[1][2].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.SECOND));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[1][3].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.SECOND));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[2][0].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.THIRD));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[2][1].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.THIRD));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[2][2].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.THIRD));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        assertEquals(match.getDevelopmentCards()[2][3].get(3), match.getDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.THIRD));
        assertEquals(4, match.getDevelopmentCards()[0][0].size());
    }


    @Test
    public void testGetWhoAmI() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        assertEquals(0, match.getWhoAmI());
    }


    @Test
    public void testAddPlayer() {
        Match match = new MatchMulti(2);

        assertFalse(match.addPlayer(null));
        match.addPlayer(Player.getInstance("Player0"));
        assertFalse(match.addPlayer(Player.getInstance("Player0")));
    }

    @Test
    public void testSetWhoAmI() {
        Match match = new MatchSolo();
        match.setWhoAmI(3);
        assertEquals(3, match.getWhoAmI());
    }

    /**
     * Testing the correct picked of a {@link DevelopmentCard} on top
     */
    @Test
    public void testPickDevelopmentCardOnTop() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        DevelopmentCard developmentCard;

        developmentCard = match.getDevelopmentCards()[0][0].get(3);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST));
        assertEquals(3, match.getDevelopmentCards()[0][0].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.FIRST);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.FIRST));
        assertEquals(3, match.getDevelopmentCards()[0][1].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.FIRST));
        assertEquals(3, match.getDevelopmentCards()[0][2].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.FIRST));
        assertEquals(3, match.getDevelopmentCards()[0][3].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.SECOND);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.SECOND));
        assertEquals(3, match.getDevelopmentCards()[1][0].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.SECOND);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.SECOND));
        assertEquals(3, match.getDevelopmentCards()[1][1].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.SECOND);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.SECOND));
        assertEquals(3, match.getDevelopmentCards()[1][2].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.SECOND);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.SECOND));
        assertEquals(3, match.getDevelopmentCards()[1][3].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.THIRD);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.THIRD));
        assertEquals(3, match.getDevelopmentCards()[2][0].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.THIRD);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.BLUE, DevelopmentCardLevel.THIRD));
        assertEquals(3, match.getDevelopmentCards()[2][1].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.THIRD);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.YELLOW, DevelopmentCardLevel.THIRD));
        assertEquals(3, match.getDevelopmentCards()[2][2].size());

        developmentCard = match.getDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.THIRD);
        assertEquals(developmentCard, match.pickDevelopmentCardOnTop(DevelopmentCardType.PURPLE, DevelopmentCardLevel.THIRD));
        assertEquals(3, match.getDevelopmentCards()[2][3].size());
    }

    /**
     * Testing the correct discarding of two {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
     */
    @Test
    public void testDiscardTwoLeaderCardInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player"));
        match.startMatch();

        assertEquals(4, match.getCurrentPlayer().getLeaderCards().size());
        match.discardTwoLeaderCardInteraction(1, 1, match.getCurrentPlayer(), ResourceType.COIN, ResourceType.SERVANT);
        assertEquals(4, match.getCurrentPlayer().getLeaderCards().size());
        match.discardTwoLeaderCardInteraction(0, 1, match.getCurrentPlayer(), ResourceType.COIN, ResourceType.SERVANT);
        assertEquals(2, match.getCurrentPlayer().getLeaderCards().size());
    }

    /**
     * Testing the correct interaction of discarding a {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
     */
    @Test
    public void testDiscardLeaderCardInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        assertEquals(4, match.getCurrentPlayer().getLeaderCards().size());
        match.discardLeaderCardInteraction(0, match.getCurrentPlayer(), true);
        assertEquals(3, match.getCurrentPlayer().getLeaderCards().size());

        match.discardLeaderCardInteraction(0, match.getCurrentPlayer(), false);
        assertEquals(2, match.getCurrentPlayer().getLeaderCards().size());

        match.discardLeaderCardInteraction(3, match.getCurrentPlayer(), false);
        assertEquals(2, match.getCurrentPlayer().getLeaderCards().size());
    }


    /**
     * Testing the interaction of the activation of a {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
     */
    @Test
    public void testEnableLeaderCardInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        match.discardTwoLeaderCardInteraction(2, 3, match.getCurrentPlayer(), ResourceType.COIN, ResourceType.SERVANT);
        assertFalse(match.getCurrentPlayer().getLeaderCards().get(0).isActive());
        assertFalse(match.getCurrentPlayer().getLeaderCards().get(1).isActive());

        //Player Test has always 3 DevelopmentCard lv1, easy implementation for the test
        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, DevelopmentCardType.GREEN, 3, null, null), 0);
        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, DevelopmentCardType.BLUE, 3, null, null), 1);
        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, DevelopmentCardType.YELLOW, 3, null, null), 2);


        //For each leaderCard it adds the resources and the developmentCard to activate the power
        for (int i = 0; i < 2; i++) {
            //Adding Resources needed
            if (match.getCurrentPlayer().getLeaderCards().get(i).getResourcesNeeded().size() != 0) {
                match.getCurrentPlayer().addResourceToStrongBox(match.getCurrentPlayer().getLeaderCards().get(i).getResourcesNeeded());
            }

            //Adding DevelopmentCard needed
            //CASE SIZE=1 -> LeaderCard needs only a DevelopmentCard lv2 of a certain type
            if (match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().size() == 1) {
                match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().get(0).getType(), 3, null, null), i);
            }
            //CASE SIZE=2 -> LeaderCard needs 2 different DevelopmentCard of ony level, add another one if a Type Purple is needed
            else if (match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().size() == 2) {
                for (int j = 0; j < 2; j++) {
                    if (match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().get(j).getType() == DevelopmentCardType.PURPLE) {
                        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, DevelopmentCardType.PURPLE, 3, null, null), i);
                    }
                }
            }
            //CASE SIZE=3 -> LeaderCard needs 2 LeaderCard of one type and another one of a different type. Handling different situations
            else if (match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().size() == 3) {
                if (match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().get(1).getType() == DevelopmentCardType.PURPLE) {
                    match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, DevelopmentCardType.PURPLE, 3, null, null), i);
                    match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, DevelopmentCardType.PURPLE, 3, null, null), i);
                } else {
                    match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.SECOND, match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().get(1).getType(), 3, null, null), i);
                    if (match.getCurrentPlayer().getLeaderCards().get(i).getDevelopmentCardNeeded().get(2).getType() == DevelopmentCardType.PURPLE) {
                        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.THIRD, DevelopmentCardType.PURPLE, 3, null, null), i);
                    }
                }
            }
        }

        //Calling the method
        match.enableLeaderCardInteraction(1, match.getCurrentPlayer(), true);
        assertTrue(match.getCurrentPlayer().getLeaderCards().get(1).isActive());
        match.enableLeaderCardInteraction(3, match.getCurrentPlayer(), false);
        assertFalse(match.getCurrentPlayer().getLeaderCards().get(0).isActive());
        match.enableLeaderCardInteraction(0, match.getCurrentPlayer(), false);
        assertTrue(match.getCurrentPlayer().getLeaderCards().get(0).isActive());
    }

    /**
     * Testing the interaction of the management of {@link Resource} after buying them from the market
     */
    @Test
    public void testPositioningResourcesInteraction() {
        MatchSolo match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();
        ArrayList<Integer> whereToPlace = new ArrayList<>();

        //Testing when no Resources need to be place nothing happen
        match.positioningResourcesInteraction(whereToPlace, match.getCurrentPlayer(), false);
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(i).getResources().size());
        }

        //Testing when there are not enough position index nothing happen
        int whiteMarbleCOLMN = (int) match.getMarketBoard().getColumn(0).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
        int redMarbleCOLMN = (int) match.getMarketBoard().getColumn(0).stream().filter(elem -> elem.getColor() == MarbleColor.RED).count();
        match.marketInteraction(MoveType.COLUMN, 0, match.getCurrentPlayer(), true);
        match.positioningResourcesInteraction(whereToPlace, match.getCurrentPlayer(), false);
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(i).getResources().size());
        }
        assertEquals(3 - whiteMarbleCOLMN - redMarbleCOLMN, match.pendingMarketResources.size());

        //Counting the resources to place
        int blueMarble = (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SHIELD).count();
        int yellowMarble = (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.COIN).count();
        int greyMarble = (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.STONE).count();
        int purpleMarble = (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SERVANT).count();

        //Adding the indexes to correctly place the resources
        int reps = 0;
        for (Resource resource : match.pendingMarketResources) {
            switch (resource.getType()) {
                case COIN:
                    if (yellowMarble == blueMarble || yellowMarble == purpleMarble || yellowMarble == greyMarble) {
                        whereToPlace.add(reps);
                    } else if (yellowMarble == 1) {
                        if (whiteMarbleCOLMN != 0 || redMarbleCOLMN != 0) {
                            whereToPlace.add(reps);
                        } else {
                            whereToPlace.add(0);
                        }
                    } else {
                        whereToPlace.add(yellowMarble - 1);
                    }
                    break;
                case SHIELD:
                    if (yellowMarble == blueMarble || yellowMarble == purpleMarble || yellowMarble == greyMarble) {
                        whereToPlace.add(reps);
                    } else if (blueMarble == 1) {
                        if (whiteMarbleCOLMN != 0 || redMarbleCOLMN != 0) {
                            whereToPlace.add(reps);
                        } else {
                            whereToPlace.add(0);
                        }
                    } else {
                        whereToPlace.add(blueMarble - 1);
                    }
                    break;
                case SERVANT:
                    if (yellowMarble == blueMarble || yellowMarble == purpleMarble || yellowMarble == greyMarble) {
                        whereToPlace.add(reps);
                    } else if (purpleMarble == 1) {
                        if (whiteMarbleCOLMN != 0 || redMarbleCOLMN != 0) {
                            whereToPlace.add(reps);
                        } else {
                            whereToPlace.add(0);
                        }
                    } else {
                        whereToPlace.add(purpleMarble - 1);
                    }
                    break;
                case STONE:
                    if (yellowMarble == blueMarble || yellowMarble == purpleMarble || yellowMarble == greyMarble) {
                        whereToPlace.add(reps);
                    } else if (greyMarble == 1) {
                        if (whiteMarbleCOLMN != 0 || redMarbleCOLMN != 0) {
                            whereToPlace.add(reps);
                        } else {
                            whereToPlace.add(0);
                        }
                    } else {
                        whereToPlace.add(greyMarble - 1);
                    }
                    break;
                default:
                    break;
            }
            reps++;
        }
        match.positioningResourcesInteraction(whereToPlace, match.getCurrentPlayer(), true);
        int resourcesLocated = match.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size() + match.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size() + match.getCurrentPlayer().getWarehousesStandard().get(2).getResources().size();
        assertEquals(3 - whiteMarbleCOLMN - redMarbleCOLMN, resourcesLocated);


        //Testing the case when the player wants to discard a resources in solo
        whereToPlace = new ArrayList<>();
        match.marketInteraction(MoveType.COLUMN, 0, match.getCurrentPlayer(), true);
        for (int i = 0; i < match.pendingMarketResources.size(); i++) {
            whereToPlace.add(null);
        }
        match.positioningResourcesInteraction(whereToPlace, match.getCurrentPlayer(), true);
        assertEquals(0, match.getPosBlackCross());


        //Testing when the player discard resources in a multiplayer match
        MatchMulti matchMulti = new MatchMulti(2);
        matchMulti.addPlayer(Player.getInstance("Player0"));
        matchMulti.addPlayer(Player.getInstance("Player1"));
        matchMulti.startMatch();
        whereToPlace = new ArrayList<>();
        whiteMarbleCOLMN = (int) matchMulti.getMarketBoard().getColumn(0).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
        redMarbleCOLMN = (int) matchMulti.getMarketBoard().getColumn(0).stream().filter(elem -> elem.getColor() == MarbleColor.RED).count();
        matchMulti.marketInteraction(MoveType.COLUMN, 0, matchMulti.getCurrentPlayer(), true);
        for (int i = 0; i < matchMulti.pendingMarketResources.size(); i++) {
            whereToPlace.add(null);
        }
        matchMulti.positioningResourcesInteraction(whereToPlace, matchMulti.getCurrentPlayer(), false);
        if (matchMulti.getPosInkwell() == 0) {
            assertEquals(3 - whiteMarbleCOLMN - redMarbleCOLMN, matchMulti.getPlayers().get(1).getPosFaithMarker());
        } else {
            assertEquals(3 - whiteMarbleCOLMN - redMarbleCOLMN, matchMulti.getPlayers().get(0).getPosFaithMarker());
        }
    }


    @Test
    public void testMarketInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        //Testing if the move is not possible nothing is done
        match.marketInteraction(MoveType.COLUMN, 4, match.getCurrentPlayer(), false);
        assertEquals(0, match.getPendingMarketResources().size());
        match.marketInteraction(MoveType.ROW, 4, match.getCurrentPlayer(), false);
        assertEquals(0, match.getPendingMarketResources().size());

        int numberRedMarbles = 0;
        int numberWhiteMarbles = 0;
        int numberBlueMarbles = 0;
        int numberYellowMarbles = 0;
        int numberGreyMarbles = 0;
        int numberPurpleMarbles = 0;

        //Adding the redMarble in the Market and get the row 1
        if (match.getMarketBoard().getAdditionalMarble().getColor() == MarbleColor.RED) {
            match.marketInteraction(MoveType.ROW, 1, match.getCurrentPlayer(), true);
            match.skipTurn(match.getCurrentPlayer(), true);
            numberWhiteMarbles = (int) match.getMarketBoard().getRow(1).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
            numberBlueMarbles = (int) match.getMarketBoard().getRow(1).stream().filter(elem -> elem.getColor() == MarbleColor.BLUE).count();
            numberYellowMarbles = (int) match.getMarketBoard().getRow(1).stream().filter(elem -> elem.getColor() == MarbleColor.YELLOW).count();
            numberGreyMarbles = (int) match.getMarketBoard().getRow(1).stream().filter(elem -> elem.getColor() == MarbleColor.GREY).count();
            numberPurpleMarbles = (int) match.getMarketBoard().getRow(1).stream().filter(elem -> elem.getColor() == MarbleColor.PURPLE).count();
            match.marketInteraction(MoveType.ROW, 1, match.getCurrentPlayer(), false);
        }
        //Finding where is the RedMarble and getting that row
        else {
            for (int i = 0; i < 3; i++) {
                if (match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.RED).count() == 1) {
                    numberWhiteMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
                    numberBlueMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.BLUE).count();
                    numberYellowMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.YELLOW).count();
                    numberGreyMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.GREY).count();
                    numberPurpleMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.PURPLE).count();
                    match.marketInteraction(MoveType.ROW, i, match.getCurrentPlayer(), false);
                    break;
                }
            }
        }
        assertEquals(1, match.getCurrentPlayer().getPosFaithMarker());
        assertEquals(3 - numberWhiteMarbles, match.getPendingMarketResources().size());
        assertEquals(numberBlueMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SHIELD).count());
        assertEquals(numberYellowMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.COIN).count());
        assertEquals(numberGreyMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.STONE).count());
        assertEquals(numberPurpleMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SERVANT).count());
        match.skipTurn(match.getCurrentPlayer(), true);
        assertEquals(0, match.getPendingMarketResources().size());

        //Testing the case when I have a conversion strategy for the WhiteMarble
        match.getCurrentPlayer().addLeaderCard(LeaderCardColor.getInstance(2, ResourceType.SHIELD, null, null));
        assertEquals(3, match.getCurrentPlayer().getLeaderCards().size());
        match.getCurrentPlayer().getLeaderCard(2).active(match.getCurrentPlayer());
        for (int i = 0; i < 3; i++) {
            if (match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count() >= 1) {
                numberWhiteMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
                numberBlueMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.BLUE).count();
                numberYellowMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.YELLOW).count();
                numberGreyMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.GREY).count();
                numberPurpleMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.PURPLE).count();
                numberRedMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.RED).count();
                match.marketInteraction(MoveType.ROW, i, match.getCurrentPlayer(), false);
                break;
            }
        }
        assertEquals(1 + numberRedMarbles, match.getCurrentPlayer().getPosFaithMarker());
        assertEquals(4 - numberRedMarbles, match.getPendingMarketResources().size());
        assertEquals(numberBlueMarbles + numberWhiteMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SHIELD).count());
        assertEquals(numberYellowMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.COIN).count());
        assertEquals(numberGreyMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.STONE).count());
        assertEquals(numberPurpleMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SERVANT).count());
        match.skipTurn(match.getCurrentPlayer(), true);
        assertEquals(0, match.getPendingMarketResources().size());

        //Testing when the Player has activated 2 power of conversion
        match.getCurrentPlayer().addLeaderCard(LeaderCardColor.getInstance(2, ResourceType.SERVANT, null, null));
        assertEquals(4, match.getCurrentPlayer().getLeaderCards().size());
        match.getCurrentPlayer().getLeaderCard(3).active(match.getCurrentPlayer());
        numberWhiteMarbles=0;
        for (int i = 0; i < 3; i++) {
            if (match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count() > 1) {
                numberWhiteMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
                numberBlueMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.BLUE).count();
                numberYellowMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.YELLOW).count();
                numberGreyMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.GREY).count();
                numberPurpleMarbles = (int) match.getMarketBoard().getRow(i).stream().filter(elem -> elem.getColor() == MarbleColor.PURPLE).count();
                match.marketInteraction(MoveType.ROW, i, match.getCurrentPlayer(), false);
                break;
            }
        }
        if (numberWhiteMarbles < 1) {
            //Setting that a row in the Market has 2 WhiteMarble
            do {
                assertEquals(MarbleColor.WHITE, match.getMarketBoard().getAdditionalMarble().getColor());
                match.marketInteraction(MoveType.ROW, 0, match.getCurrentPlayer(), false);
                match.skipTurn(match.getCurrentPlayer(), true);
                assertEquals(0, match.getPendingMarketResources().size());
            } while (match.getMarketBoard().getAdditionalMarble().getColor() == MarbleColor.WHITE);
            numberWhiteMarbles = (int) match.getMarketBoard().getRow(0).stream().filter(elem -> elem.getColor() == MarbleColor.WHITE).count();
            numberBlueMarbles = (int) match.getMarketBoard().getRow(0).stream().filter(elem -> elem.getColor() == MarbleColor.BLUE).count();
            numberYellowMarbles = (int) match.getMarketBoard().getRow(0).stream().filter(elem -> elem.getColor() == MarbleColor.YELLOW).count();
            numberGreyMarbles = (int) match.getMarketBoard().getRow(0).stream().filter(elem -> elem.getColor() == MarbleColor.GREY).count();
            numberPurpleMarbles = (int) match.getMarketBoard().getRow(0).stream().filter(elem -> elem.getColor() == MarbleColor.PURPLE).count();
            match.marketInteraction(MoveType.ROW, 0, match.getCurrentPlayer(), true);
        }

        //Testing if Player makes a wrong input
        match.marketMarbleConvertInteraction(numberWhiteMarbles - 1, 3, match.getCurrentPlayer(), false);
        assertEquals(numberBlueMarbles + numberGreyMarbles + numberPurpleMarbles + numberYellowMarbles, match.getPendingMarketResources().size());

        //Asking to convert one WhiteMarble with the second PowerConversion, then the remaining with the first PowerConversion
        match.marketMarbleConvertInteraction(numberWhiteMarbles - 1, 1, match.getCurrentPlayer(), false);
        assertEquals(numberBlueMarbles + numberWhiteMarbles - 1, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SHIELD).count());
        assertEquals(numberYellowMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.COIN).count());
        assertEquals(numberGreyMarbles, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.STONE).count());
        assertEquals(numberPurpleMarbles + 1, (int) match.getPendingMarketResources().stream().filter(elem -> elem.getType() == ResourceType.SERVANT).count());
    }


    /**
     * Testing the interaction of moving {@link Resource} between {@link Warehouse}
     */
    public void testMoveResourcesInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0);

        //Testing when the Player gives the same index nothing happens
        match.MoveResourcesInteraction(0, 0, 1, 0, match.getCurrentPlayer(), false);
        assertEquals(1, match.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
        assertEquals(ResourceType.COIN, match.getCurrentPlayer().getWarehousesStandard().get(0).getResourceType());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(2).getResources().size());

        //Testing when the Player gives the index of a warehouseAdditional, but he hasn't any
        match.MoveResourcesInteraction(4, 3, 1, 0, match.getCurrentPlayer(), false);
        assertEquals(1, match.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
        assertEquals(ResourceType.COIN, match.getCurrentPlayer().getWarehousesStandard().get(0).getResourceType());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(2).getResources().size());

        //Testing the correct workflow
        match.MoveResourcesInteraction(0, 1, 1, 0, match.getCurrentPlayer(), false);
        assertEquals(1, match.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
        assertEquals(ResourceType.COIN, match.getCurrentPlayer().getWarehousesStandard().get(1).getResourceType());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(2).getResources().size());
    }

    /**
     * Testing the interaction of buying a {@link DevelopmentCard} by a {@link Player}
     */
    @Test
    public void testBuyDevelopmentCardInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player"));
        match.startMatch();
        ArrayList<ResourcePick> resourcesToPick = new ArrayList<>();

        //Testing if a Player hasn't enough resources to buy the DevelopmentCard nothing happen
        match.buyDevelopmentCardInteraction(DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST, match.getCurrentPlayer(),0, resourcesToPick, true);
        assertEquals(0, match.getCurrentPlayer().getDevelopmentCards().size());
        assertEquals(4, match.getDevelopmentCards()[0][0].size());

        //Giving the resources to Player
        int index = 0;
        for (ResourcesCount resourcesCount : match.getDevelopmentCardOnTop(DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST).getCosts(match.getCurrentPlayer())) {
            for (int i = 0; i < resourcesCount.getCount(); i++) {
                match.getCurrentPlayer().getWarehousesStandard().get(2 - index).addResource(Resource.getInstance(resourcesCount.getType()));
                resourcesToPick.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 2 - index, resourcesCount.getType()));
            }
            index++;
        }
        int resourcesPlayer = match.getCurrentPlayer().getResources().size();

        //Testing when Player chooses a wrong place to place the DevelopmentCard nothing happen
        match.buyDevelopmentCardInteraction(DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST, match.getCurrentPlayer(), 4, resourcesToPick, false);
        assertEquals(0, match.getCurrentPlayer().getDevelopmentCards().size());
        assertTrue(match.getCurrentPlayer().getDevelopmentCards().isEmpty());
        assertEquals(resourcesPlayer, match.getCurrentPlayer().getResources().size());

        //Testing that if Player buy a DevelopmentCard he can't add nothing happen
        match.buyDevelopmentCardInteraction(DevelopmentCardType.GREEN, DevelopmentCardLevel.SECOND, match.getCurrentPlayer(), 0, resourcesToPick, false);
        assertEquals(0, match.getCurrentPlayer().getDevelopmentCards().size());
        assertEquals(4, match.getDevelopmentCards()[1][0].size());
        assertEquals(resourcesPlayer, match.getCurrentPlayer().getResources().size());

        //Testing the correct workflow
        match.buyDevelopmentCardInteraction(DevelopmentCardType.GREEN, DevelopmentCardLevel.FIRST, match.getCurrentPlayer(), 0, resourcesToPick, false);
        assertEquals(1, match.getCurrentPlayer().getDevelopmentCards().size());
        assertEquals(DevelopmentCardType.GREEN, match.getCurrentPlayer().getDevelopmentCards().get(0).getType());
        assertEquals(DevelopmentCardLevel.FIRST, match.getCurrentPlayer().getDevelopmentCards().get(0).getLevel());
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(i).getResources().size());
        }

        //Testing if a Player hasn't enough resources to buy the DevelopmentCard nothing happen
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0);
        resourcesToPick = new ArrayList<>();
        resourcesToPick.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.COIN));
        match.buyDevelopmentCardInteraction(DevelopmentCardType.BLUE, DevelopmentCardLevel.FIRST, match.getCurrentPlayer(),1, resourcesToPick, true);
        assertEquals(1, match.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
        assertEquals(ResourceType.COIN, match.getCurrentPlayer().getWarehousesStandard().get(0).getResourceType());
        assertEquals(1, match.getCurrentPlayer().getDevelopmentCards().size());
        assertEquals(4, match.getDevelopmentCards()[0][1].size());
    }

    /**
     * Testing interaction of activation of a ProductivePower gained by a {@link LeaderCardAddProductive}
     */
    @Test
    public void testActivateProductivePower() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        //Setting for the call of the method
        ArrayList<ResourcesCount> resourcesCountArrayList = new ArrayList<>();
        resourcesCountArrayList.add(ResourcesCount.getInstance(1, ResourceType.COIN));
        ArrayList<Resource> resourceArrayList = new ArrayList<>();
        resourceArrayList.add(Resource.getInstance(ResourceType.STONE));
        resourceArrayList.add(Resource.getInstance(ResourceType.FAITH));
        ProductivePower power = new ProductivePower(resourcesCountArrayList, resourceArrayList);

        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0);
        ArrayList<ResourcePick> resourcePickArrayList = new ArrayList<>();
        resourcePickArrayList.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.COIN));


        //Testing the normal operation
        match.activateProductivePower(power, resourcePickArrayList, match.getCurrentPlayer(), false);
        assertEquals(1, match.getCurrentPlayer().getPosFaithMarker());
        assertEquals(1, match.getPendingProductionResources().size());
        assertEquals(ResourceType.STONE, match.getPendingProductionResources().get(0).getType());

        //Testing that if there is an error, nothing happen
        match.activateProductivePower(power, resourcePickArrayList, match.getCurrentPlayer(), false);
        assertEquals(1, match.getCurrentPlayer().getPosFaithMarker());
        assertEquals(1, match.getPendingProductionResources().size());
        assertEquals(ResourceType.STONE, match.getPendingProductionResources().get(0).getType());
    }

    /**
     * Testing the interaction with the activation of the base Production
     */
    @Test
    public void testEnableProductionBaseInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SERVANT), 1);

        ArrayList<ResourcePick> resourcePickArrayList = new ArrayList<>();
        resourcePickArrayList.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.SHIELD));
        resourcePickArrayList.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 1, ResourceType.SERVANT));

        match.enableProductionBaseInteraction(resourcePickArrayList, ResourceType.SERVANT, match.getCurrentPlayer(), true);
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(0).getResources().size());
        assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(1).getResources().size());
        assertEquals(1, match.getPendingProductionResources().size());
    }

    /**
     * Testing the interaction of the activation of the {@link DevelopmentCard}
     */
    @Test
    public void testEnableProductionDevelopmentInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player"));
        match.startMatch();

        ArrayList<ResourcesCount> resourcesCountArrayList = new ArrayList<>();
        resourcesCountArrayList.add(ResourcesCount.getInstance(1, ResourceType.COIN));
        ArrayList<Resource> resourceArrayList = new ArrayList<>();
        resourceArrayList.add(Resource.getInstance(ResourceType.SHIELD));
        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, DevelopmentCardType.GREEN, 0, null, ProductivePower.getInstance(resourcesCountArrayList, resourceArrayList)), 0);
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0);

        ArrayList<ResourcePick> resourcePickArrayList = new ArrayList<>();
        resourcePickArrayList.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.COIN));

        //Testing if the Player makes an error, after that insert correctly and then makes an error again
        match.enableProductionDevelopmentInteraction(resourcePickArrayList, 4, match.getCurrentPlayer(), false);
        assertEquals(0, match.getPendingProductionResources().size());
        match.enableProductionDevelopmentInteraction(resourcePickArrayList, 0, match.getCurrentPlayer(), true);
        assertEquals(1, match.getPendingProductionResources().size());
        assertEquals(ResourceType.SHIELD, match.getPendingProductionResources().get(0).getType());
        match.enableProductionDevelopmentInteraction(resourcePickArrayList, 2, match.getCurrentPlayer(), false);
        assertEquals(1, match.getPendingProductionResources().size());
        assertEquals(ResourceType.SHIELD, match.getPendingProductionResources().get(0).getType());
    }

    /**
     * Testing the interaction of the activation of the production of a {@link LeaderCardAddProductive}
     */
    @Test
    public void testEnableProductionLeaderInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player"));
        match.startMatch();

        //Setting for the test
        match.getCurrentPlayer().addLeaderCard(LeaderCardAddProductive.getInstance(2, ResourceType.SHIELD, null, null));
        match.getCurrentPlayer().getLeaderCards().get(4).active(match.getCurrentPlayer());
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
        ArrayList<ResourcePick> whereToPick = new ArrayList<>();
        whereToPick.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.SHIELD));

        //Testing wrong, correct, wrong interaction
        match.enableProductionLeaderInteraction(whereToPick, 4, ResourceType.SERVANT, match.getCurrentPlayer(), false);
        assertEquals(0, match.getPendingProductionResources().size());
        match.enableProductionLeaderInteraction(whereToPick, 0, ResourceType.SERVANT, match.getCurrentPlayer(), true);
        assertEquals(1, match.getPendingProductionResources().size());
        assertEquals(ResourceType.SERVANT, match.getPendingProductionResources().get(0).getType());
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
        match.enableProductionLeaderInteraction(whereToPick, 4, ResourceType.SERVANT, match.getCurrentPlayer(), false);
        assertEquals(1, match.getPendingProductionResources().size());
        assertEquals(ResourceType.SERVANT, match.getPendingProductionResources().get(0).getType());
    }

    /**
     * Testing the interaction of the end of a turn
     */
    @Test
    public void testEndRoundInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        //Setting for the test
        match.getCurrentPlayer().addLeaderCard(LeaderCardAddProductive.getInstance(2, ResourceType.SHIELD, null, null));
        match.getCurrentPlayer().getLeaderCards().get(4).active(match.getCurrentPlayer());
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.SHIELD), 0);
        ArrayList<ResourcePick> whereToPick = new ArrayList<>();
        whereToPick.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.SHIELD));
        match.enableProductionLeaderInteraction(whereToPick, 0, ResourceType.STONE, match.getCurrentPlayer(), true);

        //Todo:sistemare il valore dell'equals dopo aver tolto le risorse extra
        match.endRoundInteraction(match.getCurrentPlayer(), true);
        assertEquals(0, match.getPendingProductionResources().size());
        assertEquals(11, match.getCurrentPlayer().getStrongBox().stream().filter(elem -> elem.getType() == ResourceType.STONE).count());

    }


    /**
     * Testing the activation of the Pope's Tales' activation zone
     */
    @Test
    public void testControlPopePath() {
        Match match = new MatchMulti(3);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));
        match.addPlayer(Player.getInstance("Player2"));
        match.startMatch();

        //Testing when any Players haven't reach an activation zone
        match.controlPopePath();
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0).isActive());
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2).isActive());

        //Testing Player0 activate the Pope's Tale1, Player1 is in the activation zone and Player2 is before the zone
        match.getPlayerFromPosition(0).moveAheadFaith(8);
        match.getPlayerFromPosition(1).moveAheadFaith(5);
        match.controlPopePath();
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).getPoints());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).getPoints());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0));
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2).isActive());

        //Testing that if the Player2 reaches the activation zone of the first Pope's Tale nothing change
        match.getPlayerFromPosition(2).moveAheadFaith(8);
        match.controlPopePath();
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).getPoints());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).getPoints());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0));
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1).isActive());
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2).isActive());

        //Testing Player0 activate the Pope's Tale2, Player1 is in the activation zone and Player2 is before the zone
        match.getPlayerFromPosition(0).moveAheadFaith(8);
        match.getPlayerFromPosition(1).moveAheadFaith(8);
        match.controlPopePath();
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).getPoints());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).getPoints());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0));
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1));
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2).isActive());

        //Testing that if the Player2 reaches the activation zone of the second Pope's Tale nothing change
        match.getPlayerFromPosition(2).moveAheadFaith(8);
        match.controlPopePath();
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).getPoints());
        assertFalse(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).getPoints());
        assertFalse(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0));
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1));
        assertFalse(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2).isActive());

        //Testing Player0 activate the Pope's Tale3, Player1 is in the activation zone and Player2 is before the zone
        match.getPlayerFromPosition(0).moveAheadFaith(8);
        match.getPlayerFromPosition(1).moveAheadFaith(8);
        match.controlPopePath();
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).getPoints());
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertEquals(4, match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertEquals(4, match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).getPoints());
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0));
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1));
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2));

        //Testing that if the Player2 reaches the activation zone of the third Pope's Tale nothing change
        match.getPlayerFromPosition(2).moveAheadFaith(8);
        match.controlPopePath();
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(0).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(0).getPopeFavorTiles().get(1).getPoints());
        assertTrue(match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).isActive());
        assertEquals(4, match.getPlayerFromPosition(0).getPopeFavorTiles().get(2).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).isActive());
        assertEquals(2, match.getPlayerFromPosition(1).getPopeFavorTiles().get(0).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).isActive());
        assertEquals(3, match.getPlayerFromPosition(1).getPopeFavorTiles().get(1).getPoints());
        assertTrue(match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).isActive());
        assertEquals(4, match.getPlayerFromPosition(1).getPopeFavorTiles().get(2).getPoints());
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(0));
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(1));
        assertNull(match.getPlayerFromPosition(2).getPopeFavorTiles().get(2));
    }

    /**
     * Covering the case of the testing that miss from {@link MatchSolo} and from {@link MatchMulti} test: When 2 players
     * have the same points, but only one wins because he has more {@link Resource}. Players whe in the position: 2,8,17,24. Player2 wins
     */
    @Test
    public void testWhoIsWinner() {
        Match match = new MatchMulti(4);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));
        match.addPlayer(Player.getInstance("Player2"));
        match.addPlayer(Player.getInstance("Player3"));
        match.startMatch();

        match.getPlayerFromPosition(2).moveAheadFaith(24);
        match.getPlayerFromPosition(0).moveAheadFaith(2);
        match.getPlayerFromPosition(1).moveAheadFaith(8);
        match.getPlayerFromPosition(3).moveAheadFaith(17);
        match.controlPopePath();
        for (int i = 0; i < 75; i++) {
            match.getPlayerFromPosition(3).addResourceToStrongBox(Resource.getInstance(ResourceType.SERVANT));
        }

        ArrayList<Winner> winners = match.whoIsWinner();
        assertEquals("Player3", match.whoIsWinner().get(0).getName());
    }

    /**
     * Testing the research of a {@link Player} from his name
     */
    @Test
    public void testGetPlayerFromName() {
        Match match = new MatchMulti(2);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));
        match.startMatch();
        int hasPlayer = match.getPlayers().get(0).hashCode();

        assertEquals(hasPlayer, match.getPlayerFromName("Player0").hashCode());
        assertNull(match.getPlayerFromName("PIPPO"));

    }

    /**
     * Testing the reconnection of a {@link Player}
     */
    @Test
    public void testReconnectPlayer() {
        Match match = new MatchMulti(2);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));
        match.startMatch();

        match.getPlayers().get(0).makeOffline();

        match.ReconnectPlayer("PLUTO", false);
        assertTrue(match.getPlayers().get(0).isOffline());
        assertFalse(match.getPlayers().get(1).isOffline());

        match.ReconnectPlayer("Player0", false);
        assertFalse(match.getPlayers().get(0).isOffline());
        assertFalse(match.getPlayers().get(1).isOffline());
    }

    /**
     * Testing the reconnection of a {@link Player}
     */
    @Test
    public void testDisconnectPlayer() {
        Match match = new MatchMulti(2);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));
        match.startMatch();

        match.disconnectPlayer("TOPOLINO", true);
        assertFalse(match.getPlayers().get(0).isOffline());
        assertFalse(match.getPlayers().get(1).isOffline());

        match.disconnectPlayer("Player0", false);
        assertTrue(match.getPlayers().get(0).isOffline());
        assertFalse(match.getPlayers().get(1).isOffline());
    }

    @Test
    public void testSkipTurn() {
        Match match = new MatchMulti(2);
        match.addPlayer(Player.getInstance("Player0"));
        match.addPlayer(Player.getInstance("Player1"));
        match.startMatch();
        int position;
        int pendingResources;

        if (match.getCurrentPlayer().equals(match.getPlayerFromPosition(0))) {
            position = 0;
        } else {
            position = 1;
        }

        match.skipTurn(match.getCurrentPlayer(), true);
        match.skipTurn(match.getCurrentPlayer(), true);
        for (int i = 0; i < 2; i++) {
            assertEquals(2, match.getPlayerFromPosition(i).getLeaderCards().size());
        }

        match.marketInteraction(MoveType.ROW, 0, match.getCurrentPlayer(), true);
        if (match.getPendingMarketResources().size() == 0) {
            match.marketInteraction(MoveType.ROW, 1, match.getCurrentPlayer(), true);
        }
        pendingResources = match.getPendingMarketResources().size();

        match.skipTurn(match.getCurrentPlayer(), true);
        assertEquals(pendingResources, match.getCurrentPlayer().getPosFaithMarker());
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getPlayerFromPosition(position).getWarehousesStandard().get(i).getResources().size());
        }

        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(ResourcesCount.getInstance(1, ResourceType.COIN));
        ArrayList<Resource> to = new ArrayList<>();
        to.add(Resource.getInstance(ResourceType.SERVANT));
        match.getCurrentPlayer().addDevelopmentCard(DevelopmentCard.getInstance(DevelopmentCardLevel.FIRST, DevelopmentCardType.GREEN, 3, null, ProductivePower.getInstance(from, to)), 0);
        match.getCurrentPlayer().addResourceToWarehouseStandard(Resource.getInstance(ResourceType.COIN), 0);
        ArrayList<ResourcePick> wherePick = new ArrayList<>();
        wherePick.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, ResourceType.COIN));
        match.enableProductionDevelopmentInteraction(wherePick, 0, match.getCurrentPlayer(), true);

        match.skipTurn(match.getCurrentPlayer(), false);
        //todo: cambiare questo assert dopo aver tolo la cosa delle risorse iniziali
        if (position == 0) {
            assertEquals(41, match.getPlayerFromPosition(1).getStrongBox().size());
        } else {
            assertEquals(41, match.getPlayerFromPosition(0).getStrongBox().size());
        }
    }
}