package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.ClientConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static  ClientConnection connection;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = new File("src/main/resources/fxml/register.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root =loader.load();
        GenericController myController = loader.getController();
        connection = new ClientConnection("127.0.0.1",  63556);
        myController.addObserver(connection);
        connection.addObserver(myController);
        connection.asyncReadFromSocket();

        scene = new Scene(root, 1255, 993);
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
        connection.disconnect();
    }

    public static void main(String[] args) {
        launch();
    }
}