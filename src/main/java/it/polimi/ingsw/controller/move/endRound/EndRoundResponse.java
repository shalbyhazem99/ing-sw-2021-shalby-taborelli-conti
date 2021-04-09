package it.polimi.ingsw.controller.move.endRound;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class EndRoundResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link Player} wants to swap two {@link it.polimi.ingsw.model.Warehouse}
     * The numberOfResourcesMoved represents how many {@link Resource} have been totally moved between the two {@link it.polimi.ingsw.model.Warehouse}
     */
    private boolean correctlyEnded;

    public EndRoundResponse(ArrayList<Player> players, boolean correctlyEnded) {
        super(players);
        this.correctlyEnded = correctlyEnded;
    }


    public static EndRoundResponse getInstance(ArrayList<Player> players, boolean correctlyEnded) {
        return new EndRoundResponse(players,correctlyEnded);
    }

    @Override
    public PlayerMove elaborateCliInput( Scanner stdin) {
        return null;
    }
}
