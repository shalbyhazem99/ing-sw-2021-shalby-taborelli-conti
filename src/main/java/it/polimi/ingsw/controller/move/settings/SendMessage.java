package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SendMessage extends MoveResponse {
    private String message;

    public SendMessage(String message, ArrayList<Player> players,int executePlayerPos,int hashToVerify) {
        super(players,executePlayerPos,hashToVerify);
        this.message = message;
    }

    public static SendMessage getInstance(String message, ArrayList<Player> players,int executePlayerPos,int hashToVerify) {
        return new SendMessage(message,players,executePlayerPos,hashToVerify);
    }
    public static SendMessage getInstance(String message, Player player,int executePlayerPos,int hashToVerify) {
        return new SendMessage(message, new ArrayList<>(Arrays.asList(player)),executePlayerPos,hashToVerify);
    }

    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }

    @Override
    public String toString() {
        return message;
    }
}
