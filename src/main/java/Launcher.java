import it.polimi.ingsw.connection.Server;
import it.polimi.ingsw.connection.view.ClientConnectionViewMulti;
import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.launcher.ClientApp;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MatchSolo;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.Utils;
import it.polimi.ingsw.view.ClientCLI;
import it.polimi.ingsw.view.ClientSoloCLI;
import it.polimi.ingsw.view.gui.App;
import it.polimi.ingsw.view.gui.AppLocal;

import java.io.IOException;
import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) throws IOException {
        int type = 0; //0 server, 1 client
        int port = 0; // automatically generated
        int viewType = 0; // 0 cli 1 gui
        int viewMode = 0; // 0 local 1 distributed
        String address = "127.0.0.1";
        boolean valid_parameter = true;
        for (int i = 0; i < args.length && valid_parameter; i++) {
            if (args[i].equals("--type")) {
                if (args[i + 1].equalsIgnoreCase("c")) {
                    type = 1;
                    i++;
                } else if (args[i + 1].equalsIgnoreCase("s")) {
                    type = 0;
                    i++;
                } else {
                    valid_parameter = false;
                    break;
                }
            } else if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("--gui")) {
                viewType = 1;
            } else if (args[i].equals("--cli")) {
                viewType = 0;
            } else if (args[i].equals("--local")) {
                viewMode = 1;
            } else if (args[i].equals("--address")) {
                address = args[i + 1];
                i++;
            } else {
                valid_parameter = false;
            }
        }

        if (!valid_parameter) {
            System.err.println("Invalid parameter");
        } else {
            Utils.port = port;
            Utils.ip = address;
            if (type == 0) { //server
                Server server;
                try {
                    server = new Server(port);
                    server.run();
                } catch (IOException e) {
                    System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
                }
            } else {//client
                if(viewType== 1){//gui
                    if(viewMode==1){//distributed
                        App.main(args);
                    }else { //local
                        AppLocal.main(args);
                    }
                }
                else { //cli
                    if(viewMode==1){//distributed
                        ClientConnectionViewMulti clientConnectionViewMulti = new ClientConnectionViewMulti(Utils.ip,  Utils.port);
                        ClientCLI client = new ClientCLI(new Scanner(System.in));
                        clientConnectionViewMulti.addObserver(client);
                        client.addObserver(clientConnectionViewMulti);
                        clientConnectionViewMulti.asyncReadFromSocket();
                    }else { //local
                        Scanner stdin = new Scanner(System.in);
                        System.out.println("Welcome!\nWhat is your name?");
                        Player player = Player.getInstance(stdin.nextLine());
                        //model (M)
                        Match match = new MatchSolo();
                        match.addPlayer(player);
                        //controller (C)
                        GameManger gameManger = GameManger.getInstance(match);
                        //view (V)
                        ClientSoloCLI localView = new ClientSoloCLI(stdin,match);
                        //observers
                        match.addObserver(localView);  //localView (view) observe the match (model)
                        localView.addObserver(gameManger);  //gameManger (controller) observe localView (view)
                        match.startMatch();
                    }
                }
            }
        }
    }
}
