package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AskForMove extends MoveResponse {

    ArrayList<MovePlayerType> possibleMove;

    public AskForMove(ArrayList<Player> players, ArrayList<MovePlayerType> possibleMove,int executePlayerPos) {
        super(players,executePlayerPos);
        this.possibleMove = possibleMove;
    }

    public static AskForMove getInstance(ArrayList<Player> players, ArrayList<MovePlayerType> possibleMove,int executePlayerPos) {
        return new AskForMove(players, possibleMove,executePlayerPos);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        PlayerMove playerMove = null;
        do {
            System.out.println("Choose from the following move:");
            for (int i = 0; i < possibleMove.size(); i++) {
                System.out.println(i + ") " + possibleMove.get(i).getDescription());
            }
            //todo:controlli sull'int
            int move = stdin.nextInt();
            playerMove = possibleMove.get(move).elaborateMoveForCLI(stdin, match);
        } while (playerMove == null);
        return playerMove;
    }
}
