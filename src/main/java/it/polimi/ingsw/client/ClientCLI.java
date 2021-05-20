package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientCLI {

    final private String ip;
    final private int port;
    private Match match;
    private int playerPos;

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
                        Object inputObject = socketIn.readObject();
                        if (inputObject instanceof SendModel) {
                            SendModel sendModel = ((SendModel) inputObject);
                            match = sendModel.getMatch();
                            playerPos = sendModel.getPlayerPosition();
                            match.toString();
                            System.out.flush();
                        } else if (inputObject instanceof SendMessage) {
                            System.out.println(inputObject.toString());
                        } else if (inputObject instanceof MoveResponse) {
                            MoveResponse moveResponse = (MoveResponse) inputObject;
                            //update local match
                            moveResponse.updateLocalMatch(match);
                            //verify the model correctness
                            if (match != null) {
                                match.toString();
                                System.out.flush();
                                if (match.hashCode() != moveResponse.getHashToVerify()) {
                                    //todo to complete (response and move)
                                    System.err.println("Different model received");
                                }
                            }
                            if (moveResponse.getExecutePlayerPos() == playerPos) {
                                manageResponse(moveResponse, stdin, socketOut);
                            }
                        } else if (inputObject instanceof String) {
                            System.out.println(inputObject.toString());
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void manageResponse(MoveResponse moveResponse, final Scanner stdin, final ObjectOutputStream socketOut) {
        try {
            PlayerMove playerMove = moveResponse.elaborateCliInput(stdin, match);
            if (playerMove != null) {
                socketOut.writeObject(playerMove);
            }
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
