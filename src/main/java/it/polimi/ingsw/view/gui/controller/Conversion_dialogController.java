package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.connection.ClientConnectionView;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.view.gui.GenericController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;

public class Conversion_dialogController extends GenericController {
    @FXML
    private Pane firstPaneConversion;
    @FXML
    private Pane secondPaneConversion;
    @FXML
    private Spinner firstSpinnerConversion;
    @FXML
    private Spinner secondSpinnerConversion;
    private int num;
    private boolean initialized = false;




    /**
     * c&p from Spinner
     */
    private <T> void commitEditorText(Spinner<T> spinner) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }
/*
// useage in client code
spinner.focusedProperty().addListener((s, ov, nv) -> {
        if (nv) return;
        //intuitive method on textField, has no effect, though
        //spinner.getEditor().commitValue();
        commitEditorText(spinner);
    });*/

    @FXML
    public void firstSpinnerClicked(){

    }
    @FXML
    public void secondSpinnerClicked(){

    }
    @FXML
    public void convertButtonClicked(){
        if(!initialized){
            initialization();
        }
    }

    @Override
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        super.changeView(fxml, clientConnectionView);
    }

    @Override
    public void blockView() {

    }

    @Override
    public void printModel() {
        super.printModel();
    }

    @Override
    public void disableAllMoves() {
        super.disableAllMoves();
    }

    @Override
    public void initialization() {
        super.initialization();
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

    }

    @Override
    public void discardLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }

    @Override
    public void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos, int num) {

    }

    @Override
    public void manageResourceMarketConvert(int first, int second, int executePlayerPos) {

    }

    @Override
    public void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove) {

    }

    @Override
    public void manageEndTurn(boolean correctlyEnded, int executePlayerPos, String message) {

    }

    @Override
    public void manageEndMatch() {

    }

    @Override
    public void updateModel(Match match, int playerPosition) {

    }

    @Override
    public void manageReconnection(String playerName) {

    }

    @Override
    public void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos) {

    }

    @Override
    public void askToDiscardTwoLeader(int numOfResource, int executePlayerPos) {

    }

    @Override
    public void askForData(String message, int executePlayerPos) {

    }

    @Override
    public void moveResourceResponse(int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse) {

    }

    @Override
    public void manageDisconnection(String playerName) {

    }

    @Override
    public void update(MoveResponse message) {

    }


}
