package it.polimi.ingsw.gui;

import com.sun.scenario.animation.AnimationPulse;
import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardReponse;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.DiscardTwoLeaderCardsPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.production.move.ResourceWarehouseType;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesPlayerMove;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Utils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Stack;

public class PrimaryController extends GenericController {

    private Stack<ResourcePick> pendingSelected;
    private ArrayList<Boolean> runningActions;
    private ArrayList<Resource> resToBePlaced;
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
    private Circle m_add;
    @FXML
    private Circle m_00;
    @FXML
    private Circle m_01;
    @FXML
    private Circle m_02;
    @FXML
    private Circle m_03;
    @FXML
    private Circle m_10;
    @FXML
    private Circle m_11;
    @FXML
    private Circle m_12;
    @FXML
    private Circle m_13;
    @FXML
    private Circle m_20;
    @FXML
    private Circle m_21;
    @FXML
    private Circle m_22;
    @FXML
    private Circle m_23;
    @FXML
    private Circle[][] marketBoardObj;
    @FXML
    private Circle additionalMarble;
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
        if(match!=null){
            message.updateLocalMatch(match);
        }
        //TODO: UPDATE SUCCEDE QUI
        message.elaborateGUI(this);
    }

    public void initialization(){
        pendingSelected = new Stack<>();
        runningActions = new ArrayList<>();
        resToBePlaced = new ArrayList<>();
        resNeededDevelopmentCardSelected = new ArrayList<>();
        resDevCardSelected = new Stack<>();
        buttons_card_space = new ArrayList<>();
        card_space = new ArrayList<>();
        //1-->place resources
        //2-->buy devcard
        for(int i = 0;i<5;i++){
            runningActions.add(false);
        }

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
        attachEventsToCards();

        market_button = new Pane[7];
        market_button[0] = btn_col_0;
        market_button[1] = btn_col_1;
        market_button[2] = btn_col_2;
        market_button[3] = btn_col_3;
        market_button[4] = btn_row_0;
        market_button[5] = btn_row_1;
        market_button[6] = btn_row_2;

        marketBoardObj = new Circle[3][4];
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

        buttons_card_space.add(button_card_space_0);
        buttons_card_space.add(button_card_space_1);
        buttons_card_space.add(button_card_space_2);

        card_space.add(card_space_0);
        card_space.add(card_space_1);
        card_space.add(card_space_2);
    }
    public void attachEventsToCards(){
        EventHandler<MouseEvent> eventHandler;
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<3;j++)
            {
                eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ok hai cliccato ");
                    }
                };
                //devcardmatrix[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
            }
        }
    }
    /*
        POSITIONING RESOURCE:
        - Click on the resource -> add resource pick to pending array (with random target warehouse)
        - Click on the target resource -> set correctyle the target
     */
    public void coin_pending_click(){
        int val = Integer.parseInt(coin_pending_text.getText().replaceAll("\\D+",""));
        switch (getRunningAction()){
            case -1:{
                //NO ACTIONS ALREADY RUNNING --> INITIALIZE NEW ACTION
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else{
                    runDialog(Alert.AlertType.INFORMATION, "Move all pending resources to warehouses, click on the resource and then click the target's warehouse");
                    unsetOtherActions(1);
                    pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.COIN));
                    coin_pending_text.setText(--val+"x");
                    System.out.println(new ArrayList<>(pendingSelected));

                }
                break;
            }
            case 1:{
                //RESOURCE POSITION ALREADY RUNNING, CHECK THAT PREVIOUSLY NO RESOURCE WERE LEFT WITHOUT PLACE
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else {
                    if(pendingSelected.peek().getWarehousePosition()==-1){
                        runDialog(Alert.AlertType.ERROR, "Before selecting another resource you must position the "+pendingSelected.peek().getResourceType());
                    }
                    else{
                        pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.COIN));
                        coin_pending_text.setText(--val+"x");
                        System.out.println(new ArrayList<>(pendingSelected));
                    }
                }
                break;
            }
            default: {
                runDialog(Alert.AlertType.ERROR,"Another move is already running, if you want to place resources from market pending to warehouses click abort");
            }
        }
    }
    public void stone_pending_click(){
        int val = Integer.parseInt(stone_pending_text.getText().replaceAll("\\D+",""));
        switch (getRunningAction()){
            case -1:{
                //NO ACTIONS ALREADY RUNNING --> INITIALIZE NEW ACTION
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else{
                    runDialog(Alert.AlertType.INFORMATION, "Move all pending resources to warehouses, click on the resource and then click the target's warehouse");
                    unsetOtherActions(1);
                    pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.STONE));
                    stone_pending_text.setText(--val+"x");
                    System.out.println(new ArrayList<>(pendingSelected));
                }
                break;
            }
            case 1:{
                //RESOURCE POSITION ALREADY RUNNING, CHECK THAT PREVIOUSLY NO RESOURCE WERE LEFT WITHOUT PLACE
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else {
                    if(pendingSelected.peek().getWarehousePosition()==-1){
                        runDialog(Alert.AlertType.ERROR, "Before selecting another resource you must position the "+pendingSelected.peek().getResourceType());
                    }
                    else{
                        pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.STONE));
                        stone_pending_text.setText(--val+"x");
                        System.out.println(new ArrayList<>(pendingSelected));
                    }
                }
                break;
            }
            case 2:{
                System.out.println(resNeededDevelopmentCardSelected);
                if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(ResourceType.STONE))){
                    if(val!=0){
                        resNeededDevelopmentCardSelected.remove(resNeededDevelopmentCardSelected.indexOf(Resource.getInstance(ResourceType.STONE)));
                        runDialog(Alert.AlertType.INFORMATION,Utils.fromResourcesToResourceCount(resNeededDevelopmentCardSelected).toString());
                    }
                    else{
                        runDialog(Alert.AlertType.ERROR,"Stone is required but you haven't got it");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Stone isn't required");
                }
            }
        }
    }
    public void servant_pending_click(){
        int val = Integer.parseInt(servant_pending_text.getText().replaceAll("\\D+",""));
        switch (getRunningAction()){
            case -1:{
                //NO ACTIONS ALREADY RUNNING --> INITIALIZE NEW ACTION
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else{
                    runDialog(Alert.AlertType.INFORMATION, "Move all pending resources to warehouses, click on the resource and then click the target's warehouse");
                    unsetOtherActions(1);
                    pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.SERVANT));
                    servant_pending_text.setText(--val+"x");
                    System.out.println(new ArrayList<>(pendingSelected));
                }
                break;
            }
            case 1:{
                //RESOURCE POSITION ALREADY RUNNING, CHECK THAT PREVIOUSLY NO RESOURCE WERE LEFT WITHOUT PLACE
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else {
                    if(pendingSelected.peek().getWarehousePosition()==-1){
                        runDialog(Alert.AlertType.ERROR, "Before selecting another resource you must position the "+pendingSelected.peek().getResourceType());
                    }
                    else{
                        pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.SERVANT));
                        servant_pending_text.setText(--val+"x");
                        System.out.println(new ArrayList<>(pendingSelected));
                    }
                }
                break;
            }
            case 2:{
                System.out.println(resNeededDevelopmentCardSelected);
                if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(ResourceType.SERVANT))){
                    if(val!=0){
                        resNeededDevelopmentCardSelected.remove(resNeededDevelopmentCardSelected.indexOf(Resource.getInstance(ResourceType.SERVANT)));
                        runDialog(Alert.AlertType.INFORMATION,Utils.fromResourcesToResourceCount(resNeededDevelopmentCardSelected).toString());
                    }
                    else{
                        runDialog(Alert.AlertType.ERROR,"Servant is required but you haven't got it");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Servant isn't required");
                }
            }
        }
    }
    public void shield_pending_click(){
        int val = Integer.parseInt(shield_pending_text.getText().replaceAll("\\D+",""));
        switch (getRunningAction()){
            case -1:{
                //NO ACTIONS ALREADY RUNNING --> INITIALIZE NEW ACTION
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else{
                    runDialog(Alert.AlertType.INFORMATION, "Move all pending resources to warehouses, click on the resource and then click the target's warehouse");
                    unsetOtherActions(1);
                    pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.SHIELD));
                    shield_pending_text.setText(--val+"x");
                    System.out.println(new ArrayList<>(pendingSelected));
                }
                break;
            }
            case 1:{
                //RESOURCE POSITION ALREADY RUNNING, CHECK THAT PREVIOUSLY NO RESOURCE WERE LEFT WITHOUT PLACE
                if(val==0){
                    runDialog(Alert.AlertType.ERROR, "Error you have 0 resources");
                }
                else {
                    if(pendingSelected.peek().getWarehousePosition()==-1){
                        runDialog(Alert.AlertType.ERROR, "Before selecting another resource you must position the "+pendingSelected.peek().getResourceType());
                    }
                    else{
                        pendingSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,-1,ResourceType.SHIELD));
                        shield_pending_text.setText(--val+"x");
                        System.out.println(new ArrayList<>(pendingSelected));
                    }
                }
                break;
            }
            case 2:{
                System.out.println(resNeededDevelopmentCardSelected);
                if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(ResourceType.SHIELD))){
                    if(val!=0){
                        resNeededDevelopmentCardSelected.remove(resNeededDevelopmentCardSelected.indexOf(Resource.getInstance(ResourceType.SHIELD)));
                        if(resNeededDevelopmentCardSelected.isEmpty()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the needed resources");
                        }
                        else{
                            runDialog(Alert.AlertType.INFORMATION,Utils.fromResourcesToResourceCount(resNeededDevelopmentCardSelected).toString());
                        }
                    }
                    else{
                        runDialog(Alert.AlertType.ERROR,"Shield is required but you haven't got it");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Shield isn't required");
                }
            }
        }
    }
    public void abortAction(){
        for(int p = 0;p<runningActions.size();p++){
            if(runningActions.get(p)) {
                revertAction(p);
                runningActions.set(p, false);
            }
        }
        Platform.runLater(() -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Action correctly aborted", ButtonType.OK);
            dialog.show();
        });
    }
    private void revertAction(int i){
        switch (i) {
            case 1: {
                //POSITIONING RESOURCE ==> GIVE BACK REOSURCE TO PENDING MARKET
                for(ResourcesCount r: Utils.fromResourcesToResourceCount(Utils.fromResourcePickToResources(new ArrayList<>(pendingSelected)))){
                    switch(r.getType()){
                        case COIN:{
                            int n = Integer.parseInt(coin_pending_text.getText().replaceAll("\\D+","")) + r.getCount();
                            coin_pending_text.setText(n+"x");
                            break;
                        }
                        case STONE:{
                            int n = Integer.parseInt(stone_pending_text.getText().replaceAll("\\D+","")) + r.getCount();
                            stone_pending_text.setText(n+"x");
                            break;
                        }
                        case SHIELD:{
                            int n = Integer.parseInt(shield_pending_text.getText().replaceAll("\\D+","")) + r.getCount();
                            shield_pending_text.setText(n+"x");
                            break;
                        }
                        case SERVANT:{
                            int n = Integer.parseInt(servant_pending_text.getText().replaceAll("\\D+","")) + r.getCount();
                            servant_pending_text.setText(n+"x");
                            break;
                        }
                    }
                }
                pendingSelected = new Stack<>();
                mapWarehouses();
                break;
            }
            case 2:{
                developmentCardSelected = null;
                resNeededDevelopmentCardSelected = new ArrayList<>();
                resDevCardSelected = new Stack<>();
                mapWarehouses();
                break;
            }

        }
    }
    private boolean isRunning(int i){
        return runningActions.get(i);
    }
    private int getRunningAction(){
        for(int i = 0;i<runningActions.size();i++){
            if(runningActions.get(i)){
                return i;
            }
        }
        return -1;
    }
    public void unsetOtherActions(int i){
        for(int p = 0;p<runningActions.size();p++){
            if(i!=p){
                runningActions.set(p,false);
            }
            else{
                runningActions.set(p,true);
            }
        }
    }
    private boolean isLastPositioning(){
        return new ArrayList<>(pendingSelected).stream().filter(el->el.getWarehousePosition()!=-1).count() == resToBePlaced.size();
    }
    private boolean isLastPickingDevCard(){
        return new ArrayList<>(resDevCardSelected).stream().filter(el->el.getWarehousePosition()!=-1).count() == resNeededDevelopmentCardSelected.size();
    }
    private void runDialog(Alert.AlertType type,String message){
        Platform.runLater(() -> {
            Alert dialog = new Alert(type, message, ButtonType.OK);
            dialog.show();
        });

    }

    @Override
    public void blockView() {
        tabpane.setDisable(true);
    }

    @Override
    public void printModel() {
        // new Image(url)
        mapLeaderCards();
        mapMarbles();
        mapWarehouses();
        mapDevelopmentCards();
        mapStrongBox();
    }

    public void mapStrongBox(){
        for(ResourcesCount r:Utils.fromResourcesToResourceCount(match.getPlayers().get(match.getWhoAmI()).getStrongBox())){
            switch (r.getType()){
                case STONE:{
                    stone_strongbox_text.setText(r.getCount()+"x");
                    break;
                }
                case SERVANT:{
                    servant_strongbox_text.setText(r.getCount()+"x");
                    break;
                }
                case COIN:{
                    coin_strongbox_text.setText(r.getCount()+"x");
                    break;
                }
                case SHIELD:{
                    shield_strongbox_text.setText(r.getCount()+"x");
                    break;
                }
            }
        }
    }

    public void mapWarehouses(){
        mapWarehousesStandard();
        mapWarehousesAdditional();
    }
    public void mapWarehousesStandard(){
        unsetAllBackgroundWarehouseStandard();
        for(int i = 0;i<3;i++){
            for(int j=0;j<match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(i).getResources().size();j++){
                changeImage(warehousesStandard.get(i).get(j),Utils.mapResTypeToImage(match.getPlayers().get(match.getWhoAmI()).getWarehousesStandard().get(i).getResourceType()),"resources/");
            }
        }
    }
    public void mapWarehousesAdditional(){

    }

    public void mapDevelopmentCards(){
        System.out.println("stiamo mappando le dev card");
        for(int i = 0;i<3;i++){
            for(int j = 0;j<4;j++){
                System.out.println(i+","+j);
                try{changeImage(devcardmatrix[i][j], match.getDevelopmentCards()[i][j].peek().getImage(),"devcard_leadercard/");}
                catch (Exception e){}
            }
        }
    }

    public void mapMarbles(){
        m_add.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getAdditionalMarble().getColor()));
        m_00.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,0).getColor()));
        m_01.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,1).getColor()));
        m_02.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,2).getColor()));
        m_03.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,3).getColor()));
        m_10.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,0).getColor()));
        m_11.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,1).getColor()));
        m_12.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,2).getColor()));
        m_13.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,3).getColor()));
        m_20.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,0).getColor()));
        m_21.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,1).getColor()));
        m_22.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,2).getColor()));
        m_23.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,3).getColor()));
    }

    public void mapLeaderCards(){
        changeImage(leadercards[0],match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(0).getImage(),"devcard_leadercard/");
        changeImage(leadercards[1],match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(1).getImage(),"devcard_leadercard/");
    }

    public void warehouse00Clicked(){
        switch (getRunningAction()) {
            case -1: {
                //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN

                break;
            }
            case 1: {
                //WE'RE INSIDE A POSITIONING
                Background s = ware_00.getBackground();
                if(!checkForTypeCorrectness(pendingSelected.peek().getResourceType(),0)){
                    runDialog(Alert.AlertType.ERROR, "Error you must respect the warehouse's type rule!");
                    return;
                }
                if(ware_00.getBackground()==null){
                    if(pendingSelected.peek().getWarehousePosition()!=-1){
                        runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                    }
                    else {
                        pendingSelected.peek().setWarehousePosition(0);
                        changeImage(ware_00,Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()),"resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if(isLastPositioning()){
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                }
                break;
            }
            case 2:{
                Background s = ware_00.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if(s!=null){
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))){
                        resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,0,contained));
                        ware_00.setBackground(null);
                        if(isLastPickingDevCard()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    }
                    else {
                        runDialog(Alert.AlertType.ERROR,contained + " is not required!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Nothing selected!");
                }
            }
        }
    }
    public void warehouse10Clicked(){
        switch (getRunningAction()) {
            case -1: {
                //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN

                break;
            }
            case 1: {
                //WE'RE INSIDE A POSITIONING
                Background s = ware_10.getBackground();
                if(!checkForTypeCorrectness(pendingSelected.peek().getResourceType(),1)){
                    runDialog(Alert.AlertType.ERROR, "Error you must respect the warehouse's type rule!");
                    return;
                }
                if(ware_10.getBackground()==null){
                    if(pendingSelected.peek().getWarehousePosition()!=-1){
                        runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                    }
                    else{
                        pendingSelected.peek().setWarehousePosition(1);
                        changeImage(ware_10,Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()),"resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if(isLastPositioning()){
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    }

                }
                else{
                    runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                }
                break;
            }
            case 2:{
                Background s = ware_10.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if(s!=null){
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))){
                        resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,1,contained));
                        ware_10.setBackground(null);
                        if(isLastPickingDevCard()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    }
                    else {
                        runDialog(Alert.AlertType.ERROR,contained + " is not required!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Nothing selected!");
                }
            }
        }
    }
    public void warehouse11Clicked(){
        switch (getRunningAction()) {
            case -1: {
                //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN

                break;
            }
            case 1: {
                //WE'RE INSIDE A POSITIONING
                Background s = ware_11.getBackground();
                if(!checkForTypeCorrectness(pendingSelected.peek().getResourceType(),1)){
                    runDialog(Alert.AlertType.ERROR, "Error you must respect the warehouse's type rule!");
                    return;
                }
                if(ware_11.getBackground()==null){
                    if(pendingSelected.peek().getWarehousePosition()!=-1){
                        runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                    }
                    else{
                        pendingSelected.peek().setWarehousePosition(1);
                        changeImage(ware_11,Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()),"resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if(isLastPositioning()){
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                }
                break;
            }
            case 2:{
                Background s = ware_11.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if(s!=null){
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))){
                        resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,1,contained));
                        ware_11.setBackground(null);
                        if(isLastPickingDevCard()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    }
                    else {
                        runDialog(Alert.AlertType.ERROR,contained + " is not required!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Nothing selected!");
                }
            }
        }
    }
    public void warehouse20Clicked(){
        switch (getRunningAction()) {
            case -1: {
                //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN

                break;
            }
            case 1: {
                //WE'RE INSIDE A POSITIONING
                Background s = ware_20.getBackground();
                if(!checkForTypeCorrectness(pendingSelected.peek().getResourceType(),2)){
                    runDialog(Alert.AlertType.ERROR, "Error you must respect the warehouse's type rule!");
                    return;
                }
                if(ware_20.getBackground()==null){
                    if(pendingSelected.peek().getWarehousePosition()!=-1){
                        runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                    }
                    else {
                        pendingSelected.peek().setWarehousePosition(2);
                        changeImage(ware_20, Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()), "resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if(isLastPositioning()){
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                }
                break;
            }
            case 2:{
                Background s = ware_20.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if(s!=null){
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))){
                        resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,2,contained));
                        ware_20.setBackground(null);
                        if(isLastPickingDevCard()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    }
                    else {
                        runDialog(Alert.AlertType.ERROR,contained + " is not required!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Nothing selected!");
                }
            }
        }
    }
    public void warehouse21Clicked(){
        switch (getRunningAction()) {
            case -1: {
                //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN

                break;
            }
            case 1: {
                //WE'RE INSIDE A POSITIONING
                Background s = ware_21.getBackground();
                if(!checkForTypeCorrectness(pendingSelected.peek().getResourceType(),2)){
                    runDialog(Alert.AlertType.ERROR, "Error you must respect the warehouse's type rule!");
                    return;
                }
                if(ware_21.getBackground()==null){
                    if(pendingSelected.peek().getWarehousePosition()!=-1){
                        runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                    }
                    else{
                        pendingSelected.peek().setWarehousePosition(2);
                        changeImage(ware_21,Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()),"resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if(isLastPositioning()){
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                }
                break;
            }
            case 2:{
                Background s = ware_21.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if(s!=null){
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))){
                        resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,2,contained));
                        ware_21.setBackground(null);
                        if(isLastPickingDevCard()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    }
                    else {
                        runDialog(Alert.AlertType.ERROR,contained + " is not required!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Nothing selected!");
                }
            }
        }
    }
    public void warehouse22Clicked(){
        switch (getRunningAction()) {
            case -1: {
                //PROBABLY A SWAP WAREHOUSE INTERACTION IS GOING TO HAPPEN

                break;
            }
            case 1: {
                //WE'RE INSIDE A POSITIONING
                Background s = ware_22.getBackground();
                if(!checkForTypeCorrectness(pendingSelected.peek().getResourceType(),2)){
                    runDialog(Alert.AlertType.ERROR, "Error you must respect the warehouse's type rule!");
                    return;
                }
                if(ware_22.getBackground()==null){
                    if(pendingSelected.peek().getWarehousePosition()!=-1){
                        runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                    }
                    else{
                        pendingSelected.peek().setWarehousePosition(2);
                        changeImage(ware_22,Utils.mapResTypeToImage(pendingSelected.peek().getResourceType()),"resources/");
                        System.out.println(new ArrayList<>(pendingSelected));
                        if(isLastPositioning()){
                            runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                            sendPlaceResourceMove();
                        }
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR, "Error Warehouse is not empty!");
                }
                break;
            }
            case 2:{
                Background s = ware_22.getBackground();
                System.out.println(resNeededDevelopmentCardSelected);

                if(s!=null){
                    ResourceType contained = Utils.getResourceTypeFromUrl(s.getImages().get(0).getImage().getUrl());
                    if(resNeededDevelopmentCardSelected.contains(Resource.getInstance(contained))){
                        resDevCardSelected.push(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE,2,contained));
                        ware_22.setBackground(null);
                        if(isLastPickingDevCard()){
                            runDialog(Alert.AlertType.INFORMATION,"You've correctly picked all the resources, now you must choose where to place the devcard!");
                        }
                    }
                    else {
                        runDialog(Alert.AlertType.ERROR,contained + " is not required!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Nothing selected!");
                }
            }
        }
    }

    public void button_card_space_0_clicked(){
        switch(getRunningAction())
        {
            case 2:{
                if(resNeededDevelopmentCardSelected.size()==resDevCardSelected.size()){
                    if(match.getPlayers().get(match.getWhoAmI()).developmentCardCanBeAdded(developmentCardSelected, 0)){
                        runDialog(Alert.AlertType.INFORMATION,"The card can be correctly placed here!");
                        notify(BuyDevelopmentCardPlayerMove.getInstance(developmentCardSelected.getType(),developmentCardSelected.getLevel(),0,new ArrayList<>(resDevCardSelected)));
                    }
                    else{
                        runDialog(Alert.AlertType.ERROR,"You can't place there your card!");
                    }
                }
                else{
                    runDialog(Alert.AlertType.ERROR,"Before trying to place the card you must pick all the resources needed!");
                }
                break;
            }
            default:{
                runDialog(Alert.AlertType.ERROR,"Check which action you're performing!");
            }
        }
    }
    public void button_card_space_1_clicked(){

    }
    public void button_card_space_2_clicked(){

    }

    public void binClicked(){
        switch (getRunningAction()) {
            case -1: {
                //NOTHING

                break;
            }
            case 1: {
                if(pendingSelected.peek().getWarehousePosition()!=-1){
                    runDialog(Alert.AlertType.ERROR, "Resource already placed, select another one!");
                }
                else {
                    pendingSelected.peek().setWarehousePosition(6); //6 = DISCARD
                    System.out.println(new ArrayList<>(pendingSelected));
                    if(isLastPositioning()){
                        runDialog(Alert.AlertType.CONFIRMATION, "You've correctly placed all the market pending resources");
                        sendPlaceResourceMove();
                    }
                }
                break;
            }
        }
    }

    private void sendPlaceResourceMove(){
        runningActions.set(1,false);
        //map respick to integer
        ArrayList<Integer> whereToBePlaced = new ArrayList<>();
        ArrayList<ResourcePick> whereArePlaced = new ArrayList<>(pendingSelected);
        for (Resource r:resToBePlaced) {
            for(int i = 0;i<pendingSelected.size();i++){
                if(r.getType()==pendingSelected.get(i).getResourceType()){
                    whereToBePlaced.add(pendingSelected.get(i).getWarehousePosition());
                    System.out.println("Place a "+r.getType()+ " in : "+pendingSelected.get(i).getWarehousePosition());
                    pendingSelected.remove(i);
                }
            }
        }

        try {
            notify(PositioningResourcesPlayerMove.getInstance(whereToBePlaced));
        }catch(Exception e){
            //todo: what do we do?
        }
        pendingSelected = new Stack<>();
        resToBePlaced = new ArrayList<>();
    }

    private boolean checkForTypeCorrectness(ResourceType resourceType, int where){
        //check 3 warehouses rule
        //TODO: warehouse addizionali
        for(int i=0;i<warehousesStandard.size();i++){
            for(int j=0;j<warehousesStandard.get(i).size();j++){
                try{
                    ResourceType res = Utils.getResourceTypeFromUrl(warehousesStandard.get(i).get(j).getBackground().getImages().get(0).getImage().getUrl());
                    if(res==resourceType && where!=i){
                        return false;
                    }
                }catch (Exception e){
                    //è vuoto
                }
            }
        }
        //check warehouse correctness
        for(int j=0;j<warehousesStandard.get(where).size();j++){
            try{
                ResourceType res = Utils.getResourceTypeFromUrl(warehousesStandard.get(where).get(j).getBackground().getImages().get(0).getImage().getUrl());
                if(res!=resourceType){
                    //trying to add to warehouse with different types
                    return false;
                }
            }catch (Exception e){
                //è vuoto
            }
        }
        return true;
    }

    private void unsetAllBackgroundWarehouseStandard(){
        for(int i = 0;i<3;i++){
            for(int j=0;j<warehousesStandard.get(i).size();j++){
                warehousesStandard.get(i).get(j).setBackground(null);
            }
        }
    }

    public void changeImage(Pane p,String s,String type){
        System.out.println("Stiamo cambiando icona e settando : "+type+s);
        URL url = null;
        try{
            url = new File("src/main/resources/images/"+type+s+".png").toURI().toURL();
        }catch (Exception e){}
        Image image = new Image(url.toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage myBI= new BackgroundImage(new Image(url.toString()),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background r = new Background(myBI);
        p.setBackground(r);
    }

    @Override
    public void changeView(String fxml, ClientConnection clientConnection) throws IOException {
        super.changeView(fxml, clientConnection);
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
    public void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {
        System.out.println("Correctly received buy decelopment card response");
        changeImage(card_space.get(posToAdd),developmentCardSelected.getImage(),"devcard_leadercard/");
        hasPerformedUnBlockingAction = true;
        developmentCardSelected = null;
        resNeededDevelopmentCardSelected = new ArrayList<>();
        resDevCardSelected = new Stack<>();
        hasPerformedUnBlockingAction = true;
        runningActions.set(2,false);
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

    @Override
    public void discardLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }

    @Override
    public void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos) {
        //BEGIN ANIMATION
        ArrayList<TranslateTransition> translateTransitionArray = new ArrayList<>();
        final double duration = 1;
        TranslateTransition temp,t2,t3;
        ParallelTransition parallelTransition = new ParallelTransition();
        ParallelTransition parallelTransitionDouble = new ParallelTransition();
        SequentialTransition sequentialTransition = new SequentialTransition();

        if(moveType==MoveType.COLUMN)
        {
            t2 = new TranslateTransition(
                    Duration.seconds(duration), additionalMarble
            );
            t3 = new TranslateTransition(
                    Duration.seconds(duration), additionalMarble
            );
            t2.setByY(210);
            t3.setByX(-1*(3-pos)*80);
            System.out.println(marketBoardObj[0][pos].getLayoutX()-additionalMarble.getLayoutX());
            parallelTransitionDouble.getChildren().add(t2);
            parallelTransitionDouble.getChildren().add(t3);
            sequentialTransition.getChildren().add(parallelTransitionDouble);
            for(int i = 2;i>=0;i--)
            {
                temp = new TranslateTransition(
                        Duration.seconds(duration), marketBoardObj[i][pos]
                );
                temp.setByY(-70);
                parallelTransition.getChildren().add(temp);
            }
            temp = new TranslateTransition(
                    Duration.seconds(duration), marketBoardObj[0][pos]
            );
            temp.setByX((3-pos)*80);
            sequentialTransition.getChildren().add(parallelTransition);
            sequentialTransition.getChildren().add(temp);
            sequentialTransition.play();
            slideColumn(pos);
        }
        else
        {
            t2 = new TranslateTransition(
                    Duration.seconds(duration), additionalMarble
            );
            t3 = new TranslateTransition(
                    Duration.seconds(duration), additionalMarble
            );
            t2.setByY((pos+1)*70);
            t3.setByX(80);
            System.out.println(marketBoardObj[0][pos].getLayoutX()-additionalMarble.getLayoutX());
            parallelTransitionDouble.getChildren().add(t2);
            parallelTransitionDouble.getChildren().add(t3);
            sequentialTransition.getChildren().add(parallelTransitionDouble);
            for(int i = 3;i>=0;i--)
            {
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
            temp.setByX(4*80);
            temp.setByY(-1*(pos+1)*70);
            sequentialTransition.getChildren().add(parallelTransition);
            sequentialTransition.getChildren().add(temp);
            sequentialTransition.play();
            slideRow(pos);
        }
        updateMarketResource();
        updateFaith(executePlayerPos);
        print();
    }

    private void updateMarketResource(){
        ArrayList<ResourcesCount> res = Utils.fromResourcesToResourceCount(match.getPendingMarketResources());
        resToBePlaced = match.getPendingMarketResources();
        for(ResourcesCount r: res){
            switch (r.getType()){
                case COIN:{
                    coin_pending_text.setText(r.getCount()+"x");
                    break;
                }
                case STONE:{
                    stone_pending_text.setText(r.getCount()+"x");
                    break;
                }
                case SERVANT:{
                    servant_pending_text.setText(r.getCount()+"x");
                    break;
                }
                case SHIELD:{
                    shield_pending_text.setText(r.getCount()+"x");
                    break;
                }
                default: break;
            }
        }
    }
    private void updateFaith(int index){
        System.out.println("Vai a > "+faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX()+", "+faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY());
        faith_player.setLayoutX(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutX()+10);
        faith_player.setLayoutY(faithArray[match.getPlayers().get(index).getPosFaithMarker()].getLayoutY()+10);
    }
    private void slideRow(int row) {
        Circle additionalMarbleTemp = additionalMarble;
        this.additionalMarble = marketBoardObj[row][0]; //the marble in the left position of the row will be the next additionalMarble
        marketBoardObj[row][0] = marketBoardObj[row][1]; //slide to left
        marketBoardObj[row][1] = marketBoardObj[row][2]; //slide to left
        marketBoardObj[row][2] = marketBoardObj[row][3]; //slide to left
        marketBoardObj[row][3] = additionalMarbleTemp; //the old additional marble will be the marble in the right position of the row
    }
    private void slideColumn(int column) {
        Circle additionalMarbleTemp = additionalMarble;
        this.additionalMarble = marketBoardObj[0][column]; //the marble in the top position of the column will be the next additionalMarble
        marketBoardObj[0][column] = marketBoardObj[1][column]; //slide top
        marketBoardObj[1][column] = marketBoardObj[2][column]; //slide top
        marketBoardObj[2][column] = additionalMarbleTemp;;  //the old additional marble will be the marble in the bottom position of the column
    }
    private String print(){
        System.out.println(change(additionalMarble.getFill().toString()));
        for(int i =0;i<3;i++)
        {
            for(int j=0;j<4;j++)
            {
                System.out.print(change(marketBoardObj[i][j].getFill().toString())+"\t");
            }
            System.out.println();
        }
        return null;
    }
    private String change(String s){
        if(s.equals(Color.RED.toString()))
        {
            return "RED";
        }
        else if(s.equals(Color.AQUA.toString()))
        {
            return "AQ";
        }
        else if(s.equals(Color.WHITE.toString()))
        {
            return "WH";
        }
        else if(s.equals(Color.GREY.toString()))
        {
            return "GR";
        }
        else if(s.equals(Color.PURPLE.toString()))
        {
            return "PU";
        }
        else if(s.equals(Color.YELLOW.toString()))
        {
            return "YE";
        }
        return null;
    }

    @Override
    public void manageResourceMarketConvert(int first, int second, int executePlayerPos) {

    }

    @Override
    public void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove) {
        enableMove(possibleMove);
        System.out.println("manage");
    }

    @Override
    public void manageEndTurn(boolean correctlyEnded, int executePlayerPos,String message) {
        runDialog(Alert.AlertType.INFORMATION,message);
        mapDevelopmentCards();
    }

    @Override
    public void manageEndMatch() {

    }

    @Override
    public void updateModel(Match match, int playerPosition) {
        this.match= match;
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
    public void askToDiscardTwoLeader(int numOfResource, int executePlayerPos) {
        //Todo:to Modify
        notify(DiscardTwoLeaderCardsPlayerMove.getInstance(0,1, ResourceType.COIN,ResourceType.FAITH));
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

    @Override
    public void askForData(String message, int executePlayerPos) {

    }

    @Override
    public void moveResourceResponse(int numberOfResourcesMoved, int indexFirstWarehouse, int indexSecondWarehouse) {

    }

    @Override
    public void manageDisconnection(String playerName) {

    }

    public void playSound(String sound){
        //Shalby's code, solo nome senza .wav
    }

    public void clickCol3(){
        disableAllMoves();
        notify(MarketInteractionPlayerMove.getInstance(MoveType.COLUMN,3));
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }
    public void clickCol2(){
        disableAllMoves();
        notify(MarketInteractionPlayerMove.getInstance(MoveType.COLUMN,2));
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }
    public void clickCol1(){
        disableAllMoves();
        notify(MarketInteractionPlayerMove.getInstance(MoveType.COLUMN,1));
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }
    public void clickCol0(){
        disableAllMoves();
        notify(MarketInteractionPlayerMove.getInstance(MoveType.COLUMN,0));
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }
    public void clickRow2(){
        disableAllMoves();
        notify(MarketInteractionPlayerMove.getInstance(MoveType.ROW,2));
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }
    public void clickRow1(){
        notify(MarketInteractionPlayerMove.getInstance(MoveType.ROW,1));
        disableAllMoves();
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }
    public void clickRow0(){
        disableAllMoves();
        notify(MarketInteractionPlayerMove.getInstance(MoveType.ROW,0));
        enableMove(new ArrayList<MovePlayerType>() {{
            add(MovePlayerType.DISCARD_LEADER_CARD);
            add(MovePlayerType.ENABLE_LEADER_CARD);
            add(MovePlayerType.END_TURN);
            add(MovePlayerType.MOVE_RESOURCES);
        }});
        hasPerformedUnBlockingAction = true;
    }

    public void endTurn(){
        //
        System.out.println(match.getPendingMarketResources());
        if(!match.getPendingMarketResources().isEmpty()){
            runDialog(Alert.AlertType.ERROR,"You must place all the resources before ending your round");
        }
        else{
            System.out.println(hasPerformedUnBlockingAction);
            hasPerformedUnBlockingAction = false;
            notify(EndRoundPlayerMove.getInstance());
            disableAllMoves();
            msgBox.getChildren().clear();
            msgBox.getChildren().add( new Text("Waiting for other players"));
        }
    }

    public void disableAllMoves(){
        System.out.println("disable");
        for(Pane p:leadercards){
            p.setDisable(true);
        }
        /*for(Pane p[]:devcardmatrix){
            for(Pane s:p){
                s.setDisable(true);
            }
        }*/
        for(Pane p:market_button){
            p.setDisable(true);
        }
        coin_pending.setDisable(true);
        stone_pending.setDisable(true);
        servant_pending.setDisable(true);
        shield_pending.setDisable(true);
    }

    public void enableMove(ArrayList<MovePlayerType> moves){
        System.out.println("enable "+ moves.toString());
        Cursor c = null;
        boolean dis = false;
        //Market Interaction
        if(moves.contains(MovePlayerType.MARKET_INTERACTION))
        {
            for(Pane p:market_button){
                p.setDisable(false);
            }
        }
        if(moves.contains(MovePlayerType.BUY_DEVELOPMENT_CARD))
        {
            /*for(Pane p[]:devcardmatrix){
                for(Pane s:p){
                    s.setDisable(true);
                }
            }*/
        }
        if(moves.contains(MovePlayerType.MOVE_RESOURCES))
        {
            coin_pending.setDisable(false);
            servant_pending.setDisable(false);
            shield_pending.setDisable(false);
            stone_pending.setDisable(false);
        }
    }

    public void devcard00_click(){
        if(getRunningAction()!=-1){
            runDialog(Alert.AlertType.ERROR,"Another action is already running, abort it before performing another one!");
        }
        else{
            runDialog(Alert.AlertType.INFORMATION,"Card correctly selected, now you must select from your warehouses the resources needed");
            runningActions.set(2,true);
            developmentCardSelected = match.getDevelopmentCards()[0][0].peek();
            resNeededDevelopmentCardSelected = Utils.fromResourceCountToResources(developmentCardSelected.getCosts(match.getPlayers().get(match.getWhoAmI())));
            ScaleTransition st = new ScaleTransition(Duration.millis(500),devcard_00.getParent());
            st.setByX(0.3f);
            st.setByY(0.3f);
            st.setCycleCount(2);
            st.setAutoReverse(true);
            st.play();
        }
    }
}
