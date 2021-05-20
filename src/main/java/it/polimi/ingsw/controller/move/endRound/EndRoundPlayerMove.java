package it.polimi.ingsw.controller.move.endRound;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;


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
            match.endRoundInteraction(getPlayer(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
