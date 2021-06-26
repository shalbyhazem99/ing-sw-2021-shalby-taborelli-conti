package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.view.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AskForMove extends MoveResponse {

    ArrayList<MovePlayerType> possibleMove;

    public AskForMove(ArrayList<Player> players, ArrayList<MovePlayerType> possibleMove, int executePlayerPos, int hashToVerify) {
        super(players, executePlayerPos, hashToVerify);
        this.possibleMove = possibleMove;
    }

    public static AskForMove getInstance(ArrayList<Player> players, ArrayList<MovePlayerType> possibleMove, int executePlayerPos, int hashToVerify) {
        return new AskForMove(players, possibleMove, executePlayerPos, hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.manageAllowedMoves(possibleMove);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        PlayerMove playerMove = null;
        do {
            System.out.println("Choose from the following move:");
            for (int i = 0; i < possibleMove.size(); i++) {
                System.out.println(i + ") " + possibleMove.get(i).getDescription());
            }

            int move;
            do {
                move = stdin.nextInt();
            } while (move >= possibleMove.size() || move < 0);
            playerMove = possibleMove.get(move).elaborateMoveForCLI(stdin, match);
        } while (playerMove == null);
        return playerMove;
    }
}
