package it.polimi.ingsw.connection.view;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.endMatch.EndMatchResponse;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class ClientConnectionView extends Observable<MoveResponse> implements Observer<PlayerMove> {

}
