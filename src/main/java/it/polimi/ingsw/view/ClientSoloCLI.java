package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.Scanner;

public class ClientSoloCLI extends Observable<PlayerMove> implements Observer<MoveResponse> {
    private Match match;
    private int playerPos;
    Scanner stdin;

    private synchronized void manageResponse(MoveResponse inputObject) {
        if (inputObject instanceof SendModel) {
            SendModel sendModel = ((SendModel) inputObject);
            match = sendModel.getMatch();
            playerPos = sendModel.getPlayerPosition();
            match.setWhoAmI(playerPos);
            System.out.println(match.toString());
            System.out.flush();
        } else {
            MoveResponse moveResponse = (MoveResponse) inputObject;
            //update local match
            //moveResponse.updateLocalMatch(match); not needed it's always the same
            //verify the model correctness
            if (match != null) {
                match.setWhoAmI(0);
                System.out.println(match.toString());
                System.out.flush();
                if (moveResponse.getExecutePlayerPos() == playerPos) {
                    PlayerMove playerMove = moveResponse.elaborateCliInput(stdin, match);
                    if (playerMove != null) {
                        playerMove.concatPlayer(match.getPlayerFromPosition(0));
                        notify(playerMove);
                    }
                }
            }
        }
    }

    @Override
    public void update(MoveResponse message) {
        manageResponse(message);
    }

    public ClientSoloCLI(Scanner stdin, Match match) {
        this.stdin = stdin;
        this.match = match;
    }
}
