package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.DiscardLeaderCardPlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public abstract class GenericController extends Observable<PlayerMove> implements Observer<MoveResponse> {

    protected Match match;
    public void changeView(String fxml, ClientConnection clientConnection) throws IOException {
        URL url = new File("src/main/resources/fxml/"+fxml+".fxml").toURI().toURL();
        System.out.println(fxml);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root =loader.load();
        App.setRoot(root);
        GenericController myController = loader.getController();
        myController.match = this.match;
        //add the clientConnection
        myController.addObserver(clientConnection);
        //add controller to notify the connection
        clientConnection.addObserver(myController);
        if(myController instanceof PrimaryController)
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
        }
    }
    //abstact method to modify view
    public abstract void blockView();

    public void printModel(){};
}
