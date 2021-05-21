package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientCLI;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args){
        //todo: ask the connection port, ask connectiontype(cli, grafica), locale o cosa
        //System.out.println("Insert the server port");
        ClientCLI client = new ClientCLI("127.0.0.1",  51214);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
