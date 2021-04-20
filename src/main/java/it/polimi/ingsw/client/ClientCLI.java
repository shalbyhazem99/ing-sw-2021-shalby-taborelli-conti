package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientCLI {

    final private String ip;
    final private int port;
    private Match match;
    public ClientCLI(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final Scanner stdin, final ObjectInputStream socketIn, final ObjectOutputStream socketOut) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        //System.err.println("message recived...");
                        //todo: something to print the match
                        Object inputObject = socketIn.readObject();
                        if (inputObject instanceof SendModel) {
                            SendModel sendModel = ((SendModel) inputObject);
                            match = sendModel.getMatch();
                            //rotate player Array so that my player is the first
                            ArrayList<Player> players = match.getPlayers();
                            for (int i = 0; i < sendModel.getPlayerPosition(); i++) {
                                Player player= players.get(0);
                                players.remove(0);
                                players.add(player);
                            }
                            System.out.println(match.toString());
                            //manageResponse((MoveResponse) inputObject, stdin, socketOut);
                        } else if (inputObject instanceof SendMessage) {
                            System.out.println(inputObject.toString());
                        } else if (inputObject instanceof MoveResponse) {
                            manageResponse((MoveResponse) inputObject, stdin, socketOut);
                        } else if (inputObject instanceof String) {
                            System.out.println(inputObject.toString());
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void manageResponse(MoveResponse moveResponse, final Scanner stdin, final ObjectOutputStream socketOut) {
        try {
            socketOut.writeObject(moveResponse.elaborateCliInput(stdin,match));
            socketOut.flush();
        } catch (Exception e) {
            setActive(false);
        }

    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket(stdin, socketIn, socketOut);
            t0.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            socketOut.close();
            socketIn.close();
            socket.close();
        }
    }

}
