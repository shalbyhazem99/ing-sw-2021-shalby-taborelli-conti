package it.polimi.ingsw.launcher;

import it.polimi.ingsw.view.ClientCLI;
import it.polimi.ingsw.connection.view.ClientConnectionView;
import it.polimi.ingsw.utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp
{
    public static void main(String[] args) throws IOException {
        //todo: ask the connection port, ask connectiontype(cli, grafica), locale o cosa
        ClientConnectionView clientConnectionView = new ClientConnectionView(Utils.ip,  Utils.port);
        ClientCLI client = new ClientCLI(new Scanner(System.in));
        clientConnectionView.addObserver(client);
        client.addObserver(clientConnectionView);
        clientConnectionView.asyncReadFromSocket();
    }
}
