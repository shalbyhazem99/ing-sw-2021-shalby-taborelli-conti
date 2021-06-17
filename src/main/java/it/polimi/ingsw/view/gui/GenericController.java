package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.ClientConnectionView;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class GenericController extends Observable<PlayerMove> implements Observer<MoveResponse> {

    protected Match match;
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        URL url = new File("src/main/resources/fxml/"+fxml+".fxml").toURI().toURL();
        System.out.println(fxml);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root =loader.load();
        App.setRoot(root);
        GenericController myController = loader.getController();
        myController.match = this.match;
        //add the clientConnection
        myController.addObserver(clientConnectionView);
        //add controller to notify the connection
        clientConnectionView.addObserver(myController);
        myController.initialization();
        myController.disableAllMoves();
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
    //abstact method to modify view
    public abstract void blockView();

    public void printModel(){};

    public void disableAllMoves(){}

    public void initialization(){}

    public abstract void printMessage(String message);

    public abstract void flipLeaderCard(int leaderCardPosition, int executePlayerPos);

    public abstract void enableProduction(ProductivePower power, ArrayList<ResourcePick> resourceToUse, int executePlayerPos);

    public abstract void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos);

    public abstract void discardLeaderCard(int leaderCardPosition, int executePlayerPos);

    public abstract void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos);

    public abstract void manageResourceMarketConvert(int first, int second, int executePlayerPos);

    public abstract void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove);

    public abstract void manageEndTurn(boolean correctlyEnded, int executePlayerPos, String message);

    public abstract void manageEndMatch();

    public abstract void updateModel(Match match, int playerPosition);

    public abstract void manageReconnection(String playerName);

    public abstract void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos);

    public abstract void askToDiscardTwoLeader(int numOfResource, int executePlayerPos);

    public abstract void askForData(String message, int executePlayerPos);

    public abstract void moveResourceResponse(int numberOfResourcesMoved, int indexFirstWarehouse, int indexSecondWarehouse);

    public abstract void manageDisconnection(String playerName);
}
