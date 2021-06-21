package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.view.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SendModel extends MoveResponse {
    Match match;
    int playerPosition;

    public SendModel(Match match, Player player, int playerPosition,int hashToVerify) {
        super(new ArrayList<>(Arrays.asList(player)),playerPosition,hashToVerify);
        this.match = match;
        this.playerPosition = playerPosition;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public static SendModel getInstance(Match match, Player player, int playerPosition,int hashToVerify) {
        return new SendModel(match,player,playerPosition,hashToVerify);
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public void updateLocalMatch(Match match) {

    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.updateModel(match,playerPosition);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        //TODO: require which to discard
        return null;
    }

    public Match getMatch() {
        match.setWhoAmI(playerPosition);
        return match;
    }
}
