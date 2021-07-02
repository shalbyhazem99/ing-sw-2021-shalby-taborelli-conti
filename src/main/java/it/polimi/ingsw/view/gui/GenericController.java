package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.view.ClientConnectionView;
import it.polimi.ingsw.connection.view.ClientConnectionViewMulti;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class GenericController extends Observable<PlayerMove> implements Observer<MoveResponse> {

    protected Match match;

    /**
     * Method to change view
     * @param fxml file to load
     * @param clientConnectionView {@link ClientConnectionView}
     * @throws IOException throws an {@link IOException}
     */
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/"+fxml+".fxml"));
        Parent root = loader.load();
        try {
            App.setRoot(root);
        }catch (Exception e){
            AppLocal.setRoot(root);
        }
        //remove observer
        GenericController myController = loader.getController();
        myController.match = this.match;
        //add the clientConnection
        myController.addObserver(this.getObservers());
        //add controller to notify the connection
        clientConnectionView.setObserver(myController);

        try{match.addObserver(myController);}catch(Exception e){}
        myController.initialization();
        if(match!=null && !match.getCurrentPlayer().equals(match.getPlayerFromPosition(match.getWhoAmI())))
            myController.disableAllMoves();
    }

    /**
     * Change the image
     * @param p pane to be changed
     * @param s string of the path
     * @param type type of string
     */
    public void changeImage(Pane p, String s, String type) {
        URL url = null;
        try {
            url = getClass().getResource("/images/" + type + s + ".png");
        } catch (Exception e) {
        }
        Image image = new Image(url.toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage myBI = new BackgroundImage(new Image(url.toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background r = new Background(myBI);
        p.setBackground(r);
    }

    public void changeImage(Pane p, String urlS) {
        URL url = null;
        try {
            url = getClass().getResource(urlS);
        } catch (Exception e) {
        }
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage myBI = new BackgroundImage(new Image(url.toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background r = new Background(myBI);
        p.setBackground(r);
    }


    /**
     * Method to run dialog
     * @param type {@link javafx.scene.control.Alert.AlertType} type of dialog
     * @param message that must be printed
     */
    public void runDialog(Alert.AlertType type, String message) {
        Platform.runLater(() -> {
            Alert dialog = new Alert(type, message, ButtonType.OK);
            dialog.show();
        });

    }

    //abstact method to modify view
    public abstract void blockView();

    public void printModel() {
    }

    ;

    public void disableAllMoves() {
    }

    public void initialization() {
    }

    public abstract void printMessage(String message);

    public abstract void flipLeaderCard(int leaderCardPosition, int executePlayerPos);

    public abstract void enableProduction(ProductivePower power, ArrayList<ResourcePick> resourceToUse, int executePlayerPos);

    public abstract void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos);

    public abstract void discardLeaderCard(int leaderCardPosition, int executePlayerPos);

    public abstract void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos, int numOfWhiteMarble);

    public abstract void manageResourceMarketConvert(int first, int second, int executePlayerPos);

    public abstract void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove);

    public abstract void manageEndTurn(boolean correctlyEnded, int executePlayerPos, String message);

    public abstract void manageEndMatch();

    public abstract void updateModel(Match match, int playerPosition);

    public abstract void manageReconnection(String playerName);

    public abstract void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos);

    public abstract void askToDiscardTwoLeader(int numOfResource, int executePlayerPos);

    public abstract void askForData(String message, int executePlayerPos);

    public abstract void moveResourceResponse(int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse);

    public abstract void manageDisconnection(String playerName);
}
