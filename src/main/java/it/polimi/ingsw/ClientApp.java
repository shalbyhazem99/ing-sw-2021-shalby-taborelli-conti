package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientCLI;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args){
        ClientCLI client = new ClientCLI("127.0.0.1", 51953);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
