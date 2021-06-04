package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AskForData extends MoveResponse {
    private String message;

    public AskForData(String message, ArrayList<Player> players,int executePlayerPos,int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
        this.message = message;
    }

    public static AskForData getInstance(String message,ArrayList<Player> players,int executePlayerPos,int hashToVerify) {
        return new AskForData(message,players,executePlayerPos,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.askForData(message,getExecutePlayerPos());
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        System.out.println(message);
        return MessageMove.getInstance(stdin.nextLine());
    }

    @Override
    public String toString() {
        return message;
    }
}
