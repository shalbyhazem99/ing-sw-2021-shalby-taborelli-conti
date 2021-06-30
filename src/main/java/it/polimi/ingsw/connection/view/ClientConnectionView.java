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

public class ClientConnectionView extends Observable<MoveResponse> implements Observer<PlayerMove> {

    private Socket socket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private boolean active = true;


    public ClientConnectionView(String ip, int port) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(ip, port));
        System.out.println("Connection established");
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        this.socketIn = new ObjectInputStream(socket.getInputStream());
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public void update(PlayerMove message) {
        //write the received PlayerMove to socket
        asyncWriteToSocket(message);
    }

    public void asyncWriteToSocket(PlayerMove playerMove) {
        new Thread(() -> {
            try {
                socketOut.writeObject(playerMove);
                socketOut.flush();
            } catch (Exception e) {
                setActive(false);
            }
        }).start();
    }


    public void asyncReadFromSocket() {
         new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = socketIn.readObject();
                    System.err.println(inputObject.getClass().toString());
                    if (inputObject instanceof SendModel) {
                        ClientConnectionView.this.notify((SendModel) inputObject);
                    }
                    else if(inputObject instanceof EndMatchResponse) {
                        setActive(false);
                        ClientConnectionView.this.disconnect();
                        ClientConnectionView.this.notify((MoveResponse) inputObject);
                    }
                    else if (inputObject instanceof MoveResponse) {
                        ClientConnectionView.this.notify((MoveResponse) inputObject);
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                setActive(false);
            }
        }).start();
    }


    public void disconnect() throws IOException {
        if (!socket.isClosed()) {
            socketOut.close();
            socketIn.close();
            socket.close();
        }
    }
}
