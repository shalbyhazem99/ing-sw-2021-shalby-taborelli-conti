package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AskForMove extends MoveResponse {

    ArrayList<MovePlayerType> possibleMove;
    
    public AskForMove(ArrayList<Player> players,ArrayList<MovePlayerType> possibleMove) {
        super(players);
        this.possibleMove = possibleMove;
    }

    public static AskForMove getInstance(ArrayList<Player> players, ArrayList<MovePlayerType> possibleMove) {
        return new AskForMove(players,possibleMove);

    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin) {
        System.out.println("Choose from the following move:");
        for (int i = 0; i < possibleMove.size() ; i++) {
            System.out.println(i+") "+possibleMove.get(i).getDescription());
        }
        int move = stdin.nextInt();
        return possibleMove.get(move).elaborateMoveForCLI(stdin);
    }
}
