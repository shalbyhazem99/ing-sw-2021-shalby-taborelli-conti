package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.ClientConnectionView;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.DiscardTwoLeaderCardsPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.production.move.ResourceWarehouseType;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesPlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
import java.util.Stack;

public class PrimaryController extends GenericController {

    private Stack<ResourcePick> pendingSelected;
    private MovePlayerType runningAction;
    private boolean hasPerformedUnBlockingAction = false;
    private DevelopmentCard developmentCardSelected;
    private ArrayList<Resource> resNeededDevelopmentCardSelected;
    private Stack<ResourcePick> resDevCardSelected;

    @FXML
    private TabPane tabpane;
    @FXML
    private Pane leadercard1;
    @FXML
    private Pane leadercard2;

    @FXML
    private Sphere m_add;
    @FXML
    private Sphere m_00;
    @FXML
    private Sphere m_01;
    @FXML
    private Sphere m_02;
    @FXML
    private Sphere m_03;
    @FXML
    private Sphere m_10;
    @FXML
    private Sphere m_11;
    @FXML
    private Sphere m_12;
    @FXML
    private Sphere m_13;
    @FXML
    private Sphere m_20;
    @FXML
    private Sphere m_21;
    @FXML
    private Sphere m_22;
    @FXML
    private Sphere m_23;
    @FXML
    private Sphere[][] marketBoardObj;
    @FXML
    private Sphere additionalMarble;
    //--------------------------

    @FXML
    private Pane[] leadercards;
    @FXML
    private Pane devcard_00;
    @FXML
    private Pane devcard_01;
    @FXML
    private Pane devcard_02;
    @FXML
    private Pane devcard_03;
    @FXML
    private Pane devcard_10;
    @FXML
    private Pane devcard_11;
    @FXML
    private Pane devcard_12;
    @FXML
    private Pane devcard_13;
    @FXML
    private Pane devcard_20;
    @FXML
    private Pane devcard_21;
    @FXML
    private Pane devcard_22;
    @FXML
    private Pane devcard_23;
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
    private Pane faith_0;
    @FXML
    private Pane faith_1;
    @FXML
    private Pane faith_2;
    @FXML
    private Pane faith_3;
    @FXML
    private Pane faith_4;
    @FXML
    private Pane faith_5;
    @FXML
    private Pane faith_6;
    @FXML
    private Pane faith_7;
    @FXML
    private Pane faith_8;
    @FXML
    private Pane faith_9;
    @FXML
    private Pane faith_10;
    @FXML
    private Pane faith_11;
    @FXML
    private Pane faith_12;
    @FXML
    private Pane faith_13;
    @FXML
    private Pane faith_14;
    @FXML
    private Pane faith_15;
    @FXML
    private Pane faith_16;
    @FXML
    private Pane faith_17;
    @FXML
    private Pane faith_18;
    @FXML
    private Pane faith_19;
    @FXML
    private Pane faith_20;
    @FXML
    private Pane faith_21;
    @FXML
    private Pane faith_22;
    @FXML
    private Pane faith_23;
    @FXML
    private Pane faith_24;
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
    @FXML
    private Pane bin;

    @FXML
    private ArrayList<ArrayList<Pane>> warehousesStandard;

    @FXML
    private Pane button_card_space_0;
    @FXML
    private Pane button_card_space_1;
    @FXML
    private Pane button_card_space_2;
    @FXML
    private ArrayList<Pane> buttons_card_space;
    @FXML
    private ArrayList<Pane> card_space;
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
        card_space = new ArrayList<>();

        leadercards = new Pane[2];
        leadercards[0] = leadercard1;
        leadercards[1] = leadercard2;

        devcardmatrix = new Pane[3][4];
        devcardmatrix[0][0] = devcard_00;
        devcardmatrix[0][1] = devcard_01;
        devcardmatrix[0][2] = devcard_02;
        devcardmatrix[0][3] = devcard_03;
        devcardmatrix[1][0] = devcard_10;
        devcardmatrix[1][1] = devcard_11;
        devcardmatrix[1][2] = devcard_12;
        devcardmatrix[1][3] = devcard_13;
        devcardmatrix[2][0] = devcard_20;
        devcardmatrix[2][1] = devcard_21;
        devcardmatrix[2][2] = devcard_22;
        devcardmatrix[2][3] = devcard_23;

        market_button = new Pane[7];
        market_button[0] = btn_col_0;
        market_button[1] = btn_col_1;
        market_button[2] = btn_col_2;
        market_button[3] = btn_col_3;
        market_button[4] = btn_row_0;
        market_button[5] = btn_row_1;
        market_button[6] = btn_row_2;

