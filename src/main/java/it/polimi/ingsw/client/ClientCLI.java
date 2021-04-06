package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
                            match = ((SendModel) inputObject).getMatch();
                            manageResponse((MoveResponse) inputObject, stdin, socketOut);
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
            System.out.println(moveResponse.toString());
            String inputLine = stdin.nextLine();
            socketOut.writeObject(moveResponse.elaborateCliInput(inputLine, stdin));
            socketOut.flush();
        } catch (Exception e) {
            setActive(false);
        }

    }

    public Thread asyncWriteToSocket(final Scanner stdin, final ObjectOutputStream socketOut) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        String inputLine = stdin.nextLine();
                        socketOut.writeObject(inputLine);
                        socketOut.flush();
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket(stdin, socketIn, socketOut);
            //Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            //t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            socketOut.close();
            socketIn.close();
            socket.close();
        }
    }

}