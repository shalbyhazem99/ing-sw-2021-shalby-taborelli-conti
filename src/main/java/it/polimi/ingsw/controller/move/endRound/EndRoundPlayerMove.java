package it.polimi.ingsw.controller.move.endRound;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.EndRoundException;
import it.polimi.ingsw.exceptions.SwapWarehouseException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MatchMulti;
import it.polimi.ingsw.model.MatchSolo;

import java.util.ArrayList;
import java.util.Arrays;

public class EndRoundPlayerMove extends PlayerMove {
    /**
     * Class representing the {@link PlayerMove} performed by the {@link it.polimi.ingsw.model.Player} in order to end the round
     */


    public static EndRoundPlayerMove getInstance() {
        return new EndRoundPlayerMove();
    }

    @Override
    public void execute(Match match) {
        try {
            match.endRoundInteraction(getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