        marketBoardObj = new Sphere[3][4];
        marketBoardObj[0][0] = m_00;
        marketBoardObj[0][1] = m_01;
        marketBoardObj[0][2] = m_02;
        marketBoardObj[0][3] = m_03;
        marketBoardObj[1][0] = m_10;
        marketBoardObj[1][1] = m_11;
        marketBoardObj[1][2] = m_12;
        marketBoardObj[1][3] = m_13;
        marketBoardObj[2][0] = m_20;
        marketBoardObj[2][1] = m_21;
        marketBoardObj[2][2] = m_22;
        marketBoardObj[2][3] = m_23;
        additionalMarble = m_add;

        faithArray = new Pane[25];
        faithArray[0] = faith_0;
        faithArray[1] = faith_1;
        faithArray[2] = faith_2;
        faithArray[3] = faith_3;
        faithArray[4] = faith_4;
        faithArray[5] = faith_5;
        faithArray[6] = faith_6;
        faithArray[7] = faith_7;
        faithArray[8] = faith_8;
        faithArray[9] = faith_9;
        faithArray[10] = faith_10;
        faithArray[10] = faith_10;
        faithArray[11] = faith_11;
        faithArray[12] = faith_12;
        faithArray[13] = faith_13;
        faithArray[14] = faith_14;
        faithArray[15] = faith_15;
        faithArray[16] = faith_16;
        faithArray[17] = faith_17;
        faithArray[18] = faith_18;
        faithArray[19] = faith_19;
        faithArray[20] = faith_20;
        faithArray[21] = faith_21;
        faithArray[22] = faith_22;
        faithArray[23] = faith_23;
        faithArray[24] = faith_24;

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

        buttons_card_space.add(button_card_space_0);
        buttons_card_space.add(button_card_space_1);
        buttons_card_space.add(button_card_space_2);

        card_space.add(card_space_0);
        card_space.add(card_space_1);
        card_space.add(card_space_2);
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
    public void printModel() {
        // new Image(url)
        mapLeaderCards();
        mapMarbles();
        mapWarehouses();
        mapDevelopmentCards();
        mapStrongBox();
        mapMarketResource();
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

    /**
     * Update the Market Pending resources
     */
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
        //todo: to be implemented
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
        changeImage(leadercards[0], match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(0).getImage(), "devcard_leadercard/");
        changeImage(leadercards[1], match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(1).getImage(), "devcard_leadercard/");
    }

    //-----------------------------------BUY DEVELOPMENT CARD---------------------------------------------------------
    @Override
    public void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {
        System.out.println("Correctly received buy decelopment card response");
        changeImage(card_space.get(posToAdd), developmentCardSelected.getImage(), "devcard_leadercard/");
        hasPerformedUnBlockingAction = true;
        developmentCardSelected = null;
        resNeededDevelopmentCardSelected = new ArrayList<>();
        resDevCardSelected = new Stack<>();
        hasPerformedUnBlockingAction = true;
        runningAction = MovePlayerType.NOTHING;
        disableAllMoves();
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        mapWarehouses();
        mapDevelopmentCards();
    }

