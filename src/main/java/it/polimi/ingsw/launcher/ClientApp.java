package it.polimi.ingsw.launcher;

import it.polimi.ingsw.view.ClientCLI;
import it.polimi.ingsw.connection.view.ClientConnectionViewMulti;
import it.polimi.ingsw.utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp
{
    public static void main(String[] args) throws IOException {
        ClientConnectionViewMulti clientConnectionViewMulti = new ClientConnectionViewMulti(Utils.ip,  Utils.port);
        ClientCLI client = new ClientCLI(new Scanner(System.in));
        clientConnectionViewMulti.addObserver(client);
        client.addObserver(clientConnectionViewMulti);
        clientConnectionViewMulti.asyncReadFromSocket();
    }
}
