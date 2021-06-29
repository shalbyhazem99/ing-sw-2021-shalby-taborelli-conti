package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.view.ClientConnectionView;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.leaderCard.DiscardTwoLeaderCardsPlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class RegistrationController extends GenericController {

    @FXML
    private Text messageToAskData;
    @FXML
    private TextField dataTextField;
    @FXML
    private VBox askDataPane;

    @FXML
    private VBox discardTwoLeaderCardPane;


    @FXML
    Pane leader_card_1,leader_card_2,leader_card_3,leader_card_4;



    @Override
    public void update(MoveResponse message) {
        if(message instanceof SendModel) {
            match = ((SendModel) message).getMatch();
        }else {
            message.elaborateGUI(this);
        }
    }


    @Override
    public void askToDiscardTwoLeader(int numOfResource, int executePlayerPos) {
        changeImage(leader_card_1,match.getPlayerFromPosition(executePlayerPos).getLeaderCard(0).getImage(),"devcard_leadercard/");
        changeImage(leader_card_2,match.getPlayerFromPosition(executePlayerPos).getLeaderCard(1).getImage(),"devcard_leadercard/");
        changeImage(leader_card_3,match.getPlayerFromPosition(executePlayerPos).getLeaderCard(2).getImage(),"devcard_leadercard/");
        changeImage(leader_card_4,match.getPlayerFromPosition(executePlayerPos).getLeaderCard(3).getImage(),"devcard_leadercard/");
        discardTwoLeaderCardPane.setVisible(true);
    }

    @Override
    public void askForData(String message, int executePlayerPos) {
        messageToAskData.setText(message);
        discardTwoLeaderCardPane.setVisible(false);

    }

    @Override
    public void moveResourceResponse(int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse) {

    }

    public void onClickPlay(MouseEvent mouseEvent) throws IOException {
        notify(MessageMove.getInstance(dataTextField.getText()));
        askDataPane.setVisible(false);
    }

    public void onLeaderCardMouseClick(MouseEvent mouseEvent){
        Pane selectedPane = (Pane) mouseEvent.getSource();
        Pane leaderCards[]= {leader_card_1,leader_card_2,leader_card_3,leader_card_4};
        if(Integer.parseInt(selectedPane.getUserData().toString())==0) {
            if (Arrays.stream(leaderCards).filter(elem -> !elem.equals(selectedPane) && Integer.parseInt(elem.getUserData().toString()) != 0).count() < 2) {
                selectedPane.setUserData("1");
                selectedPane.scaleXProperty().setValue(1.2);
                selectedPane.scaleYProperty().setValue(1.2);
                selectedPane.scaleZProperty().setValue(1.2);
            }
            else {
                runDialog(Alert.AlertType.WARNING,"You have already selected two leader cards, unselect one of them");
            }
        }
        else {
            selectedPane.setUserData("0");
            selectedPane.scaleXProperty().setValue(1);
            selectedPane.scaleYProperty().setValue(1);
            selectedPane.scaleZProperty().setValue(1);
        }
    }

    public void onClickDiscard() throws IOException {
        ArrayList<Pane> leaderCards=new ArrayList<>();
        leaderCards.add(leader_card_1);
        leaderCards.add(leader_card_2);
        leaderCards.add(leader_card_3);
        leaderCards.add(leader_card_4);
        if (leaderCards.stream().filter(elem-> Integer.parseInt(elem.getUserData().toString()) != 0).count() ==2) {
            int first=-1;
            int second=-1;
            Pane[] selectedLeaderCards = leaderCards.stream().filter(elem-> Integer.parseInt(elem.getUserData().toString()) == 0).toArray(Pane[]::new);
            first = leaderCards.indexOf(selectedLeaderCards[0]);
            second = leaderCards.indexOf(selectedLeaderCards[1]);
            System.out.println("leader cards discarded: first:"+first+", second:"+second);
            changeView("primary",App.connection);
            notify(DiscardTwoLeaderCardsPlayerMove.getInstance(first, second, ResourceType.COIN, ResourceType.FAITH));
        }
        else {
            runDialog(Alert.AlertType.WARNING,"You have to select two leader cards");
        }
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
    public void manageReconnection(String playerName){
        try {
            changeView("primary", App.connection);
        }catch (Exception e){
            //todo:manage
        }
    }

    @Override
    public void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos) {

    }


    @Override
    public void manageDisconnection(String playerName) {

    }
}
