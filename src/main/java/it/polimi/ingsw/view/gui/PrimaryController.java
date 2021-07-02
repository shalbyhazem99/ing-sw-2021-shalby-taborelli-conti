package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.view.ClientConnectionView;
import it.polimi.ingsw.connection.view.ClientConnectionViewMulti;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.DiscardLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.EnableLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketMarbleConversionMove;
import it.polimi.ingsw.controller.move.moveResources.MoveResourcesPlayerMove;
import it.polimi.ingsw.controller.move.production.move.*;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesPlayerMove;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PrimaryController extends GenericController {

    private Stack<ResourcePick> pendingSelected;
    private MovePlayerType runningAction;
    private boolean hasPerformedUnBlockingAction = false;
    private DevelopmentCard developmentCardSelected;
    private ArrayList<Resource> resNeededDevelopmentCardSelected;
    private Stack<ResourcePick> resDevCardSelected;
    private DataFormat leaderCardDragFormat = new DataFormat("leaderCard");
    private DataFormat marketPendingDragFormat = new DataFormat("marketPending");
    private DataFormat resourceDragFormat = new DataFormat("resource");

    @FXML
    private TabPane tabpane;
    @FXML
    private Pane leadercard1;
    @FXML
    private Pane leadercard2;
    @FXML
    private Sphere m_add, m_00, m_01, m_02, m_03, m_10, m_11, m_12, m_13, m_20, m_21, m_22, m_23;
    private Sphere[][] marketBoardObj;
    @FXML
    private Sphere additionalMarble;
    @FXML
    private Pane[] leadercards;
    @FXML
    private Pane devcard_00, devcard_01, devcard_02, devcard_03, devcard_10, devcard_11, devcard_12, devcard_13, devcard_20, devcard_21, devcard_22, devcard_23;
    @FXML
    private Pane[][] devcardmatrix;
    @FXML
    private Pane btn_col_0;
    @FXML
    private Pane btn_col_1;
    @FXML
    private Pane btn_col_2;
    @FXML
    private Pane btn_col_3;
    @FXML
    private Pane btn_row_0;
    @FXML
    private Pane btn_row_1;
    @FXML
    private Pane btn_row_2;
    @FXML
    private Pane[] market_button;

    //PENDING
    @FXML
    private Pane coin_pending;
    @FXML
    private Pane stone_pending;
    @FXML
    private Pane servant_pending;
    @FXML
    private Pane shield_pending;

    @FXML
    private ArrayList<Pane> market_pending_panes;

    @FXML
    private Text coin_pending_text;
    @FXML
    private Text stone_pending_text;
    @FXML
    private Text servant_pending_text;
    @FXML
    private Text shield_pending_text;

    //FAITH
    @FXML
    private Pane faith_0, faith_1, faith_2, faith_3, faith_4, faith_5, faith_6, faith_7, faith_8, faith_9, faith_10, faith_11, faith_12, faith_13, faith_14, faith_15, faith_16, faith_17, faith_18, faith_19, faith_20, faith_21, faith_22, faith_23, faith_24;
    @FXML
    private Pane[] faithArray;
    @FXML
    private Rectangle faith_player;

    //PopeFavorTiles
    @FXML
    private Pane pope_tiles_1;
    @FXML
    private Pane pope_tiles_2;
    @FXML
    private Pane pope_tiles_3;
    @FXML
    private ArrayList<Pane> pope_tiles_list;

    @FXML
    private Pane ware_00;
    @FXML
    private Pane ware_10;
    @FXML
    private Pane ware_11;
    @FXML
    private Pane ware_20;
    @FXML
    private Pane ware_21;
    @FXML
    private Pane ware_22;
    //additional
    @FXML
    private Pane ware_30;
    @FXML
    private Pane ware_31;
    @FXML
    private Pane ware_40;
    @FXML
    private Pane ware_41;
    @FXML
    private Pane bin;

    @FXML
    private ArrayList<ArrayList<Pane>> warehousesStandard;
    @FXML
    private ArrayList<ArrayList<Pane>> warehousesAdditional;

    @FXML
    private Pane button_card_space_0;
    @FXML
    private Pane button_card_space_1;
    @FXML
    private Pane button_card_space_2;
    @FXML
    private ArrayList<Pane> buttons_card_space;
    @FXML
    private ArrayList<Pane> card_spaces;
    @FXML
    private Pane card_space_0;
    @FXML
    private Pane card_space_1;
    @FXML
    private Pane card_space_2;

    @FXML
    private Pane stone_strongbox;
    @FXML
    private Pane coin_strongbox;
    @FXML
    private Pane servant_strongbox;
    @FXML
    private Pane shield_strongbox;
    @FXML
    private ArrayList<Pane> strongbox_panes;


    @FXML
    private Text coin_strongbox_text;
    @FXML
    private Text servant_strongbox_text;
    @FXML
    private Text shield_strongbox_text;
    @FXML
    private Text stone_strongbox_text;

    @FXML
    private Pane production_leader_1;
    @FXML
    private Pane production_leader_2;
    @FXML
    private Pane ware_add_1;
    @FXML
    private Pane ware_add_2;
    @FXML
    private Text ware_add_type_1;
    @FXML
    private Text ware_add_type_2;

    //posfaith lorenzo
    @FXML
    private Text lorenzo_pos_faith;


    //production variable

    @FXML
    private Pane production_base_from_1;
    @FXML
    private Pane production_base_from_2;
    @FXML
    private Pane production_leader_from_1;
    @FXML
    private Pane production_leader_from_2;
    @FXML
    private Pane production_base_to;
    @FXML
    private Pane production_leader_to_1;
    @FXML
    private Pane production_leader_to_2;
    @FXML
    private Button changeProductionBaseTo;
    @FXML
    private Button changeProductionLeader1;
    @FXML
    private Button changeProductionLeader2;
    private ResourceWarehouseType resourceWarehouseTypeProduction;
    private int posWareHouseProduction;
    private ResourceType resourceTypeProduction;
    private Pane wareHousePaneFromProduction;
    private ArrayList<Resource> activeProductivePowerCost = null;
    private int activePlayerPos = 0;

    private enum ProductionType {BASE, LEADER_CARD, DEVELOPMENT_CARD, NOTHING;}

    private ProductionType activeProduction = ProductionType.NOTHING;
    private int activeProductionIndex;
    @FXML
    private Text coin_production_pending_text;
    @FXML
    private Text stone_production_pending_text;
    @FXML
    private Text servant_production_pending_text;
    @FXML
    private Text shield_production_pending_text;

    @FXML
    private ComboBox players_list_combobox;

    @Override
    public void update(MoveResponse message) {
        //elaborate message
        if (match != null) {
            if (AppLocal.getConnection() == null)
                message.updateLocalMatch(match);
        }
        message.elaborateGUI(this);
    }

    //--------------------------------------Initialization-----------------------------------------------------------

    /**
     * Method to intialize all the gui objects
     */
    public void initialization() {
        pendingSelected = new Stack<>();
        runningAction = MovePlayerType.NOTHING;
        resNeededDevelopmentCardSelected = new ArrayList<>();
        resDevCardSelected = new Stack<>();
        buttons_card_space = new ArrayList<>();
        card_spaces = new ArrayList<>();
        //leader card
        leadercards = new Pane[]{leadercard1, leadercard2};
        //development card
        devcardmatrix = new Pane[][]{
                {devcard_00, devcard_01, devcard_02, devcard_03,},
                {devcard_10, devcard_11, devcard_12, devcard_13,},
                {devcard_20, devcard_21, devcard_22, devcard_23,}};
        buttons_card_space.add(button_card_space_0);
        buttons_card_space.add(button_card_space_1);
        buttons_card_space.add(button_card_space_2);
        card_spaces.add(card_space_0);
        card_spaces.add(card_space_1);
        card_spaces.add(card_space_2);
        //market structure
        market_button = new Pane[]{btn_col_0, btn_col_1, btn_col_2, btn_col_3, btn_row_0, btn_row_1, btn_row_2};
        marketBoardObj = new Sphere[][]{{m_00, m_01, m_02, m_03}, {m_10, m_11, m_12, m_13}, {m_20, m_21, m_22, m_23}};
        additionalMarble = m_add;
        //faith structure
        faithArray = new Pane[]{faith_0, faith_1, faith_2, faith_3, faith_4, faith_5, faith_6, faith_7, faith_8, faith_9, faith_10, faith_11, faith_12, faith_13, faith_14, faith_15, faith_16, faith_17, faith_18, faith_19, faith_20, faith_21, faith_22, faith_23, faith_24};
        //warehouse standard
        warehousesStandard = new ArrayList<>();
        warehousesStandard.add(new ArrayList<Pane>() {{
            add(ware_00);
        }});
        warehousesStandard.add(new ArrayList<Pane>() {{
            add(ware_10);
            add(ware_11);
        }});
        warehousesStandard.add(new ArrayList<Pane>() {{
            add(ware_20);
            add(ware_21);
            add(ware_22);
        }});
        //warehouse additional
        warehousesAdditional = new ArrayList<>();
        warehousesAdditional.add(new ArrayList<Pane>() {{
            add(ware_30);
            add(ware_31);
        }});
        warehousesAdditional.add(new ArrayList<Pane>() {{
            add(ware_40);
            add(ware_41);
        }});

        strongbox_panes = new ArrayList<>();
        strongbox_panes.add(stone_strongbox);
        strongbox_panes.add(coin_strongbox);
        strongbox_panes.add(shield_strongbox);
        strongbox_panes.add(servant_strongbox);

        market_pending_panes = new ArrayList<>();
        market_pending_panes.add(coin_pending);
        market_pending_panes.add(stone_pending);
        market_pending_panes.add(shield_pending);
        market_pending_panes.add(servant_pending);


        pope_tiles_list = new ArrayList<>();
        pope_tiles_list.add(pope_tiles_1);
        pope_tiles_list.add(pope_tiles_2);
        pope_tiles_list.add(pope_tiles_3);
        mapMarbles();

        /*try{
            MatchSolo s = (MatchSolo) match;
            lorenzo_pos_faith.setVisible(true);
        }
        catch (Exception e){}*/
    }

    @Override
    public void askToDiscardTwoLeader(int numOfResource, int executePlayerPos) {

    }

    //--------------------------------------PRINT MODEL---------------------------------------------------------------
    /**
     * Method to update the model
     * @param match The {@link Match} to be watched
     * @param playerPosition the position of the {@link Player}
     */
    @Override
    public void updateModel(Match match, int playerPosition) {
        this.match = match;
        activePlayerPos = playerPosition;
        initialization();
        printModel();
    }

    /**
     * Method used to print the model to the gui
     */
    @Override
    public void printModel() {
        if (activePlayerPos == match.getWhoAmI())
            mapMyLeaderCards(activePlayerPos);
        else
            mapOthersLeaderCards(activePlayerPos);
        mapLeaderCardProduction(activePlayerPos);
        //mapMarbles();
        mapWarehouses(activePlayerPos);
        mapDevelopmentCards();
        mapDevelopmentCardsSpaces(activePlayerPos);
        mapStrongBox(activePlayerPos);
        mapMarketResource(activePlayerPos);
        mapProductionResource();
        updateFaith(activePlayerPos);
        mapComboPox();
    }

    /**
     * Method used to map the combo box
     */
    public void mapComboPox() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                players_list_combobox.getItems().setAll(match.getPlayers());
                players_list_combobox.getSelectionModel().select(activePlayerPos);
            }
        });

    }
    /**
     * Method used to map the {@link PopeFavorTiles}
     * @param indexOfPlayer pos of the plauer to be shown
     */
    public void mapPopeTiles(int indexOfPlayer){
        for(int i=0;i<match.getPlayers().get(indexOfPlayer).getPopeFavorTiles().size();i++){
            try{
                if(match.getPlayers().get(indexOfPlayer).getPopeFavorTiles().get(i) == null){
                    pope_tiles_list.get(i).setVisible(false);
                }
                else{
                    pope_tiles_list.get(i).setVisible(true);
                }
            }catch (Exception e){
                pope_tiles_list.get(i).setVisible(false);
            }
        }
    }

    /**
     * Method used to map the strongbox
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapStrongBox(int posPlayer) {
        for (ResourcesCount r : Utils.fromResourcesToResourceCount(match.getPlayers().get(posPlayer).getStrongBox())) {
            switch (r.getType()) {
                case STONE: {
                    stone_strongbox_text.setText(r.getCount() + "x");
                    break;
                }
                case SERVANT: {
                    servant_strongbox_text.setText(r.getCount() + "x");
                    break;
                }
                case COIN: {
                    coin_strongbox_text.setText(r.getCount() + "x");
                    break;
                }
                case SHIELD: {
                    shield_strongbox_text.setText(r.getCount() + "x");
                    break;
                }
            }
        }
    }

    /**
     * Method used to map the market's pending res
     * @param posPlayer pos of the plauer to be shown
     */
    private void mapMarketResource(int posPlayer) {
        if (match.getCurrentPlayer().equals(match.getPlayerFromPosition(posPlayer))) {
            ArrayList<ResourcesCount> res = Utils.fromResourcesToResourceCount(match.getPendingMarketResources());
            coin_pending.setDisable(true);
            stone_pending.setDisable(true);
            servant_pending.setDisable(true);
            shield_pending.setDisable(true);
            for (ResourcesCount r : res) {
                switch (r.getType()) {
                    case COIN: {
                        coin_pending_text.setText(r.getCount() + "x");
                        coin_pending.setDisable(r.getCount() == 0);
                        break;
                    }
                    case STONE: {
                        stone_pending_text.setText(r.getCount() + "x");
                        stone_pending.setDisable(r.getCount() == 0);
                        break;
                    }
                    case SERVANT: {
                        servant_pending_text.setText(r.getCount() + "x");
                        servant_pending.setDisable(r.getCount() == 0);
                        break;
                    }
                    case SHIELD: {
                        shield_pending_text.setText(r.getCount() + "x");
                        shield_pending.setDisable(r.getCount() == 0);
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Method used to map the pending production resources
     */
    private void mapProductionResource() {
        ArrayList<ResourcesCount> res = Utils.fromResourcesToResourceCount(match.getPendingProductionResources());
        for (ResourcesCount r : res) {
            switch (r.getType()) {
                case COIN: {
                    coin_production_pending_text.setText(r.getCount() + "x");
                    break;
                }
                case STONE: {
                    stone_production_pending_text.setText(r.getCount() + "x");
                    break;
                }
                case SERVANT: {
                    servant_production_pending_text.setText(r.getCount() + "x");
                    break;
                }
                case SHIELD: {
                    shield_production_pending_text.setText(r.getCount() + "x");
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * Method used to map the warehouses
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapWarehouses(int posPlayer) {
        mapWarehousesStandard(posPlayer);
        mapWarehousesAdditional(posPlayer);
    }

    /**
     * Method used to map the warehouses standard
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapWarehousesStandard(int posPlayer) {
        unsetAllBackgroundWarehouseStandard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < match.getPlayers().get(posPlayer).getWarehousesStandard().get(i).getResources().size(); j++) {
                changeImage(warehousesStandard.get(i).get(j), Utils.mapResTypeToImage(match.getPlayers().get(posPlayer).getWarehousesStandard().get(i).getResourceType()), "resources/");
            }
        }
    }

    /**
     * Method used to map the warehouses additional
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapWarehousesAdditional(int posPlayer) {
        unsetAllBackgroundWarehouseAdditional();
        for (int i = 0; i < match.getPlayers().get(posPlayer).getWarehousesAdditional().size(); i++) {
            for (int j = 0; j < match.getPlayers().get(posPlayer).getWarehousesAdditional().get(i).getResources().size(); j++) {
                changeImage(warehousesAdditional.get(i).get(j), Utils.mapResTypeToImage(match.getPlayers().get(posPlayer).getWarehousesAdditional().get(i).getResourceType()), "resources/");
            }
        }
        ware_add_1.setVisible(match.getPlayers().get(posPlayer).getWarehousesAdditional().size() > 0);
        ware_add_2.setVisible(match.getPlayers().get(posPlayer).getWarehousesAdditional().size() > 1);
        if (ware_add_1.isVisible()) {
            ware_add_type_1.setText(match.getPlayers().get(posPlayer).getWarehousesAdditional().get(0).getResourceType().toString());
        }
        if (ware_add_2.isVisible()) {
            ware_add_type_2.setText(match.getPlayers().get(posPlayer).getWarehousesAdditional().get(1).getResourceType().toString());
        }
    }

    /**
     * Method used to map the dev cards
     */
    public void mapDevelopmentCards() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    changeImage(devcardmatrix[i][j], match.getDevelopmentCards()[i][j].peek().getImage(), "devcard_leadercard/");
                } catch (Exception e) {
                    devcardmatrix[i][j].setVisible(false);
                }
            }
        }
    }

    /**
     * Method used to map the dev cards spaces
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapDevelopmentCardsSpaces(int posPlayer) {
        for (int i = 0; i < card_spaces.size(); i++) {
            Pane space = card_spaces.get(i);
            space.setBackground(null);
            try {
                changeImage(space, match.getPlayers().get(posPlayer).getDevelopmentCardSpaces().get(i).pickTopCard().getImage(), "devcard_leadercard/");
            } catch (Exception e) {
                space.setDisable(true);
            }
        }
    }

    /**
     * Method used to map the marbles
     */
    public void mapMarbles() {
        m_add.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getAdditionalMarble().getColor())));
        m_00.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0, 0).getColor())));
        m_01.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0, 1).getColor())));
        m_02.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0, 2).getColor())));
        m_03.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0, 3).getColor())));
        m_10.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1, 0).getColor())));
        m_11.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1, 1).getColor())));
        m_12.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1, 2).getColor())));
        m_13.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1, 3).getColor())));
        m_20.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2, 0).getColor())));
        m_21.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2, 1).getColor())));
        m_22.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2, 2).getColor())));
        m_23.setMaterial(new PhongMaterial(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2, 3).getColor())));
    }

    /**
     * Method used to map the leadercards of the player
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapMyLeaderCards(int posPlayer) {
        try {
            changeImage(leadercards[0], match.getPlayers().get(posPlayer).getLeaderCards().get(0).getImage(), "devcard_leadercard/");
            changeImage(leadercards[1], match.getPlayers().get(posPlayer).getLeaderCards().get(1).getImage(), "devcard_leadercard/");
        } catch (Exception exception) {

        }
    }

    /**
     * Method used to map the leadercards of other player
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapOthersLeaderCards(int posPlayer) {
        leadercards[0].setBackground(null);
        leadercards[1].setBackground(null);
        try {
            if (match.getPlayers().get(posPlayer).getLeaderCards().get(0).isActive())
                changeImage(leadercards[0], match.getPlayers().get(posPlayer).getLeaderCards().get(0).getImage(), "devcard_leadercard/");
            if (match.getPlayers().get(posPlayer).getLeaderCards().get(1).isActive())
                changeImage(leadercards[1], match.getPlayers().get(posPlayer).getLeaderCards().get(1).getImage(), "devcard_leadercard/");
        } catch (Exception exception) {

        }
    }

    /**
     * Method used to map the {@link LeaderCard}'s production
     * @param posPlayer pos of the plauer to be shown
     */
    public void mapLeaderCardProduction(int posPlayer) {
        ArrayList<ProductivePower> activePower = match.getPlayerFromPosition(posPlayer).getAddedPower();
        production_leader_1.setVisible(false);
        production_leader_2.setVisible(false);
        if (activePower.size() > 0) {
            production_leader_1.setVisible(true);
            changeImage(production_leader_from_1, Utils.mapResTypeToImage(activePower.get(0).getFrom().get(0).getType()), "leadercard_production");
            if (activePower.size() > 1) {
                production_leader_2.setVisible(true);
                changeImage(production_leader_from_2, Utils.mapResTypeToImage(activePower.get(1).getFrom().get(0).getType()), "leadercard_production");
            }
        }
    }

    //-----------------------------------BUY DEVELOPMENT CARD---------------------------------------------------------
    /**
     * Method used to buy a {@link DevelopmentCard}
     * @param type {@link DevelopmentCardType} of the card
     * @param level {@link DevelopmentCardLevel} of the card
     * @param posToAdd where to add the card
     * @param resourceToUse which {@link Resource} you have to use
     * @param executePlayerPos pos of the plauer to be shown
     */
    @Override
    public void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {
        if (executePlayerPos == match.getWhoAmI()) {
            System.out.println("Correctly received buy development card response");
            changeImage(card_spaces.get(posToAdd), developmentCardSelected.getImage(), "devcard_leadercard/");
            card_spaces.get(posToAdd).setDisable(false);
            hasPerformedUnBlockingAction = true;
            developmentCardSelected = null;
            resNeededDevelopmentCardSelected = new ArrayList<>();
            resDevCardSelected = new Stack<>();
            hasPerformedUnBlockingAction = true;
            runningAction = MovePlayerType.NOTHING;
            disableAllMoves();
        } else {
            runDialog(Alert.AlertType.INFORMATION, match.getPlayerFromPosition(executePlayerPos).getName() + " buy a new development card [" + type.toString() + ", " + level.toString() + "]");
        }
        mapDevelopmentCards();
    }

    /**
     * Click method of dev_card
     * @param event {@link javafx.event.Event} to be handled
     */
    public void dev_card_click(MouseEvent event) {
        Node node = (Node) event.getSource();
        String[] data = ((String) node.getUserData()).split(",");
        int row = Integer.parseInt(data[0]);
        int column = Integer.parseInt(data[1]);

        if (runningAction != MovePlayerType.NOTHING) {
            runDialog(Alert.AlertType.ERROR, "Another action is already running, abort it before performing another one!");
        } else {
            if(!canBePlacedSomewhere(match.getDevelopmentCards()[row][column].peek())){
                runDialog(Alert.AlertType.ERROR,"Error the card you've selected can't be placed anywhere!");
            }
            else if(canBuy(activePlayerPos,match.getDevelopmentCards()[row][column].peek())){
                runDialog(Alert.AlertType.INFORMATION, "Card correctly selected, now you must select from your warehouses :" + match.getDevelopmentCards()[row][column].peek().getCosts(match.getCurrentPlayer()).toString());
                disableAllMoves();
                enableMoves(new ArrayList<MovePlayerType>() {{
                    add(MovePlayerType.MOVE_RESOURCES);
                    add(MovePlayerType.BUY_DEVELOPMENT_CARD);
                }}, false);
                runningAction = MovePlayerType.BUY_DEVELOPMENT_CARD;
                developmentCardSelected = match.getDevelopmentCards()[row][column].peek();
                resNeededDevelopmentCardSelected = Utils.fromResourceCountToResources(developmentCardSelected.getCosts(match.getPlayers().get(match.getWhoAmI())));
                ScaleTransition st = new ScaleTransition(Duration.millis(500), node.getParent());
                st.setByX(0.3f);
                st.setByY(0.3f);
                st.setCycleCount(2);
                st.setAutoReverse(true);
                st.play();
            }
            else{
                runDialog(Alert.AlertType.ERROR,"Error: you can't afford this dev card!");
            }
        }
    }

    /**
     * Method to know if the {@link Player} has enough {@link Resource} to buy the {@link DevelopmentCard}
     * @param indexOfPlayer to identify the {@link Player}
     * @param developmentCard the {@link DevelopmentCard} to check
     * @return true <==> the {@link DevelopmentCard} can be bought by the {@link Player}
     */
    private boolean canBuy(int indexOfPlayer,DevelopmentCard developmentCard){
        Player p = match.getPlayers().get(indexOfPlayer);
        ArrayList<Resource> resNeeded = Utils.fromResourceCountToResources(developmentCard.getCosts(p));
        ArrayList<Resource> resGot = new ArrayList<>();
        for(int i = 0;i<p.getWarehousesStandard().size();i++){
            resGot.addAll(p.getWarehousesStandard().get(i).getResources());
        }
        for(int i = 0;i<p.getWarehousesAdditional().size();i++){
            resGot.addAll(p.getWarehousesAdditional().get(i).getResources());
        }
        resGot.addAll(p.getStrongBox());
        System.out.println(resGot);
        System.out.println(resNeeded);
        return Utils.containsAll(resGot,resNeeded);
    }

    /**
     * Method used to know if a {@link DevelopmentCard} can be placed in some {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace}
     * @param developmentCard the {@link DevelopmentCard} to be placed
     * @return true <==> the {@link DevelopmentCard} can be correctly placed
     */
    private boolean canBePlacedSomewhere(DevelopmentCard developmentCard){
        Player p = match.getPlayers().get(activePlayerPos);
        for(int i = 0;i<p.getDevelopmentCardSpaces().size();i++){
            if(p.developmentCardCanBeAdded(developmentCard, i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Click method of button card space
     * @param event {@link javafx.event.Event} to be handled
     */
    public void button_card_space_clicked(MouseEvent event) {
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int card_space_pos = Integer.parseInt(data);

        switch (runningAction) {
            case BUY_DEVELOPMENT_CARD: {
                if (resNeededDevelopmentCardSelected.size() == 0) {
                    if (match.getPlayers().get(match.getWhoAmI()).developmentCardCanBeAdded(developmentCardSelected, card_space_pos)) {
                        runDialog(Alert.AlertType.INFORMATION, "The card can be correctly placed here!");
                        notify(BuyDevelopmentCardPlayerMove.getInstance(developmentCardSelected.getType(), developmentCardSelected.getLevel(), card_space_pos, new ArrayList<>(resDevCardSelected)));
                    } else {
                        runDialog(Alert.AlertType.ERROR, "You can't place there your card!");
                    }
                } else {
                    runDialog(Alert.AlertType.ERROR, "Before trying to place the card you must pick all the resources needed!");
                }
                break;
            }
            default: {
                runDialog(Alert.AlertType.ERROR, "Check which action you're performing!");
            }
        }
    }

    /**
     * Click method of the resources
     * @param mouseEvent {@link MouseEvent} to be handled
     */
    public void onResourceClick(MouseEvent mouseEvent) {
        Pane warehousePane = (Pane) mouseEvent.getSource();
        int warehousePos = 0;
        ResourceWarehouseType resourceWarehouseType = ResourceWarehouseType.WAREHOUSE;
        if (warehousePane == ware_00) {
            warehousePos = 0;
        } else if (warehousePane == ware_10 || warehousePane == ware_11) {
            warehousePos = 1;
        } else if (warehousePane == ware_20 || warehousePane == ware_21 || warehousePane == ware_22) {
            warehousePos = 2;
        } else if (warehousePane == ware_30 || warehousePane == ware_31) {
            warehousePos = 3;
        } else if (warehousePane == ware_40 || warehousePane == ware_41) {
            warehousePos = 4;
        } else if (warehousePane == stone_strongbox || warehousePane == shield_strongbox || warehousePane == servant_strongbox || warehousePane == coin_strongbox) {
            warehousePos = 0;
            resourceWarehouseType = ResourceWarehouseType.STRONGBOX;
        }
        switch (runningAction) {
            case BUY_DEVELOPMENT_CARD: {
                //select
                Background s = warehousePane.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if (s != null) {
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if (resNeededDevelopmentCardSelected.remove(Resource.getInstance(contained))) {
                        resDevCardSelected.push(ResourcePick.getInstance(resourceWarehouseType, warehousePos, contained));
                        if (resourceWarehouseType == ResourceWarehouseType.WAREHOUSE)
                            warehousePane.setBackground(null);
                        else
                            decrementStrongBoxLabelCount(contained);
                        if (resNeededDevelopmentCardSelected.isEmpty()) {
                            runDialog(Alert.AlertType.INFORMATION, "You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    } else {
                        runDialog(Alert.AlertType.ERROR, contained + " is not required!");
                    }
                } else {
                    runDialog(Alert.AlertType.ERROR, "Nothing selected!");
                }
            }
        }
    }

    //---------------------------------------MARKET INTERACTION-------------------------------------------------------

    /**
     * Manaage the click event on one of the marketBoard arrow
     *
     * @param event {@link MouseEvent} to be handled
     */
    public void clickMarketInteraction(MouseEvent event) {
        //get the pos and if > column dim means that the user click row
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        disableAllMoves();
        if (value > Utils.MARKET_COL_NUMBER - 1) {
            System.out.println("ROW"+(value - Utils.MARKET_COL_NUMBER));
            notify(MarketInteractionPlayerMove.getInstance(MoveType.ROW, value - Utils.MARKET_COL_NUMBER));
        } else {
            notify(MarketInteractionPlayerMove.getInstance(MoveType.COLUMN, value));
            System.out.println("COLUMN"+(value - Utils.MARKET_COL_NUMBER));
        }
        runningAction = MovePlayerType.MARKET_INTERACTION;
        hasPerformedUnBlockingAction = true;
    }

    /**
     * Manage the response to a market move
     *
     * @param moveType {@link MoveType} can be row or column
     * @param pos index of the row or column
     * @param executePlayerPos pos of the {@link Player} who executes the move
     */
    @Override
    public void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos, int num) {
        //BEGIN ANIMATION
        System.out.println(match.getMarketBoard().getAdditionalMarble().toString());
        for(int row = 0;row<3;row++){
            System.out.println(match.getMarketBoard().getRow(row));
        }
        final double duration = 1;
        TranslateTransition temp, t2, t3;
        ParallelTransition parallelTransition = new ParallelTransition();
        ParallelTransition parallelTransitionDouble = new ParallelTransition();
        SequentialTransition sequentialTransition = new SequentialTransition();

        t2 = new TranslateTransition(
                Duration.seconds(duration), additionalMarble
        );
        t3 = new TranslateTransition(
                Duration.seconds(duration), additionalMarble
        );

        if (moveType == MoveType.COLUMN) {
            t2.setByY(210);
            t3.setByX(-1 * (3 - pos) * 80);
            System.out.println(marketBoardObj[0][pos].getLayoutX() - additionalMarble.getLayoutX());
            parallelTransitionDouble.getChildren().add(t2);
            parallelTransitionDouble.getChildren().add(t3);
            sequentialTransition.getChildren().add(parallelTransitionDouble);
            for (int i = 2; i >= 0; i--) {
                temp = new TranslateTransition(
                        Duration.seconds(duration), marketBoardObj[i][pos]
                );
                temp.setByY(-70);
                parallelTransition.getChildren().add(temp);
            }
            temp = new TranslateTransition(
                    Duration.seconds(duration), marketBoardObj[0][pos]
            );
            temp.setByX((3 - pos) * 80);
            sequentialTransition.getChildren().add(parallelTransition);
            sequentialTransition.getChildren().add(temp);
            sequentialTransition.play();
            playSound("market-column");
            slideColumn(pos);
        } else {
            t2.setByY((pos + 1) * 70);
            t3.setByX(80);
            System.out.println(marketBoardObj[0][pos].getLayoutX() - additionalMarble.getLayoutX());
            parallelTransitionDouble.getChildren().add(t2);
            parallelTransitionDouble.getChildren().add(t3);
            sequentialTransition.getChildren().add(parallelTransitionDouble);
            for (int i = 3; i >= 0; i--) {
                temp = new TranslateTransition(
                        Duration.seconds(duration), marketBoardObj[pos][i]
                );
                temp.setByX(-80);
                parallelTransition.getChildren().add(temp);
            }
            temp = new TranslateTransition(
                    Duration.seconds(duration), additionalMarble
            );
            temp.setByX(-80);
            parallelTransition.getChildren().add(temp);
            temp = new TranslateTransition(
                    Duration.seconds(duration), marketBoardObj[pos][0]
            );
            temp.setByX(4 * 80);
            temp.setByY(-1 * (pos + 1) * 70);
            sequentialTransition.getChildren().add(parallelTransition);
            sequentialTransition.getChildren().add(temp);
            sequentialTransition.play();
            playSound("market-row");
            slideRow(pos);
        }

        System.out.println("Res market : "+(match.getPendingMarketResources()));
        System.out.println("Faith : "+match.getCurrentPlayer().getPosFaithMarker());
        mapMarketResource(activePlayerPos);
        updateFaith(activePlayerPos);
        /*updateFaith(activePlayerPos);
        if (executePlayerPos == match.getWhoAmI()) {
            disableAllMoves();
            enableMoves(new ArrayList<MovePlayerType>() {{
                add(MovePlayerType.MOVE_RESOURCES);
            }}, false);
        }
        mapMarketResource(activePlayerPos);
        updateFaith(executePlayerPos);
        disableAllMoves();
        enableMoves(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.MOVE_RESOURCES);
        }}, false);
        if(executePlayerPos == match.getWhoAmI()) {
            if (num == 0) //nothing to convert
            {
                runDialog(Alert.AlertType.INFORMATION, "Action Performed");
            } else {
                Platform.runLater(
                        () -> {
                            ArrayList<Integer> s = new ArrayList<>();
                            for (int p = num; p >= 0; p--) {
                                s.add(p);
                            }
                            ChoiceDialog d = new ChoiceDialog(s.get(0), s);
                            d.setHeaderText("Convert " + num + " white marbles, 1) Convert to > " + match.getCurrentPlayer().getConversionStrategies().get(0) + " 2) Convert to > " + match.getCurrentPlayer().getConversionStrategies().get(1));
                            d.setContentText("How many do you want to convert with 1)");
                            d.showAndWait();
                            int marblesWithFirstStrategy = Integer.valueOf(d.getSelectedItem().toString());
                            int marblesWithSecondStrategy = num - marblesWithFirstStrategy;
                            notify(MarketMarbleConversionMove.getInstance(marblesWithFirstStrategy, marblesWithSecondStrategy));
                            System.out.println(marblesWithFirstStrategy + "-" + marblesWithSecondStrategy);
                        }
                );

            }
        }*/
    }

    @Override
    public void manageResourceMarketConvert(int first, int second, int executePlayerPos) {
        printModel();
        runDialog(Alert.AlertType.INFORMATION, "Conversion performed correctly");
    }

    /**
     * Perform slide animation
     *
     * @param row which row to move
     */
    private void slideRow(int row) {
        Sphere additionalMarbleTemp = additionalMarble;
        this.additionalMarble = marketBoardObj[row][0]; //the marble in the left position of the row will be the next additionalMarble
        marketBoardObj[row][0] = marketBoardObj[row][1]; //slide to left
        marketBoardObj[row][1] = marketBoardObj[row][2]; //slide to left
        marketBoardObj[row][2] = marketBoardObj[row][3]; //slide to left
        marketBoardObj[row][3] = additionalMarbleTemp; //the old additional marble will be the marble in the right position of the row
    }

    /**
     * Perform slide animation
     *
     * @param column which column to move
     */
    private void slideColumn(int column) {
        Sphere additionalMarbleTemp = additionalMarble;
        this.additionalMarble = marketBoardObj[0][column]; //the marble in the top position of the column will be the next additionalMarble
        marketBoardObj[0][column] = marketBoardObj[1][column]; //slide top
        marketBoardObj[1][column] = marketBoardObj[2][column]; //slide top
        marketBoardObj[2][column] = additionalMarbleTemp;
        ;  //the old additional marble will be the marble in the bottom position of the column
    }



    //---------------------------------------RESOURCE POSITIONING-------------------------------------------------------
    private ResourceType resourceTypeDragged;

    /**
     * Method called when pending market resource is dragged
     * @param mouseEvent event to be handled
     */
    public void onPendingMarketResDragDetected(MouseEvent mouseEvent) {
        if (runningAction.equals(MovePlayerType.MARKET_INTERACTION)) {
            Pane pane = (Pane) mouseEvent.getSource();
            Text pending_text = null;
            if (pane == coin_pending) {
                resourceTypeDragged = ResourceType.COIN;
                pending_text = coin_pending_text;
            } else if (pane == shield_pending) {
                resourceTypeDragged = ResourceType.SHIELD;
                pending_text = shield_pending_text;
            } else if (pane == stone_pending) {
                resourceTypeDragged = ResourceType.STONE;
                pending_text = stone_pending_text;
            } else if (pane == servant_pending) {
                resourceTypeDragged = ResourceType.SERVANT;
                pending_text = servant_pending_text;
            }
            int val = Integer.parseInt(pending_text.getText().replaceAll("\\D+", ""));
            if (val > 0) {
                Dragboard db = pane.startDragAndDrop(TransferMode.COPY);
                ClipboardContent cc = new ClipboardContent();
                db.setDragView(pane.snapshot(null, null));
                db.setDragViewOffsetX(mouseEvent.getX());
                db.setDragViewOffsetY(mouseEvent.getY());
                cc.put(marketPendingDragFormat, "marketPending");
                db.setContent(cc);
            }
        }
    }

    /**
     * Method used when the drag event is finished
     * @param dragEvent event to be handled
     */
    public void onWarehousesDragOver(DragEvent dragEvent) {
        Pane sourcePane = (Pane) dragEvent.getGestureSource();
        Pane pane = (Pane) dragEvent.getSource();
        boolean prova = !pane.isDisable();
        if (!pane.isDisable() && !pane.equals(sourcePane) && (market_pending_panes.contains(sourcePane) || warehousesStandard.stream().anyMatch(elem -> elem.contains(sourcePane)) || warehousesAdditional.stream().anyMatch(elem -> elem.contains(sourcePane)))) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
    }

    /**
     * Method used to map index of {@link Warehouse} from gui to model
     * @param warehousePane {@link Pane} object to be mapped
     * @return the index of the {@link Warehouse}
     */
    private int getWarehousePositionFromPane(Pane warehousePane) {
        if (warehousePane == ware_00) {
            return 0;
        } else if (warehousePane == ware_10 || warehousePane == ware_11) {
            return 1;
        } else if (warehousePane == ware_20 || warehousePane == ware_21 || warehousePane == ware_22) {
            return 2;
        } else if (warehousePane == ware_30 || warehousePane == ware_31) {
            return 3;
        } else if (warehousePane == ware_40 || warehousePane == ware_41) {
            return 4;
        }
        return -1;
    }

    /**
     * Method called when the drag is dropped
     * @param dragEvent event to be handled
     */
    public void onWarehousesDragDropped(DragEvent dragEvent) {
        if (dragEvent.isAccepted()) {
            Pane warehousePaneFrom = (Pane) dragEvent.getGestureSource();
            Pane warehousePaneTo = (Pane) dragEvent.getSource();
            int toWarehousePos = getWarehousePositionFromPane(warehousePaneTo);

            switch (runningAction) {
                case NOTHING: { //A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN
                    if (warehousesStandard.stream().anyMatch(elem -> elem.contains(warehousePaneFrom)) || warehousesAdditional.stream().anyMatch(elem -> elem.contains(warehousePaneFrom))) {
                        int fromWarehousePos = getWarehousePositionFromPane(warehousePaneFrom);
                        if (toWarehousePos == fromWarehousePos) {
                            //for the code organization shouldn't enter here but is better to prevent
                            return;
                        }
                        boolean firstIsStandard = true, secondIsStandard = true;
                        //getting the warehouses from the player
                        if (fromWarehousePos == 3 || fromWarehousePos == 4) {
                            firstIsStandard = false;
                        }
                        if (toWarehousePos == 3 || toWarehousePos == 4) {
                            secondIsStandard = false;
                        }
                        Warehouse w1 = match.getPlayerFromPosition(match.getWhoAmI()).getWarehouseFromPosition(fromWarehousePos);
                        Warehouse w2 = match.getPlayerFromPosition(match.getWhoAmI()).getWarehouseFromPosition(toWarehousePos);
                        //A<->A
                        if (!firstIsStandard && !secondIsStandard) {
                            //not possible for the game rule
                            runDialog(Alert.AlertType.ERROR, "Error you must respect the warehousePane's type rule!");
                            break;
                        }
                        //S<->S
                        if (firstIsStandard && secondIsStandard) {
                            if (w1.getSpaceAvailable() + w1.getResources().size() < w2.getResources().size()) {
                                runDialog(Alert.AlertType.ERROR, "swap not possible");
                                return;
                            }
                            if (w2.getSpaceAvailable() + w2.getResources().size() < w1.getResources().size()) {
                                runDialog(Alert.AlertType.ERROR, "swap not possible");
                                return;
                            }
                            notify(MoveResourcesPlayerMove.getInstance(fromWarehousePos, toWarehousePos, 0, 0));
                            disableAllMoves();
                            return;
                        }

                        if (!checkForTypeCorrectness(Utils.getResourceTypeFromUrl(warehousePaneFrom.getBackground().getImages().get(0).getImage().getUrl()), toWarehousePos)) {
                            runDialog(Alert.AlertType.ERROR, "Error you must respect the warehousePane's type rule!");
                            return;
                        }
                        if (warehousePaneTo.getBackground() != null) {
                            runDialog(Alert.AlertType.ERROR, "pane is not empty");
                            return;
                        }
                        //A->S  || S->A
                        if ((!firstIsStandard && secondIsStandard) || (firstIsStandard && !secondIsStandard)) {
                            //send message
                            notify(MoveResourcesPlayerMove.getInstance(fromWarehousePos, toWarehousePos, 1, 0));
                            disableAllMoves();
                        }
                    }
                    break;
                }
                case MARKET_INTERACTION: {
                    //WE'RE INSIDE A POSITIONING
                    if (market_pending_panes.contains(warehousePaneFrom)) {
                        if (!checkForTypeCorrectness(resourceTypeDragged, toWarehousePos)) {
                            runDialog(Alert.AlertType.ERROR, "Error you must respect the warehousePane's type rule!");
                            return;
                        }
                        if (warehousePaneTo.getBackground() == null) {
                            pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, -1, resourceTypeDragged));
                            pendingSelected.peek().setWarehousePosition(toWarehousePos);
                            decrementPendingLabelCount(resourceTypeDragged);
                            changeImage(warehousePaneTo, Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()), "resources/");
                            System.out.println(new ArrayList<>(pendingSelected));
                            if (isLastPositioning()) {
                                runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                                sendPlaceResourceMove();
                            }
                        } else {
                            runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                        }
                    }
                    break;
                }
            }
        }
        dragEvent.setDropCompleted(true);
    }

    /**
     * Method used to manage a correct pending resource positioning
     * @param resourceType {@link ResourceType} associated to the label to be modified
     */
    private void decrementPendingLabelCount(ResourceType resourceType) {
        Text pending_text = null;
        switch (resourceType) {
            case COIN:
                pending_text = coin_pending_text;
                break;
            case SHIELD:
                pending_text = shield_pending_text;
                break;
            case SERVANT:
                pending_text = servant_pending_text;
                break;
            case STONE:
                pending_text = stone_pending_text;
                break;
        }
        int val = Integer.parseInt(pending_text.getText().replaceAll("\\D+", ""));
        pending_text.setText(--val + "x");
    }

    /**
     * Method to know if we are positioning the last pending {@link Resource} of the market
     * @return true <==> we have placed all the {@link Resource}
     */
    private boolean isLastPositioning() {
        return new ArrayList<>(pendingSelected).stream().filter(el -> el.getWarehousePosition() != -1).count() == match.getPendingMarketResources().size();
    }

    /**
     * Method used to notify a {@link PositioningResourcesPlayerMove}
     */
    private void sendPlaceResourceMove() {
        runningAction = MovePlayerType.NOTHING;
        disableAllMoves();
        //map respick to integer
        ArrayList<Integer> whereToBePlaced = new ArrayList<>();
        for (Resource res : match.getPendingMarketResources())
            for (int i = 0; i < pendingSelected.size(); i++) {
                if (res.getType() == pendingSelected.get(i).getResourceType()) {
                    whereToBePlaced.add(pendingSelected.get(i).getWarehousePosition());
                    System.out.println("Place a " + res.getType() + " in : " + pendingSelected.get(i).getWarehousePosition());
                    pendingSelected.remove(i);
                    break;
                }
            }
        notify(PositioningResourcesPlayerMove.getInstance(whereToBePlaced));
        pendingSelected = new Stack<>();
    }

    /**
     * Method called when a {@link it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesResponse} is received
     * @param whereToPlace {@link ArrayList} representing for each resource where to place it
     * @param executePlayerPos who is performing the action
     */
    @Override
    public void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos) {
        //updateFaith(match.getWhoAmI());
        if (activePlayerPos == executePlayerPos) {
            mapWarehouses(activePlayerPos);
            if (executePlayerPos != match.getWhoAmI())
                runDialog(Alert.AlertType.INFORMATION, match.getPlayerFromPosition(executePlayerPos).getName() + " placed all the resources");

        }
    }

    //----------------------------------------END TURN----------------------------------------------------------------
    /**
     * Method called to end a turn
     */
    public void endTurn() {
        System.out.println(match.getPendingMarketResources());
        if (!match.getPendingMarketResources().isEmpty()) {
            runDialog(Alert.AlertType.ERROR, "You must place all the resources before ending your round");
        } else if (!hasPerformedUnBlockingAction) {
            runDialog(Alert.AlertType.ERROR, "You have to execute a blocking action ");
        } else {
            System.out.println(hasPerformedUnBlockingAction);
            hasPerformedUnBlockingAction = false;
            notify(EndRoundPlayerMove.getInstance());
            disableAllMoves();
        }
    }

    /**
     * Method called when a {@link EndRoundPlayerMove} is received
     * @param correctlyEnded true <==> everything went well
     * @param executePlayerPos who is perfroming the action
     * @param message {@link String} representing a message
     */
    @Override
    public void manageEndTurn(boolean correctlyEnded, int executePlayerPos, String message) {
        System.out.println("manage end turn");
        try {
            MatchSolo temp = (MatchSolo) match;
            lorenzo_pos_faith.setVisible(true);
            lorenzo_pos_faith.setText("LORENZO POS FAITH: " + temp.getPosBlackCross());
        } catch (Exception e) {
            lorenzo_pos_faith.setVisible(false);
        }
        printModel();
        if (message.isEmpty()) {
            if (executePlayerPos != match.getWhoAmI()) {
                runDialog(Alert.AlertType.INFORMATION, match.getPlayerFromPosition(executePlayerPos).getName() + " finish his turn");
            }
        } else {
            runDialog(Alert.AlertType.INFORMATION, message);
        }
    }

    //------------------------------------ENABLE LEADER CARD---------------------------------------------------------

    /**
     * Method used to notify a {@link EnableLeaderCardPlayerMove}
     * @param mouseEvent event to be handled
     */
    public void enableCardClick(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        Player player = match.getPlayers().get(match.getWhoAmI());
        LeaderCard leaderCard = player.getLeaderCard(value);
        if (!leaderCard.isActive()) {
            if (!player.getLeaderCard(value).isActionable(player)) {
                runDialog(Alert.AlertType.CONFIRMATION, "You can't activate this leaderCard");
            } else {
                runningAction = MovePlayerType.ENABLE_LEADER_CARD;
                notify(EnableLeaderCardPlayerMove.getInstance(value));
            }
        }
    }

    /**
     * Method used to show a ledaer card
     * @param leaderCardPosition pos of the {@link LeaderCard} to be flipped
     * @param executePlayerPos who is performing the action
     */
    @Override
    public void flipLeaderCard(int leaderCardPosition, int executePlayerPos) {
        if (executePlayerPos == match.getWhoAmI()) {
            runningAction = MovePlayerType.NOTHING;
            runDialog(Alert.AlertType.CONFIRMATION, "You successfully activate the leader card");
        } else {
            runDialog(Alert.AlertType.INFORMATION, match.getPlayerFromPosition(executePlayerPos).getName() + " successfully activate a leader card");
        }

        mapLeaderCardProduction(activePlayerPos);
        mapWarehousesAdditional(activePlayerPos);

        //todo:depending on the type do something (map discount and map conversion strategy)
    }

    //------------------------------------DISCARD LEADER CARD---------------------------------------------------------
    /**
     * Method called when {@link LeaderCard} drag event occurs
     * @param mouseEvent event to be handled
     */
    public void onLeaderCardDragDetected(MouseEvent mouseEvent) {
        if (runningAction.equals(MovePlayerType.NOTHING)) {
            Pane pane = (Pane) mouseEvent.getSource();
            String data = (String) pane.getUserData();
            int value = Integer.parseInt(data);
            if (!match.getPlayerFromPosition(match.getWhoAmI()).getLeaderCard(value).isActive()) {
                Dragboard db = pane.startDragAndDrop(TransferMode.COPY);
                ClipboardContent cc = new ClipboardContent();
                db.setDragView(pane.snapshot(null, null));
                db.setDragViewOffsetX(mouseEvent.getX());
                db.setDragViewOffsetY(mouseEvent.getY());
                cc.put(leaderCardDragFormat, "prova");
                db.setContent(cc);
            }
        }
    }

    /**
     * Method called when a LeaderCard is discarded
     * @param leaderCardPosition pos of the {@link LeaderCard}
     * @param executePlayerPos who is performing the action
     */
    @Override
    public void discardLeaderCard(int leaderCardPosition, int executePlayerPos) {
        if (executePlayerPos == match.getWhoAmI()) {
            runDialog(Alert.AlertType.INFORMATION, "Leader card discarded correctly");
        } else {
            runDialog(Alert.AlertType.INFORMATION, match.getPlayerFromPosition(executePlayerPos).getName() + " DISCARDED A LEADER CARD");
        }
    }

    //---------------------------------------PRODUCTION-------------------------------------------------------------

    /**
     * Click on change button on production
     * @param mouseEvent event to be handled
     */
    public void onClickChangeProductionTo(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Pane pane = null;
        if (button.equals(changeProductionBaseTo)) {
            pane = production_base_to;
        } else if (button.equals(changeProductionLeader1)) {
            pane = production_leader_to_1;
        } else if (button.equals(changeProductionLeader2)) {
            pane = production_leader_to_2;
        }
        int posResourceVisibleProduction = Integer.parseInt(pane.getUserData().toString()) + 1;
        if (posResourceVisibleProduction > 3) {
            posResourceVisibleProduction = 0;
        }
        pane.setUserData(posResourceVisibleProduction);
        changeImage(pane, Utils.mapResTypeToImage(ResourceType.values()[posResourceVisibleProduction]), "resources/");
    }

    /**
     * Method used to activate a {@link ProductivePower}
     */
    public void activateProduction() {
        if (activeProduction.equals(ProductionType.BASE)) {
            if (production_base_from_1.getBackground() != null && production_base_from_2.getBackground() != null && production_base_to.getBackground() != null) {
                int posResourceVisibleProduction = Integer.parseInt(production_base_to.getUserData().toString());
                ArrayList<ResourcePick> res = new ArrayList<>();
                pendingSelected.stream().forEach(elem -> res.add(elem));
                notify(EnableProductionPlayerMoveBase.getInstance(res, ResourceType.values()[posResourceVisibleProduction]));
                incrementProductionPendingLabelCount(ResourceType.values()[posResourceVisibleProduction]);
                //reset
                pendingSelected = new Stack<>();
                production_base_from_1.setBackground(null);
                production_base_from_2.setBackground(null);
                production_base_to.setBackground(null);
                production_base_to.setUserData(-1);
                runningAction = MovePlayerType.NOTHING;
                hasPerformedUnBlockingAction = true;
            }
        } else if (activeProduction.equals(ProductionType.LEADER_CARD)) {
            if ((production_leader_to_1.getBackground() != null && activeProductionIndex == 0)
                    || (production_leader_to_2.getBackground() != null && activeProductionIndex == 1)) {
                int posResourceVisibleProduction;
                if (activeProductionIndex == 0) {
                    posResourceVisibleProduction = Integer.parseInt(production_leader_to_1.getUserData().toString());
                } else {
                    posResourceVisibleProduction = Integer.parseInt(production_leader_to_2.getUserData().toString());
                }
                ArrayList<ResourcePick> res = new ArrayList<>();
                pendingSelected.stream().forEach(elem -> res.add(elem));
                notify(EnableProductionPlayerMoveLeaderCard.getInstance(res, activeProductionIndex, ResourceType.values()[posResourceVisibleProduction]));
                incrementProductionPendingLabelCount(ResourceType.values()[posResourceVisibleProduction]);
                //reset
                pendingSelected = new Stack<>();
                production_leader_to_1.setBackground(null);
                production_leader_to_1.setUserData(-1);
                production_leader_to_2.setBackground(null);
                production_leader_to_2.setUserData(-1);
                runningAction = MovePlayerType.NOTHING;
                hasPerformedUnBlockingAction = true;
            }
        }

    }

    /**
     * Method called when a Production pending {@link Resource} is dragged
     * @param mouseEvent event to be handled
     */
    public void onProductionResDragDetected(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        if (runningAction.equals(MovePlayerType.NOTHING) || runningAction.equals(MovePlayerType.ENABLE_PRODUCTION)) {
            Dragboard db = pane.startDragAndDrop(TransferMode.COPY);
            ClipboardContent cc = new ClipboardContent();
            db.setDragView(pane.snapshot(null, null));
            db.setDragViewOffsetX(mouseEvent.getX());
            db.setDragViewOffsetY(mouseEvent.getY());
            cc.put(resourceDragFormat, "prova");
            db.setContent(cc);
            posWareHouseProduction = -1;
            if (pane == ware_00) {
                posWareHouseProduction = 0;
                resourceTypeProduction = match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(0).getResourceType();
            } else if (pane == ware_10 || pane == ware_11) {
                posWareHouseProduction = 1;
                resourceTypeProduction = match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(1).getResourceType();
            } else if (pane == ware_20 || pane == ware_21 || pane == ware_22) {
                posWareHouseProduction = 2;
                resourceTypeProduction = match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(2).getResourceType();
            } else if (pane == ware_30 || pane == ware_31) {
                posWareHouseProduction = 3;
                resourceTypeProduction = match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().get(0).getResourceType();
            } else if (pane == ware_40 || pane == ware_41) {
                posWareHouseProduction = 4;
                resourceTypeProduction = match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().get(1).getResourceType();
            }
            if (posWareHouseProduction != -1) { // it's a warehouse
                wareHousePaneFromProduction = pane;
                resourceWarehouseTypeProduction = ResourceWarehouseType.WAREHOUSE;
            } else { //it's a strongbox
                resourceWarehouseTypeProduction = ResourceWarehouseType.STRONGBOX;
                if (pane == stone_strongbox) {
                    resourceTypeProduction = ResourceType.STONE;
                } else if (pane == coin_strongbox) {
                    resourceTypeProduction = ResourceType.COIN;
                } else if (pane == servant_strongbox) {
                    resourceTypeProduction = ResourceType.SERVANT;
                } else if (pane == shield_strongbox) {
                    resourceTypeProduction = ResourceType.SHIELD;
                }
            }
        }

    }

    /**
     * Method called when the drag is over
     * @param dragEvent event to be handled
     */
    public void onProductionDragOver(DragEvent dragEvent) {
        Pane sourcePane = (Pane) dragEvent.getGestureSource();
        Pane pane = (Pane) dragEvent.getSource();
        if (!pane.isDisable() && (warehousesStandard.stream().anyMatch(elem -> elem.contains(sourcePane)) || warehousesAdditional.stream().anyMatch(elem -> elem.contains(sourcePane)) || strongbox_panes.contains(sourcePane))) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
    }

    /**
     * Method called when the drag is dropped
     * @param dragEvent event to be handled
     */
    public void onProductionDragDropped(DragEvent dragEvent) {
        if (dragEvent.isAccepted()) {
            Pane pane = (Pane) dragEvent.getSource();
            runningAction = MovePlayerType.ENABLE_PRODUCTION;
            if ((production_base_from_1.equals(pane) || production_base_from_2.equals(pane)) && isProductionPossible(ProductionType.BASE)) {
                manageProductionBaseAddResource(pane);
            } else if (card_space_0.equals(pane) && isProductionPossible(ProductionType.DEVELOPMENT_CARD, 0)) {
                manageProductionDevelopmentCardAddResource(0);
            } else if (card_space_1.equals(pane) && isProductionPossible(ProductionType.DEVELOPMENT_CARD, 1)) {
                manageProductionDevelopmentCardAddResource(1);
            } else if (card_space_2.equals(pane) && isProductionPossible(ProductionType.DEVELOPMENT_CARD, 2)) {
                manageProductionDevelopmentCardAddResource(2);
            } else if (production_leader_from_1.equals(pane) && isProductionPossible(ProductionType.LEADER_CARD, 0)) {
                manageProductionLeaderAddResource(0, pane);
            } else if (production_leader_from_2.equals(pane) && isProductionPossible(ProductionType.LEADER_CARD, 1)) {
                manageProductionLeaderAddResource(1, pane);
            } else {
                runningAction = MovePlayerType.NOTHING;
            }
        }
        dragEvent.setDropCompleted(true);
    }

    /**
     * Method to check if the production is possible
     * @param type {@link ProductionType} of the production to be checked
     * @return true <--> the production is possible
     */
    private boolean isProductionPossible(ProductionType type) {
        return activeProduction.equals(ProductionType.NOTHING) || activeProduction.equals(type);
    }

    /**
     * Method to check if the production is possible
     * @param type {@link ProductionType} of the production to be checked
     * @param index the index of the production
     * @return true <--> the production is possible
     */
    private boolean isProductionPossible(ProductionType type, int index) {
        return activeProduction.equals(ProductionType.NOTHING) || (activeProduction.equals(type) && activeProductionIndex == index);
    }

    /**
     * Method to manage production
     * @param pane {@link Pane} to mangage
     */
    private void manageProductionBaseAddResource(Pane pane) {
        if (pane.getBackground() == null) {
            activeProduction = ProductionType.BASE;
            pendingSelected.push(ResourcePick.getInstance(resourceWarehouseTypeProduction, posWareHouseProduction, resourceTypeProduction));
            if (resourceWarehouseTypeProduction == ResourceWarehouseType.WAREHOUSE)
                wareHousePaneFromProduction.setBackground(null);
            else
                decrementStrongBoxLabelCount(resourceTypeProduction);
            changeImage(pane, Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()), "resources/");
        }
    }

    /**
     * Method used when a {@link Resource} is added to a {@link DevelopmentCard}'s production
     * @param pos pos of the production
     */
    private void manageProductionDevelopmentCardAddResource(int pos) {
        if (activeProductivePowerCost == null || activeProductivePowerCost.size() == 0) {
            activeProductivePowerCost = Utils.fromResourceCountToResources(match.getPlayerFromPosition(match.getWhoAmI()).getDevelopmentCardSpaces().get(pos).pickTopCard().getPowers().getFrom());
        }
        if (activeProductivePowerCost.remove(Resource.getInstance(resourceTypeProduction))) {
            activeProduction = ProductionType.DEVELOPMENT_CARD;
            activeProductionIndex = pos;
            pendingSelected.push(ResourcePick.getInstance(resourceWarehouseTypeProduction, posWareHouseProduction, resourceTypeProduction));
            if (resourceWarehouseTypeProduction == ResourceWarehouseType.WAREHOUSE)
                wareHousePaneFromProduction.setBackground(null);
            else
                decrementStrongBoxLabelCount(resourceTypeProduction);
            if (activeProductivePowerCost.size() == 0) {
                ArrayList<ResourcePick> res = new ArrayList<>();
                pendingSelected.stream().forEach(elem -> res.add(elem));
                notify(EnableProductionPlayerMoveDevelopmentCard.getInstance(res, pos));
                ArrayList<Resource> to = match.getPlayerFromPosition(match.getWhoAmI()).getDevelopmentCardSpaces().get(pos).pickTopCard().getPowers().getTo();
                to.stream().forEach(elem -> incrementProductionPendingLabelCount(elem.getType()));
                runDialog(Alert.AlertType.INFORMATION, "You've correctly picked all the resources, Production activated");
                pendingSelected = new Stack<>();
                activeProduction = ProductionType.NOTHING;
                activeProductivePowerCost = null;
                runningAction = MovePlayerType.NOTHING;
                hasPerformedUnBlockingAction = true;
                //disableAllMoves();
            } else {
                runDialog(Alert.AlertType.INFORMATION, "You've correctly picked a required resource, pick the others");
            }
        } else {
            runDialog(Alert.AlertType.ERROR, resourceTypeProduction + " is not required!");
        }
    }

    /**
     * Method used when a {@link Resource} is added to a {@link DevelopmentCard}'s production
     * @param pos pos of the production
     * @param pane {@link Pane} used to identify which {@link ResourceType} is selected
     */
    private void manageProductionLeaderAddResource(int pos, Pane pane) {
        if (!activeProduction.equals(ProductionType.LEADER_CARD)) {
            activeProduction = ProductionType.LEADER_CARD;
            activeProductionIndex = pos;

            if (Utils.getResourceTypeFromUrl(pane.getBackground().getImages().get(0).getImage().getUrl()).equals(resourceTypeProduction)) {
                pendingSelected.push(ResourcePick.getInstance(resourceWarehouseTypeProduction, posWareHouseProduction, resourceTypeProduction));
                if (resourceWarehouseTypeProduction == ResourceWarehouseType.WAREHOUSE)
                    wareHousePaneFromProduction.setBackground(null);
                else
                    decrementStrongBoxLabelCount(resourceTypeProduction);
                runDialog(Alert.AlertType.CONFIRMATION, "You've Picked the correct resource");
            } else {
                runDialog(Alert.AlertType.ERROR, "place the correct Resource to active this production");
            }
        }
    }

    /**
     * Method used to enable a production
     * @param power the {@link ProductivePower} to be activated
     * @param resourceToUse which {@link Resource} we must use
     * @param executePlayerPos who is performing the action
     */
    @Override
    public void enableProduction(ProductivePower power, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {
        updateFaith(activePlayerPos);
    }

    //--------------------------------------SUPPORT METHODS---------------------------------------------------------
    /**
     * Method called when a production is activated to modify procution's labels
     * @param resourceType {@link ResourceType} used to identify the label
     */
    private void incrementProductionPendingLabelCount(ResourceType resourceType) {
        Text pending_text = null;
        switch (resourceType) {
            case COIN:
                pending_text = coin_production_pending_text;
                break;
            case SHIELD:
                pending_text = shield_production_pending_text;
                break;
            case SERVANT:
                pending_text = servant_production_pending_text;
                break;
            case STONE:
                pending_text = stone_production_pending_text;
                break;
        }
        int val = Integer.parseInt(pending_text.getText().replaceAll("\\D+", ""));
        pending_text.setText(++val + "x");
    }

    /**
     * Method called when something is took from the STRONGBOX
     * @param resourceType {@link ResourceType} which has been withdrawn
     */
    private void decrementStrongBoxLabelCount(ResourceType resourceType) {
        Text pending_text = null;
        switch (resourceType) {
            case COIN:
                pending_text = coin_strongbox_text;
                break;
            case SHIELD:
                pending_text = shield_strongbox_text;
                break;
            case SERVANT:
                pending_text = servant_strongbox_text;
                break;
            case STONE:
                pending_text = stone_strongbox_text;
                break;
        }
        int val = Integer.parseInt(pending_text.getText().replaceAll("\\D+", ""));
        pending_text.setText(--val + "x");
    }

    /**
     * Method used to completely block the view
     */
    @Override
    public void blockView() {
        tabpane.setDisable(true);
    }

    /**
     * Method used to move the {@link Player} ahed in the faith's path
     * @param index how many passes we must do
     */
    private void updateFaith(int index) {
        faith_player.setLayoutX(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX() + 10);
        faith_player.setLayoutY(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY() + 10);
    }

    /**
     * Method called when something has been dragged to the bin
     * @param dragEvent event to be handled
     */
    public void onBinDragOver(DragEvent dragEvent) {
        Pane sourcePane = (Pane) dragEvent.getGestureSource();
        Pane pane = (Pane) dragEvent.getSource();
        if (!pane.isDisable() && (market_pending_panes.contains(sourcePane) || leadercards[0].equals(sourcePane) || leadercards[1].equals(sourcePane))) {
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
    }

    /**
     * Method called when something has been dropped into the bin
     * @param dragEvent event to be handled
     */
    public void onBinDragDropped(DragEvent dragEvent) {
        if (dragEvent.isAccepted()) {
            Pane sourcePane = (Pane) dragEvent.getGestureSource();
            Pane pane = (Pane) dragEvent.getSource();
            if (pane == bin) { // discard resources
                if (runningAction.equals(MovePlayerType.MARKET_INTERACTION) && market_pending_panes.contains(sourcePane)) { // discard resources
                    pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, -1, resourceTypeDragged));
                    pendingSelected.peek().setWarehousePosition(6); //6 = DISCARD
                    decrementPendingLabelCount(resourceTypeDragged);
                    System.out.println(new ArrayList<>(pendingSelected));
                    if (isLastPositioning()) {
                        runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                        sendPlaceResourceMove();
                    }
                } else { // discard leader card
                    Pane pane1 = (Pane) dragEvent.getGestureSource();
                    if (pane1 == leadercard1 || pane1 == leadercard2) {
                        String data = pane1.getUserData().toString();
                        int value = Integer.parseInt(data);
                        disableAllMoves();
                        notify(DiscardLeaderCardPlayerMove.getInstance(value));
                        if (value == 0) { // the leaderCard2 become the first
                            leadercard2.setUserData("0");
                        }
                        pane1.setVisible(false);
                    }
                }
                dragEvent.setDropCompleted(true);
                return;
            }
        }
    }

    /**
     * Method used to play a sound
     * @param sound {@link String} of the path of the sound
     */
    public void playSound(String sound) {
        /*URL resource = null;
        try {
            resource = new File("src/main/resources/audio/" + sound + ".wav").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MediaPlayer a = new MediaPlayer(new Media(resource.toString()));
        a.play();*/
    }

    //------------------------------------OTHERS----------------------------------------------------------------------

    /**
     * Manage combo box action
     */
    public void onComboBoxAction(){
        activePlayerPos =  Math.max(0,players_list_combobox.getSelectionModel().getSelectedIndex());
    }


    /**
     * Method used when a action has to be aborted
     */
    public void abortAction() {
        revertAction();
        runningAction = MovePlayerType.NOTHING;
        Platform.runLater(() -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Action correctly aborted", ButtonType.OK);
            dialog.show();
        });
    }

    /**
     * Revert the result of the action
     */
    private void revertAction() {
        switch (runningAction) {
            case MARKET_INTERACTION: {
                //POSITIONING RESOURCE ==> GIVE BACK REOSURCE TO PENDING MARKET
                for (ResourcesCount r : Utils.fromResourcesToResourceCount(Utils.fromResourcePickToResources(new ArrayList<>(pendingSelected)))) {
                    switch (r.getType()) {
                        case COIN: {
                            int n = Integer.parseInt(coin_pending_text.getText().replaceAll("\\D+", "")) + r.getCount();
                            coin_pending_text.setText(n + "x");
                            break;
                        }
                        case STONE: {
                            int n = Integer.parseInt(stone_pending_text.getText().replaceAll("\\D+", "")) + r.getCount();
                            stone_pending_text.setText(n + "x");
                            break;
                        }
                        case SHIELD: {
                            int n = Integer.parseInt(shield_pending_text.getText().replaceAll("\\D+", "")) + r.getCount();
                            shield_pending_text.setText(n + "x");
                            break;
                        }
                        case SERVANT: {
                            int n = Integer.parseInt(servant_pending_text.getText().replaceAll("\\D+", "")) + r.getCount();
                            servant_pending_text.setText(n + "x");
                            break;
                        }
                    }
                }
                pendingSelected = new Stack<>();
                mapWarehouses(activePlayerPos);
                break;
            }
            case BUY_DEVELOPMENT_CARD: {
                developmentCardSelected = null;
                resNeededDevelopmentCardSelected = new ArrayList<>();
                resDevCardSelected = new Stack<>();
                mapWarehouses(activePlayerPos);
                break;
            }

        }
    }


    //-------------------------------ON CLICK METHODS-----------------------

    /**
     * Method used to check if the drag-swap respects the Warehouse's rules
     * @param resourceType {@link ResourceType} to be dragged-swapped
     * @param where pos of the {@link Warehouse}
     * @return true <==> the warehouses' rule is respected
     */
    private boolean checkForTypeCorrectness(ResourceType resourceType, int where) {
        //check 3 warehouses rule
        if (where >= 3) {// warehouseAdditional
            return match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().get(where - 3).getResourceType().equals(resourceType);
        }
        for (int i = 0; i < warehousesStandard.size(); i++) {
            for (int j = 0; j < warehousesStandard.get(i).size(); j++) {
                try {
                    ResourceType res = Utils.getResourceTypeFromUrl(warehousesStandard.get(i).get(j).getBackground().getImages().get(0).getImage().getUrl());
                    if (res == resourceType && where != i) {
                        return false;
                    }
                } catch (Exception e) {
                    //è vuoto
                }
            }
        }
        //check warehouse correctness
        for (int j = 0; j < warehousesStandard.get(where).size(); j++) {
            try {
                ResourceType res = Utils.getResourceTypeFromUrl(warehousesStandard.get(where).get(j).getBackground().getImages().get(0).getImage().getUrl());
                if (res != resourceType) {
                    //trying to add to warehouse with different types
                    return false;
                }
            } catch (Exception e) {
                //è vuoto
            }
        }
        return true;
    }

    /**
     * Set all the GUI warehouses standard to null
     */
    private void unsetAllBackgroundWarehouseStandard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < warehousesStandard.get(i).size(); j++) {
                warehousesStandard.get(i).get(j).setBackground(null);
            }
        }
    }

    /**
     * Set all the GUI warehouses additional to null
     */
    private void unsetAllBackgroundWarehouseAdditional() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < warehousesAdditional.get(i).size(); j++) {
                warehousesAdditional.get(i).get(j).setBackground(null);
            }
        }
    }

    /**
     * Method used to change the view
     * @param fxml where is placed the fxml
     * @param clientConnectionView {@link ClientConnectionView}
     * @throws IOException thrown if any error related to the fxml occurs
     */
    @Override
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        super.changeView(fxml, clientConnectionView);
    }

    @Override
    public void printMessage(String message) {

    }


    /**
     * Method to manage all the possible moves
     * @param possibleMove {@link ArrayList} of the {@link MovePlayerType}
     */
    @Override
    public void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove) {
        disableAllMoves();
        enableMoves(possibleMove, false);
        updateFaith(activePlayerPos);
    }

    /**
     * Manage end match
     */
    @Override
    public void manageEndMatch() {
        System.out.println("Match finished!");
        Platform.runLater(() -> {
            String msg = "";
            if(match instanceof MatchSolo){
                if(((MatchSolo) match).hasLose()){
                    msg = "Lorenzo won: your points ";
                    msg = msg + match.whoIsWinner().get(0).getPoints();
                }
                else{
                    msg = "You won: your points ";
                    msg = msg + match.whoIsWinner().get(0).getPoints();
                }
            }
            else{
                msg = "Winner are: " + match.whoIsWinner().toString();
            }
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Match ended \n"+msg, ButtonType.OK);
            Optional<ButtonType> result = dialog.showAndWait();
            result.ifPresent(res->{
                Platform.exit();
            });
        });
    }

    @Override
    public void manageReconnection(String playerName) {

    }


    @Override
    public void askForData(String message, int executePlayerPos) {

    }

    /**
     * Method called when a {@link it.polimi.ingsw.controller.move.moveResources.MoveResourcesResponse} is received
     * @param num_from_first how many {@link Resource} from the first
     * @param num_from_second how many {@link Resource} from the second
     * @param indexFirstWarehouse index of the first {@link Warehouse}
     * @param indexSecondWarehouse index of the second {@link Warehouse}
     */
    @Override
    public void moveResourceResponse(int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse) {
        //runDialog(Alert.AlertType.CONFIRMATION, "Move resources success");
        mapWarehouses(activePlayerPos);
    }


    @Override
    public void manageDisconnection(String playerName) {

    }


    //--------------------------------------DISABLE/ENABLE METHODS-------------------------------------------------
    /**
     * Method used to disable all the moves
     */
    public void disableAllMoves() {
        enableMoves(new ArrayList<>(Arrays.asList(MovePlayerType.values())), true);
    }

    /**
     * Method used to enable all the moves
     */
    public void enableAllMoves() {
        enableMoves(new ArrayList<>(Arrays.asList(MovePlayerType.values())), false);
    }

    /**
     * Method used to enable-disable all the moves contained in the parameter
     * @param moves {@link ArrayList} of {@link MovePlayerType} to be enabled-disabled
     * @param disable true <==> enable, false <==> disable
     */
    public void enableMoves(ArrayList<MovePlayerType> moves, boolean disable) {
        if (disable) {
            System.out.println("disable" + moves.toString());
        } else {
            System.out.println("enable" + moves.toString());
        }
        if (moves.contains(MovePlayerType.MARKET_INTERACTION)) {
            disableMarketMatrix(disable);
            disableMarketPendingRes(disable);
        }
        if (moves.contains(MovePlayerType.BUY_DEVELOPMENT_CARD)) {
            disableDevelopmentCardsMatrix(disable);
        }
        if (moves.contains(MovePlayerType.MOVE_RESOURCES)) {

        }
        if (moves.contains(MovePlayerType.ENABLE_LEADER_CARD) || moves.contains(MovePlayerType.DISCARD_LEADER_CARD)) {
            disableLeaderCard(disable);
        }
        if (moves.contains(MovePlayerType.ENABLE_PRODUCTION)) {
            disableBaseProduction(disable);
            disableDevelopmentCardsSpace(disable);
        }
    }

    /**
     * Method used to disable-enable all the market's butons
     * @param disable true <==> enable, false <==> disable
     */
    private void disableMarketMatrix(boolean disable) {
        for (Pane p : market_button) {
            p.setDisable(disable);
        }
    }

    private void disableMarketPendingRes(boolean disable) {
        /*coin_pending.setDisable(disable);
        stone_pending.setDisable(disable);
        servant_pending.setDisable(disable);
        shield_pending.setDisable(disable);*/
    }

    /**
     * Method used to enable-disable all the development cards
     * @param disable disable true <==> enable, false <==> disable
     */
    private void disableDevelopmentCardsMatrix(boolean disable) {
        Arrays.stream(devcardmatrix).forEach(elem -> Arrays.stream(elem).forEach(x -> x.setDisable(disable)));
        for (Pane pane : buttons_card_space) {
            pane.setDisable(disable);
        }
    }

    /**
     * Method used to enable-disable all the development cards' space
     * @param disable true <==> enable, false <==> disable
     */
    private void disableDevelopmentCardsSpace(boolean disable) {
        card_space_0.setDisable(disable || card_space_0.getBackground() == null);
        card_space_1.setDisable(disable || card_space_1.getBackground() == null);
        card_space_2.setDisable(disable || card_space_2.getBackground() == null);
    }

    /**
     * Method used to disable-enable all the {@link LeaderCard}
     * @param disable true <==> enable, false <==> disable
     */
    private void disableLeaderCard(boolean disable) {
        for (Pane pane : leadercards) {
            pane.setDisable(disable);
        }
    }

    /**
     * Method used to disable all the base prodction
     * @param disable true <==> enable, false <==> disable
     */
    private void disableBaseProduction(boolean disable) {
        production_base_from_1.setDisable(disable);
        production_base_from_2.setDisable(disable);
        production_leader_from_1.setDisable(disable);
        production_leader_from_2.setDisable(disable);
        changeProductionBaseTo.setDisable(disable);
        changeProductionLeader1.setDisable(disable);
        changeProductionLeader2.setDisable(disable);
    }

}
