package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<PlayerMove> observer);

    void asyncSend(Object message);
}
