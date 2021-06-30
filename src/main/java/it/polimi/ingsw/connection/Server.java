package it.polimi.ingsw.connection;

import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.controller.move.settings.resilience.DisconnectionMove;
import it.polimi.ingsw.controller.move.settings.resilience.ReconnectionMove;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MatchMulti;
import it.polimi.ingsw.model.MatchSolo;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.connection.view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Manage the Server connection part
 */
public class Server {
    private final ServerSocket serverSocket;
    private final Map<String, ClientConnection> waitingConnection = new HashMap<>();
    //for connection resilience
    private final Map<String, GameManger> playerReference = new HashMap<>();
    private final Map<String, RemoteView> playerConnection = new HashMap<>();
    private int numPlayer = -1;
    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection clientConnection, boolean endGame) {

        Iterator<String> iterator = playerConnection.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (playerConnection.get(key).getClientConnection() == clientConnection) {
                RemoteView playerView = playerConnection.get(key);
                playerView.removeObserver(playerReference.get(key));
                //match.removeObserver(playerView);
                playerReference.get(key).update(DisconnectionMove.getInstance(key, playerView));
                iterator.remove();
                if(endGame){
                    playerReference.remove(key);
                }
                break;
            }
        }

        //if still in the waiting connection
        iterator = waitingConnection.keySet().iterator();
        while (iterator.hasNext()) {
            if (waitingConnection.get(iterator.next()) == clientConnection) {
                iterator.remove();
            }
        }
        if (waitingConnection.isEmpty()) {
            numPlayer = -1;
        }
    }

    public synchronized int  getNumPlayer() {
        return numPlayer;
    }

    public synchronized void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    /**
     * Add a player to the lobby
     * @param clientConnection player {@link ClientConnection}
     * @param name the player name
     * @param numOfPlayer the dimension of the game the player want to start
     */
    public synchronized void lobby(ClientConnection clientConnection, String name, int numOfPlayer) {
        this.numPlayer = numOfPlayer;
        lobby(clientConnection, name);
    }

    /**
     * Add a player to the lobby
     * @param c player {@link ClientConnection}
     * @param name the player name
     */
    public synchronized void lobby(ClientConnection c, String name) {
        waitingConnection.put(name, c);
        System.out.println("user: " + name + " registered!\n");
        if (waitingConnection.size() == getNumPlayer()) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Match match;
            if (keys.size() >= 2)
                match = new MatchMulti(numPlayer); //match multi
            else
                match = new MatchSolo(); //match solo
            GameManger gameManger = GameManger.getInstance(match);
            for (String key : keys) {
                //add the player to the match
                Player player = Player.getInstance(key);
                match.addPlayer(player);
                //create MVC structure
                ClientConnection clientConnection = waitingConnection.get(key);
                RemoteView playerView = new RemoteView(player, clientConnection);
                match.addObserver(playerView);  //the view observe the player view
                playerView.addObserver(gameManger);  // the controller observe the view
                //Save data for Resilience
                playerReference.put(key, gameManger);
                playerConnection.put(key, playerView);
                //if match turn send something async
                clientConnection.asyncSend(SendMessage.getInstance("Game starts!\n", new ArrayList<>(), 0, match.hashCode()));
            }
            match.startMatch();
            waitingConnection.clear();
            setNumPlayer(-1);
        } else {
            c.asyncSend(SendMessage.getInstance("Waiting for other to join!\n", new ArrayList<>(), 0, 0));
        }
    }

    /**
     * check if a player was already playing in a game
     * @param name the player name to control
     * @return true if the player is registered in a game false otherwise
     */
    public synchronized  boolean wasAlreadyPlaying(String name){
        return playerReference.get(name)!= null;
    }

    public synchronized void manageReconnection(ClientConnection clientConnection, String name) {
        playerReference.get(name).update(ReconnectionMove.getInstance(name, playerReference.get(name), clientConnection, playerConnection));
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(0);
        System.out.println("listening on port: " + serverSocket.getLocalPort());
    }

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("listening on port: " + serverSocket.getLocalPort());
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                new Thread(socketConnection).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
