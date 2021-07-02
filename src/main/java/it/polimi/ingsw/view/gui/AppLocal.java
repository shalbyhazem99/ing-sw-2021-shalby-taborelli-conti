package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.view.ClientConnectionViewSolo;
import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MatchSolo;
import it.polimi.ingsw.model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class AppLocal extends Application {

    private static Scene scene;
    private static ClientConnectionViewSolo connection;

    public static synchronized ClientConnectionViewSolo getConnection(){
        return connection;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //model (M)
        Match match = new MatchSolo();
        match.setWhoAmI(0);
        //controller (C)
        GameManger gameManger = GameManger.getInstance(match);
        //view (V)
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/welcome.fxml"));
        Parent root =loader.load();
        GenericController myController = loader.getController();
        connection = new ClientConnectionViewSolo(gameManger);
        //observers
        match.addObserver(myController);  //localView (view) observe the match (model)
        myController.addObserver(connection);
        connection.addObserver(myController);
        myController.match = match;
        //start match
        //match.startMatch();
        ((WelcomeController)myController).start();
        scene = new Scene(root, 1141, 982);
        scene.setCamera(new PerspectiveCamera());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    static void setRoot(Parent root) throws IOException {
        scene.setRoot(root);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}