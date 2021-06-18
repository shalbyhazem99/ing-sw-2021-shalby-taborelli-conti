package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.ClientConnectionView;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;


public class RegistrationController extends GenericController {


    @FXML
    private TextArea textAreaName;

    public void onClick(MouseEvent mouseEvent) {
        notify(MessageMove.getInstance(textAreaName.getText()));
    }

    @Override
    public void update(MoveResponse message) {
        if(message instanceof SendModel)
        {
            match = ((SendModel) message).getMatch();
        }
    }

    public void onClickPlay(MouseEvent mouseEvent) throws IOException {
        changeView("primary",App.connection);
        notify(MessageMove.getInstance(textAreaName.getText()));
    }

    @Override
    public void blockView() {

    }

    @Override
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        super.changeView(fxml, clientConnectionView);
    }

    @Override
    public void printModel() {
        super.printModel();
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
    public void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos) {

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
    public void moveResourceResponse(int numberOfResourcesMoved, int indexFirstWarehouse, int indexSecondWarehouse) {

    }

    @Override
    public void manageDisconnection(String playerName) {

    }
}
