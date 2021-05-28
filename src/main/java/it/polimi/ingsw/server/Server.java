package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.controller.move.settings.resilience.DisconnectionMove;
import it.polimi.ingsw.controller.move.settings.resilience.ReconnectionMove;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MatchMulti;
import it.polimi.ingsw.model.MatchSolo;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 64999;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private Map<String, ClientConnection> waitingConnection = new HashMap<>();

    //for connection resilience
    private Map<String, GameManger> playerReference = new HashMap<>();


    private Map<String, RemoteView> playingConnection = new HashMap<>();


    private int numPlayer = -1;

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection clientConnection) {
        Iterator<String> iterator = playingConnection.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (playingConnection.get(key).getClientConnection() == clientConnection) {
                RemoteView playerView = playingConnection.get(key);
                playerView.removeObserver(playerReference.get(key));
                //match.removeObserver(playerView);
                playerReference.get(key).update(DisconnectionMove.getInstance(key, playerView));
                iterator.remove();
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

    public int getNumPlayer() {
        return numPlayer;
    }

    private void manageReconnection(ClientConnection clientConnection, String name) {
        playerReference.get(name).update(ReconnectionMove.getInstance(name, playerReference.get(name), clientConnection, playingConnection));
    }

    public synchronized void lobby(SocketClientConnection socketClientConnection, String name, int numOfPlayer) {
        this.numPlayer = numOfPlayer;
        lobby(socketClientConnection, name);
    }

    //Wait for another player
    public synchronized void lobby(ClientConnection c, String name) {
        waitingConnection.put(name, c);
        //if the player is playing in another match
        if (playerReference.get(name) != null) {
            manageReconnection(c, name);
            waitingConnection.remove(name);
            return;
        }
        System.out.println("user:" + name + " registered!\n");
        if (waitingConnection.size() == numPlayer) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Match match;
            //multi or solo match
            if (numPlayer >= 2)
                match = new MatchMulti(numPlayer);
            else
                match = new MatchSolo();
            GameManger gameManger = GameManger.getInstance(match);
            for (String key : keys) {
                ClientConnection clientConnection = waitingConnection.get(key);
                Player player = Player.getInstance(key);
                match.addPlayer(player);
                RemoteView playerView = new RemoteView(player, clientConnection);
                match.addObserver(playerView);
                playerView.addObserver(gameManger);
                //Save data for Resilience
                playerReference.put(key, gameManger);
                playingConnection.put(key, playerView);
                //if match turn send something async
                clientConnection.asyncSend(SendMessage.getInstance("Game starts!\n", new ArrayList<>(), 0, match.hashCode()));
            }
            match.startMatch();
            waitingConnection.clear();
            numPlayer = -1;
        } else {
            c.asyncSend(SendMessage.getInstance("Waiting for other to join!\n", new ArrayList<>(), 0, 0));
        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(0);
        System.out.println("listening on port: " + serverSocket.getLocalPort());
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection;
                socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

}
