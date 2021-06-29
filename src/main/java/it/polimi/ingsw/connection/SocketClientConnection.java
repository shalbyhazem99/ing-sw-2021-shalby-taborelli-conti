package it.polimi.ingsw.connection;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.endMatch.EndMatchResponse;
import it.polimi.ingsw.controller.move.response.IllegalMoveResponse;
import it.polimi.ingsw.controller.move.settings.AskForData;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that manage the connection and the interaction with the client
 */
public class SocketClientConnection extends Observable<PlayerMove> implements ClientConnection, Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private final Server server;
    private static final Object syncObj = new Object();
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(() -> send(message)).start();
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if(message instanceof EndMatchResponse){
            close(true);
        }

    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private synchronized void close(boolean endGame) {
        closeConnection();
        System.out.println("Unregistering client...");
        server.deregisterConnection(this,endGame);
        System.out.println("Done!");
    }

    @Override
    public void run() {
        int numOfPlayer;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            //ask the player name
            send(AskForData.getInstance("Welcome!\nWhat is your name?", null, 0, 0));
            String name = ((MessageMove) in.readObject()).getMessage();
            //check if the player was already playing
            if (server.wasAlreadyPlaying(name)) {
                server.manageReconnection(this, name);
            } else { // start a new match or add him to an already created match
                synchronized (syncObj) {
                    if (server.getNumPlayer() == -1) {
                        send(AskForData.getInstance("You are the first. how many player do you want?", null, 0, 0));
                        numOfPlayer = Math.min(4, Math.max(1, Integer.parseInt(((MessageMove) in.readObject()).getMessage()))); //the player number must be between 1 and 4
                        send(SendMessage.getInstance("match created!\n", new ArrayList<>(), 0, 0));
                        server.lobby(this, name, numOfPlayer);
                    } else {
                        server.lobby(this, name);
                    }
                }
            }

            Object readied;
            while (isActive()) {
                readied = in.readObject();
                if (readied instanceof PlayerMove) {
                    notify((PlayerMove) readied);
                } else {
                    asyncSend(IllegalMoveResponse.getInstance("WRONG ANSWERS", null, 0, 0));
                }
            }
        } catch (Exception e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close(false);
        }
    }
}