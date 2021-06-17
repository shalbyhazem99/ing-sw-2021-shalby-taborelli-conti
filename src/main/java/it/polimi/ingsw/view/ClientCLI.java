package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.endMatch.EndMatchResponse;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.Scanner;

public class ClientCLI extends Observable<PlayerMove> implements Observer<MoveResponse> {

    private Match match;
    private int playerPos;
    private final Scanner stdin;

    public ClientCLI(Scanner stdin) {
        this.stdin = stdin;
    }

    private void manageResponse(MoveResponse moveResponse, final Scanner stdin) {
        PlayerMove playerMove = moveResponse.elaborateCliInput(stdin, match);
        if (playerMove != null) {
            notify(playerMove);
        }
    }

    private synchronized void elaborateResponse(MoveResponse message){
        if (message instanceof SendModel) {
            SendModel sendModel = ((SendModel) message);
            match = sendModel.getMatch();
            playerPos = sendModel.getPlayerPosition();
            match.setWhoAmI(playerPos);
            System.out.println(match.toString());
            System.out.flush();
        } else if (message instanceof EndMatchResponse) {
            System.out.println("match End!");
            manageResponse(message, stdin);
        } else if (message instanceof MoveResponse) {
            //update local match
            message.updateLocalMatch(match);
            //verify the model correctness
            if (match != null) {
                match.setWhoAmI(playerPos);
                System.out.println(match.toString());
                System.out.flush();
                if (match.hashCode() != message.getHashToVerify()) {
                    //todo to complete (response and move)
                    System.err.println("Different model received");
                }
            }
            if (message.getExecutePlayerPos() == playerPos) {
                manageResponse(message, stdin);
            }
        }
    }


    @Override
    public void update(MoveResponse message) {
        elaborateResponse(message);
    }
}
