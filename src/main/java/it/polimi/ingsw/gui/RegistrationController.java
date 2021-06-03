package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.observer.Observer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class RegistrationController extends GenericController {

    @FXML
    private TextArea textAreaName;

    public void onClick(MouseEvent mouseEvent) {
        notify(MessageMove.getInstance(textAreaName.getText()));
        //change view
        /*Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        // OR
        Stage stageTheLabelBelongs = (Stage) label.getScene().getWindow();
        // these two of them return the same stage
        // Swap screen
        stage.setScene(new Scene(new Pane()));*/

    }

    @Override
    public void update(MoveResponse message) {


    }

    public void onClickPlay(MouseEvent mouseEvent) throws IOException {
        changeView("primary",App.connection);
    }

    @Override
    public void blockView()
    {

    }
}
