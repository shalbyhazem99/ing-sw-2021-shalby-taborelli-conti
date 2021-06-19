package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCardAddWarehouse;
import it.polimi.ingsw.model.leaderCard.LeaderCardColor;
import it.polimi.ingsw.model.leaderCard.LeaderCardDiscount;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;


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


    //todo: così va bene come test?
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
            //todo:testing the method in the LeaderCard

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

    @Test
    public void testMarketInteraction() {
        Match match = new MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();

        //Testing if the move is not possible nothing is done
        match.marketInteraction(MoveType.COLUMN, 3, match.getCurrentPlayer(), false);
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(i).getResources().size());
        }

        //todo:finishing testing

    }

    /*
    @Test
    public void testPositioningResourcesInteraction(){
        Match match = new  MatchSolo();
        match.addPlayer(Player.getInstance("Player0"));
        match.startMatch();
        ArrayList<Integer> whereToPlace = new ArrayList<>();

        //Testing when no Resources need to be place nothing happen
        match.positioningResourcesInteraction(whereToPlace, match.getCurrentPlayer(), false);
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(i).getResources().size());
        }



        //todo: finish and control the method
        //Testing when there are not enough position index nothing happen
        match.marketInteraction(MoveType.COLUMN, 0, match.getCurrentPlayer(), true);
        match.positioningResourcesInteraction(whereToPlace, match.getCurrentPlayer(), false);
        for (int i = 0; i < 3; i++) {
            assertEquals(0, match.getCurrentPlayer().getWarehousesStandard().get(i).getResources().size());
        }
        assertEquals(match.getPendingMarketResources().size(), match.pendingMarketResources.size());
    }

     */
}