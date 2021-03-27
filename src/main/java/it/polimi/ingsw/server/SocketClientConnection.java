package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.response.IllegalMoveResponse;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
/**
 * Class that manage the connection and the interaction with the client
 */
public class SocketClientConnection extends Observable<PlayerMove> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    ObjectInputStream in;
    private Server server;
    private boolean isFirstPlayer;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server,boolean isFirstPlayer) {
        this.socket = socket;
        this.server = server;
        this.isFirstPlayer = isFirstPlayer;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
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

    private void close() {
        //todo: manage diconnection: maybe add active variable to player
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        int numOfPlayer;
        try{
            //todo: to be modified so that all the request send will be as Response class
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            send("Welcome!\nWhat is your name?");
            String name = (String) in.readObject();
            if(isFirstPlayer){
                send("You are the first. how many player do you want?");
                numOfPlayer = in.readInt();
                server.lobby(this, name,numOfPlayer);
            }else {
                server.lobby(this, name);
            }
            Object readied;
            while(isActive()){
                readied = in.readObject();
                if(readied instanceof PlayerMove){
                    notify((PlayerMove)readied);
                }else {
                    asyncSend(IllegalMoveResponse.getInstance());
                }
            }
        } catch (Exception e) {
            System.err.println("Error!" + e.getMessage());
            //TODO:MAYBE INFORM THE CLIENT THAT SOMETHING WRONG HAPPEND
        }finally{
            close();
        }
    }
}
