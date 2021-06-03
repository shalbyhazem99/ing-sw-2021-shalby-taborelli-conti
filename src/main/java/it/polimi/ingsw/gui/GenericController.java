package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public abstract class GenericController extends Observable<PlayerMove> implements Observer<MoveResponse> {

    public void changeView(String fxml, ClientConnection clientConnection) throws IOException {
        URL url = new File("src/main/resources/fxml/"+fxml+".fxml").toURI().toURL();
        System.out.println(fxml);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root =loader.load();
        GenericController myController = loader.getController();
        //add the clientConnection
        myController.addObserver(clientConnection);
        //add controller to notify the connection
        clientConnection.addObserver(myController);
        App.setRoot(root);

    }
    //abstact method to modify view
}
