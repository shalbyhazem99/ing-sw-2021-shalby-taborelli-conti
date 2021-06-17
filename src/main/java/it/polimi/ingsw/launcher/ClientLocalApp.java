package it.polimi.ingsw.launcher;

import it.polimi.ingsw.view.ClientSoloCLI;
import it.polimi.ingsw.controller.GameManger;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MatchSolo;
import it.polimi.ingsw.model.Player;

import java.util.Scanner;

public class ClientLocalApp {

    public static void main(String[] args) {
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
