package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.response.IllegalMoveResponse;
import it.polimi.ingsw.controller.move.settings.AskForData;
import it.polimi.ingsw.controller.move.settings.AskForMove;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Class that manage the connection and the interaction with the client
 */
public class SocketClientConnection extends Observable<PlayerMove> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
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
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            send(AskForData.getInstance("Welcome!\nWhat is your name?",null));
            String name = ((MessageMove) in.readObject()).getMessage();
            //todo: generates error if two client run together
            if(server.getNumPlayer()==-1){
                send(AskForData.getInstance("You are the first. how many player do you want?",null));
                numOfPlayer = Integer.parseInt(((MessageMove) in.readObject()).getMessage());
                send(SendMessage.getInstance("match created!\n",new ArrayList<>()));
                server.lobby(this, name,numOfPlayer);
            }else {
                server.lobby(this, name);
            }
            Object readed;
            while(isActive()){
                readed = in.readObject();
                if(readed instanceof PlayerMove){
                    notify((PlayerMove)readed);
                }else {
                    asyncSend(IllegalMoveResponse.getInstance("risposta scorretta",null));
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
