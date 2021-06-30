package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.view.ClientConnectionView;
import it.polimi.ingsw.utils.Utils;
import it.polimi.ingsw.view.gui.controller.WelcomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
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
    private static ClientConnectionView connection;

    public static synchronized ClientConnectionView getConnection(){
        return connection;
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = new File("src/main/resources/fxml/welcome.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root =loader.load();
        GenericController myController = loader.getController();
        connection = new ClientConnectionView(Utils.ip,  Utils.port);
        myController.addObserver(connection);
        connection.addObserver(myController);
        connection.asyncReadFromSocket();
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
        connection.disconnect();
    }

    public static void main(String[] args) {
        launch();
    }
}