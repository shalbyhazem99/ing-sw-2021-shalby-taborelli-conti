package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import javafx.beans.InvalidationListener;
import javafx.scene.input.MouseEvent;

public class PrimaryController extends GenericController {

    Match match;

    public void onClick(MouseEvent mouseEvent) {
        notify(MarketInteractionPlayerMove.getInstance(MoveType.ROW,0));
        //block everything
    }


    @Override
    public void update(MoveResponse message) {
        //elaborate message
        System.out.println("Message Recived");

        //
    }
}
