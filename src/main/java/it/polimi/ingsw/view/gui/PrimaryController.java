package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.ClientConnectionView;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.DiscardLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.DiscardTwoLeaderCardsPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.EnableLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.*;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesPlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductivePower;
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
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PrimaryController extends GenericController {

    private Stack<ResourcePick> pendingSelected;
    private MovePlayerType runningAction;
    private boolean hasPerformedUnBlockingAction = false;
    private DevelopmentCard developmentCardSelected;
    private ArrayList<Resource> resNeededDevelopmentCardSelected;
    private Stack<ResourcePick> resDevCardSelected;
    private DataFormat leaderCardDragFormat = new DataFormat("leaderCard");
    private DataFormat marketPendingDragFormat = new DataFormat("marketPending");

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
    private TextFlow msgBox;
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

    @Override
    public void update(MoveResponse message) {
        //elaborate message
        if (match != null) {
            message.updateLocalMatch(match);
        }
        message.elaborateGUI(this);
    }

    //--------------------------------------Initialization-----------------------------------------------------------

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
    }

    @Override
    public void askToDiscardTwoLeader(int numOfResource, int executePlayerPos) {
        //Todo:to Modify
        notify(DiscardTwoLeaderCardsPlayerMove.getInstance(0, 1, ResourceType.COIN, ResourceType.FAITH));
         /*if(myController instanceof PrimaryController)
        {
            //print discard
            String [] indexes = {"1","2","3","4"};
            ChoiceDialog<String> dialog = new ChoiceDialog<>(indexes[0], indexes);
            dialog.setHeaderText("Discard leader card");
            dialog.setTitle("Choose");
            dialog.setContentText("Discard #:");
            Optional<String> choice = dialog.showAndWait();
            //TODO: manage response
            notify(DiscardLeaderCardPlayerMove.getInstance(Integer.parseInt(choice.get())));
            ((PrimaryController) myController).initialization();
            myController.printModel();
            //myController.blockView();
        }*/

    }

    //--------------------------------------PRINT MODEL---------------------------------------------------------------
    @Override
    public void updateModel(Match match, int playerPosition) {
        this.match = match;
        initialization();
        printModel();
    }

    @Override
    public void printModel() {
        mapLeaderCards();
        mapLeaderCardProduction();
        mapMarbles();
        mapWarehouses();
        mapDevelopmentCards();
        mapDevelopmentCardsSpaces();
        mapStrongBox();
        mapMarketResource();
        mapProductionResource();
        updateFaith(match.getWhoAmI());
    }

    public void mapStrongBox() {
        for (ResourcesCount r : Utils.fromResourcesToResourceCount(match.getPlayers().get(match.getWhoAmI()).getStrongBox())) {
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

    private void mapMarketResource() {
        ArrayList<ResourcesCount> res = Utils.fromResourcesToResourceCount(match.getPendingMarketResources());
        coin_pending.setDisable(true);
        stone_pending.setDisable(true);
        servant_pending.setDisable(true);
        shield_pending.setDisable(true);
        for (ResourcesCount r : res) {
            Pane paneTemp = null;
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

    public void mapWarehouses() {
        mapWarehousesStandard();
        mapWarehousesAdditional();
    }

    public void mapWarehousesStandard() {
        unsetAllBackgroundWarehouseStandard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(i).getResources().size(); j++) {
                changeImage(warehousesStandard.get(i).get(j), Utils.mapResTypeToImage(match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(i).getResourceType()), "resources/");
            }
        }
    }

    public void mapWarehousesAdditional() {
        unsetAllBackgroundWarehouseAdditional();
        for (int i = 0; i < match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().size(); i++) {
            for (int j = 0; j < 2; j++) {
                changeImage(warehousesAdditional.get(i).get(j), Utils.mapResTypeToImage(match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().get(i).getResourceType()), "resources/");
            }
        }
        ware_add_1.setVisible(match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().size() > 0);
        ware_add_2.setVisible(match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().size() > 1);
        if (ware_add_1.isVisible()) {
            ware_add_type_1.setText(match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().get(0).getResourceType().toString());
        }
        if (ware_add_2.isVisible()) {
            ware_add_type_2.setText(match.getPlayers().get(match.getWhoAmI()).getWarehousesAdditional().get(1).getResourceType().toString());
        }
    }

    public void mapDevelopmentCards() {
        System.out.println("stiamo mappando le dev card");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(i + "," + j);
                try {
                    changeImage(devcardmatrix[i][j], match.getDevelopmentCards()[i][j].peek().getImage(), "devcard_leadercard/");
                } catch (Exception e) {
                }
            }
        }
    }

    public void mapDevelopmentCardsSpaces() {
        System.out.println("stiamo mappando le dev card");
        for (int i = 0; i < card_spaces.size(); i++) {
            Pane space = card_spaces.get(i);
            space.setBackground(null);
            try {
                changeImage(space, match.getPlayers().get(match.getWhoAmI()).getDevelopmentCardSpaces().get(i).pickTopCard().getImage(), "devcard_leadercard/");
            } catch (Exception e) {
            }
        }
    }

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

    public void mapLeaderCards() {
        try {
            changeImage(leadercards[0], match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(0).getImage(), "devcard_leadercard/");
            changeImage(leadercards[1], match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(1).getImage(), "devcard_leadercard/");
        } catch (Exception exception) {

        }
    }

    public void mapLeaderCardProduction() {
        ArrayList<ProductivePower> activePower = match.getPlayerFromPosition(match.getWhoAmI()).getAddedPower();
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
    @Override
    public void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {
        System.out.println("Correctly received buy development card response");
        changeImage(card_spaces.get(posToAdd), developmentCardSelected.getImage(), "devcard_leadercard/");
        hasPerformedUnBlockingAction = true;
        developmentCardSelected = null;
        resNeededDevelopmentCardSelected = new ArrayList<>();
        resDevCardSelected = new Stack<>();
        hasPerformedUnBlockingAction = true;
        runningAction = MovePlayerType.NOTHING;
        disableAllMoves();
        mapDevelopmentCards();
    }

    public void dev_card_click(MouseEvent event) {
        Node node = (Node) event.getSource();
        String[] data = ((String) node.getUserData()).split(",");
        int row = Integer.parseInt(data[0]);
        int column = Integer.parseInt(data[1]);

        if (runningAction != MovePlayerType.NOTHING) {
            runDialog(Alert.AlertType.ERROR, "Another action is already running, abort it before performing another one!");
        } else {
            runDialog(Alert.AlertType.INFORMATION, "Card correctly selected, now you must select from your warehouses the resources needed");
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
    }

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
     * when a resource is cliccked in the warehouse or in the strongbox
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
     * @param event
     */
    @FXML
    public void clickMarketInteraction(MouseEvent event) {
        //get the pos and if > column dim means that the user click row
        Node node = (Node) event.getSource();
        String data = (String) node.getUserData();
        int value = Integer.parseInt(data);

        disableAllMoves();
        if (value > Utils.MARKET_COL_NUMBER - 1) {
            notify(MarketInteractionPlayerMove.getInstance(MoveType.ROW, value - Utils.MARKET_COL_NUMBER));
        } else {
            notify(MarketInteractionPlayerMove.getInstance(MoveType.COLUMN, value));
        }
        runningAction = MovePlayerType.MARKET_INTERACTION;
        hasPerformedUnBlockingAction = true;
    }

    /**
     * Manage the response to a market move
     *
     * @param moveType
     * @param pos
     * @param executePlayerPos
     */
    @Override
    public void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos) {
        //BEGIN ANIMATION
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
            slideRow(pos);
        }
        mapMarketResource();
        updateFaith(executePlayerPos);
        disableAllMoves();
        enableMoves(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.MOVE_RESOURCES);
        }}, false);
    }

    @Override
    public void manageResourceMarketConvert(int first, int second, int executePlayerPos) {
        //TODO: to manage
    }

    private void slideRow(int row) {
        Sphere additionalMarbleTemp = additionalMarble;
        this.additionalMarble = marketBoardObj[row][0]; //the marble in the left position of the row will be the next additionalMarble
        marketBoardObj[row][0] = marketBoardObj[row][1]; //slide to left
        marketBoardObj[row][1] = marketBoardObj[row][2]; //slide to left
        marketBoardObj[row][2] = marketBoardObj[row][3]; //slide to left
        marketBoardObj[row][3] = additionalMarbleTemp; //the old additional marble will be the marble in the right position of the row
    }

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

    public void onDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
    }

    public void onDragDropped(DragEvent dragEvent) {
        if (dragEvent.isAccepted()) {
            Pane warehousePane = (Pane) dragEvent.getSource();
            int warehousePos = 0;
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
            }
            switch (runningAction) {
                case NOTHING: {
                    //todo:the swap must be done here
                    //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN
                    break;
                }
                case MARKET_INTERACTION: {
                    //WE'RE INSIDE A POSITIONING
                    if (!checkForTypeCorrectness(resourceTypeDragged, warehousePos)) {
                        runDialog(Alert.AlertType.ERROR, "Error you must respect the warehousePane's type rule!");
                        return;
                    }
                    if (warehousePane.getBackground() == null) {
                        pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, -1, resourceTypeDragged));
                        pendingSelected.peek().setWarehousePosition(warehousePos);
                        decrementPendingLabelCount(resourceTypeDragged);
                        changeImage(warehousePane, Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()), "resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if (isLastPositioning()) {
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    } else {
                        runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                    }
                    break;
                }
            }
        }
        dragEvent.setDropCompleted(true);
    }

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

    private boolean isLastPositioning() {
        return new ArrayList<>(pendingSelected).stream().filter(el -> el.getWarehousePosition() != -1).count() == match.getPendingMarketResources().size();
    }

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

    @Override
    public void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos) {
        //updateFaith(match.getWhoAmI());
    }

    //----------------------------------------END TURN----------------------------------------------------------------
    public void endTurn() {
        System.out.println(match.getPendingMarketResources());
        if (!match.getPendingMarketResources().isEmpty()) {
            runDialog(Alert.AlertType.ERROR, "You must place all the resources before ending your round");
        } else {
            System.out.println(hasPerformedUnBlockingAction);
            hasPerformedUnBlockingAction = false;
            notify(EndRoundPlayerMove.getInstance());
            disableAllMoves();
            msgBox.getChildren().clear();
            msgBox.getChildren().add(new Text("Waiting for other players"));
        }
    }

    @Override
    public void manageEndTurn(boolean correctlyEnded, int executePlayerPos, String message) {
        printModel();
        /*runDialog(Alert.AlertType.INFORMATION, message);
        mapDevelopmentCards();*/
    }

    //------------------------------------ENABLE LEADER CARD---------------------------------------------------------

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
                runningAction = MovePlayerType.ENABLE_PRODUCTION;
                notify(EnableLeaderCardPlayerMove.getInstance(value));
            }
        }
    }

    @Override
    public void flipLeaderCard(int leaderCardPosition, int executePlayerPos) {
        runningAction = MovePlayerType.NOTHING;
        runDialog(Alert.AlertType.CONFIRMATION, "You successfully activate the leader card");
        mapLeaderCardProduction();
        mapWarehousesAdditional();
        //todo:depending on the type do something (map discount and map conversion strategy)
    }

    //------------------------------------DISCARD LEADER CARD---------------------------------------------------------
    public void onLeaderCardDragDetected(MouseEvent mouseEvent) {
        if (runningAction.equals(MovePlayerType.NOTHING)) {
            Pane pane = (Pane) mouseEvent.getSource();
            if (!pane.isDisable()) {
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

    //---------------------------------------PRODUCTION-------------------------------------------------------------

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
                runningAction=MovePlayerType.NOTHING;
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
                runningAction=MovePlayerType.NOTHING;
                disableAllMoves();
            }
        }

    }

    public void onProductionResDragDetected(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        if (runningAction.equals(MovePlayerType.NOTHING) || runningAction.equals(MovePlayerType.ENABLE_PRODUCTION)) {
            Dragboard db = pane.startDragAndDrop(TransferMode.COPY);
            ClipboardContent cc = new ClipboardContent();
            cc.putImage(pane.getBackground().getImages().get(0).getImage());
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

    public void onProductionDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
    }

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
            }
        }
        dragEvent.setDropCompleted(true);
    }

    private boolean isProductionPossible(ProductionType type) {
        return activeProduction.equals(ProductionType.NOTHING) || activeProduction.equals(type);
    }

    private boolean isProductionPossible(ProductionType type, int index) {
        return activeProduction.equals(ProductionType.NOTHING) || (activeProduction.equals(type) && activeProductionIndex == index);
    }

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

    private void manageProductionDevelopmentCardAddResource(int pos) {
        if (activeProductivePowerCost == null) {
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
                activeProductivePowerCost=null;
                runningAction=MovePlayerType.NOTHING;
                disableAllMoves();
            } else {
                runDialog(Alert.AlertType.INFORMATION, "You've correctly picked a required resource, pick the others");
            }
        } else {
            runDialog(Alert.AlertType.ERROR, resourceTypeProduction + " is not required!");
        }
    }

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

    @Override
    public void enableProduction(ProductivePower power, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {
        updateFaith(match.getWhoAmI());
    }

    //--------------------------------------SUPPORT METHODS---------------------------------------------------------
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

    private void runDialog(Alert.AlertType type, String message) {
        Platform.runLater(() -> {
            Alert dialog = new Alert(type, message, ButtonType.OK);
            dialog.show();
        });

    }

    @Override
    public void blockView() {
        tabpane.setDisable(true);
    }

    private void updateFaith(int index) {
        System.out.println("Vai a > " + faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX() + ", " + faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY());
        faith_player.setLayoutX(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX() + 10);
        faith_player.setLayoutY(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY() + 10);
    }

    public void onBinDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
    }

    public void onBinDragDropped(DragEvent dragEvent) {
        if (dragEvent.isAccepted()) {
            Pane pane = (Pane) dragEvent.getSource();
            if (pane == bin) { // discard resources
                if (runningAction.equals(MovePlayerType.MARKET_INTERACTION)) { // discard resources
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

    //------------------------------------OTHERS----------------------------------------------------------------------

    public void abortAction() {
        revertAction();
        runningAction = MovePlayerType.NOTHING;
        Platform.runLater(() -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Action correctly aborted", ButtonType.OK);
            dialog.show();
        });
    }

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
                mapWarehouses();
                break;
            }
            case BUY_DEVELOPMENT_CARD: {
                developmentCardSelected = null;
                resNeededDevelopmentCardSelected = new ArrayList<>();
                resDevCardSelected = new Stack<>();
                mapWarehouses();
                break;
            }

        }
    }


    //-------------------------------ON CLICK METHODS-----------------------

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

    private void unsetAllBackgroundWarehouseStandard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < warehousesStandard.get(i).size(); j++) {
                warehousesStandard.get(i).get(j).setBackground(null);
            }
        }
    }

    private void unsetAllBackgroundWarehouseAdditional() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < warehousesAdditional.get(i).size(); j++) {
                warehousesAdditional.get(i).get(j).setBackground(null);
            }
        }
    }

    public void changeImage(Pane p, String s, String type) {
        System.out.println("Stiamo cambiando icona e settando : " + type + s);
        URL url = null;
        try {
            url = new File("src/main/resources/images/" + type + s + ".png").toURI().toURL();
        } catch (Exception e) {
        }
        Image image = new Image(url.toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage myBI = new BackgroundImage(new Image(url.toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background r = new Background(myBI);
        p.setBackground(r);
    }

    @Override
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        super.changeView(fxml, clientConnectionView);
    }

    @Override
    public void printMessage(String message) {

    }


    @Override
    public void discardLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }


    @Override
    public void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove) {
        disableAllMoves();
        enableMoves(possibleMove, false);
    }

    @Override
    public void manageEndMatch() {

    }

    @Override
    public void manageReconnection(String playerName) {

    }


    @Override
    public void askForData(String message, int executePlayerPos) {

    }

    @Override
    public void moveResourceResponse(int numberOfResourcesMoved, int indexFirstWarehouse, int indexSecondWarehouse) {

    }

    @Override
    public void manageDisconnection(String playerName) {

    }

    public void playSound(String sound) {
        //Shalby's code, solo nome senza .wav
    }

    //--------------------------------------DISABLE/ENABLE METHODS-------------------------------------------------
    public void disableAllMoves() {
        enableMoves(new ArrayList<>(Arrays.asList(MovePlayerType.values())), true);
    }

    public void enableAllMoves() {
        enableMoves(new ArrayList<>(Arrays.asList(MovePlayerType.values())), false);
    }

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
            //todo: to be done
        }
        if (moves.contains(MovePlayerType.ENABLE_LEADER_CARD) || moves.contains(MovePlayerType.DISCARD_LEADER_CARD)) {
            disableLeaderCard(disable);
        }
        if (moves.contains(MovePlayerType.ENABLE_PRODUCTION)) {
            disableBaseProduction(disable);
            disableDevelopmentCardsSpace(disable);
        }
    }

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

    private void disableDevelopmentCardsMatrix(boolean disable) {
        Arrays.stream(devcardmatrix).forEach(elem -> Arrays.stream(elem).forEach(x -> x.setDisable(disable)));
        for (Pane pane : buttons_card_space) {
            pane.setDisable(disable);
        }
    }

    private void disableDevelopmentCardsSpace(boolean disable) {
        card_space_0.setDisable(disable);
        card_space_1.setDisable(disable);
        card_space_2.setDisable(disable);
    }

    private void disableLeaderCard(boolean disable) {
        for (Pane pane : leadercards) {
            pane.setDisable(disable);
        }
    }

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