    public void devcard_click(MouseEvent event) {
        Node node = (Node) event.getSource();
        String[] data = ((String) node.getUserData()).split(",");
        int row = Integer.parseInt(data[0]);
        int column = Integer.parseInt(data[1]);

        if (runningAction != MovePlayerType.NOTHING) {
            runDialog(Alert.AlertType.ERROR, "Another action is already running, abort it before performing another one!");
        } else {
            runDialog(Alert.AlertType.INFORMATION, "Card correctly selected, now you must select from your warehouses the resources needed");
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
                if (resNeededDevelopmentCardSelected.size() == resDevCardSelected.size()) {
                    if (match.getPlayers().get(match.getWhoAmI()).developmentCardCanBeAdded(developmentCardSelected, card_space_pos)) {
                        runDialog(Alert.AlertType.INFORMATION, "The card can be correctly placed here!");
                        notify(BuyDevelopmentCardPlayerMove.getInstance(developmentCardSelected.getType(), developmentCardSelected.getLevel(), 0, new ArrayList<>(resDevCardSelected)));
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
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
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

    public void onResDragDetected(MouseEvent mouseEvent) {
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
            cc.putImage(pane.getBackground().getImages().get(0).getImage());
            db.setContent(cc);
        }
    }

    public void onDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
    }

    public void onDragDroped(DragEvent dragEvent) {
        if (dragEvent.isAccepted()) {
            Pane warehousePane = (Pane) dragEvent.getSource();
            int warehousePos = 0;
            if (warehousePane == ware_00) {
                warehousePos = 0;
            } else if (warehousePane == ware_10 || warehousePane == ware_11) {
                warehousePos = 1;
            } else if (warehousePane == ware_20 || warehousePane == ware_21 || warehousePane == ware_22) {
                warehousePos = 2;
            } else if (warehousePane == bin) { // discard resources
                if (runningAction.equals(MovePlayerType.MARKET_INTERACTION)) {
                    pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, -1, resourceTypeDragged));
                    pendingSelected.peek().setWarehousePosition(6); //6 = DISCARD
                    decrementPendingLabelCount(resourceTypeDragged);
                    System.out.println(new ArrayList<>(pendingSelected));
                    if (isLastPositioning()) {
                        runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                        sendPlaceResourceMove();
                    }
                }
                dragEvent.setDropCompleted(true);
                return;
            }
            switch (runningAction) {
                case NOTHING: {
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
                        if (pendingSelected.peek().getWarehousePosition() != -1) {
                            runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                        } else {
                            pendingSelected.peek().setWarehousePosition(warehousePos);
                            decrementPendingLabelCount(resourceTypeDragged);
                            changeImage(warehousePane, Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()), "resources/");
                            System.out.println(new ArrayList<>(pendingSelected));
                            if (isLastPositioning()) {
                                runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                                sendPlaceResourceMove();
                            }
                        }
                    } else {
                        runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                    }
                    break;
                }
                /*case BUY_DEVELOPMENT_CARD: {
                    //select
                    Background s = warehousePane.getBackground();
                    System.out.println(resNeededDevelopmentCardSelected);

                    if (s != null) {
                        ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                        if (resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))) {
                            resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, 0, contained));
                            warehousePane.setBackground(null);
                            if (isLastPickingDevCard()) {
                                runDialog(Alert.AlertType.INFORMATION, "You've correctly picked all the resources, now you must choose where to place the devcard!");
                            }
                        } else {
                            runDialog(Alert.AlertType.ERROR, contained + " is not required!");
                        }
                    } else {
                        runDialog(Alert.AlertType.ERROR, "Nothing selected!");
                    }
                }*/
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
        runDialog(Alert.AlertType.INFORMATION, message);
        mapDevelopmentCards();
    }

    //--------------------------------------SUPPORT METHODS---------------------------------------------------------
    private void updateFaith(int index) {
        System.out.println("Vai a > " + faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX() + ", " + faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY());
        faith_player.setLayoutX(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX() + 10);
        faith_player.setLayoutY(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY() + 10);
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


    private boolean isLastPickingDevCard() {
        return new ArrayList<>(resDevCardSelected).stream().filter(el -> el.getWarehousePosition() != -1).count() == resNeededDevelopmentCardSelected.size();
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

    public void resource_pending_click() {

    }

    public void warehouseClicked() {

    }


    //-------------------------------ON CLICK METHODS-----------------------

    private boolean checkForTypeCorrectness(ResourceType resourceType, int where) {
        //check 3 warehouses rule
        //TODO: warehouse addizionali
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
    public void flipLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }

    @Override
    public void enableProduction(ProductivePower power, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {

    }


    @Override
    public void discardLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }


    @Override
    public void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove) {
        enableMove(possibleMove);
        System.out.println("manage");
    }

    @Override
    public void manageEndMatch() {

    }

    @Override
    public void updateModel(Match match, int playerPosition) {
        this.match = match;
        initialization();
        printModel();
        //todo: print
    }

    @Override
    public void manageReconnection(String playerName) {

    }

    @Override
    public void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos) {

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


    public void disableAllMoves() {
        System.out.println("disable");
        for (Pane p : leadercards) {
            p.setDisable(true);
        }
        /*for(Pane p[]:devcardmatrix){
            for(Pane s:p){
                s.setDisable(true);
            }
        }*/
        for (Pane p : market_button) {
            p.setDisable(true);
        }
        coin_pending.setDisable(true);
        stone_pending.setDisable(true);
        servant_pending.setDisable(true);
        shield_pending.setDisable(true);
    }

    public void enableMove(ArrayList<MovePlayerType> moves) {
        System.out.println("enable " + moves.toString());
        Cursor c = null;
        boolean dis = false;
        //Market Interaction
        if (moves.contains(MovePlayerType.MARKET_INTERACTION)) {
            for (Pane p : market_button) {
                p.setDisable(false);
            }
        }
        if (moves.contains(MovePlayerType.BUY_DEVELOPMENT_CARD)) {
            /*for(Pane p[]:devcardmatrix){
                for(Pane s:p){
                    s.setDisable(true);
                }
            }*/
        }
        if (moves.contains(MovePlayerType.MOVE_RESOURCES)) {
            coin_pending.setDisable(false);
            servant_pending.setDisable(false);
            shield_pending.setDisable(false);
            stone_pending.setDisable(false);
        }
    }
}
