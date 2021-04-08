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


    @Override
    public void execute(Match match) {
        //forse da pensare meglio per sfruttare meglio ereditarietà, in teoria si potrebbe togleire l'if
        try
        {
            if( (match instanceof MatchMulti))
            {
                MatchMulti matchMulti = (MatchMulti) match;
                matchMulti.endRoundInteraction(getPlayer());
            }
            else if((match instanceof MatchSolo))
            {
                MatchSolo matchSolo = (MatchSolo) match;
                matchSolo.endRoundInteraction(getPlayer());
            }
            else
            {
                match.illegalPlayerMoveInteraction("error not multi",player);
                return;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
