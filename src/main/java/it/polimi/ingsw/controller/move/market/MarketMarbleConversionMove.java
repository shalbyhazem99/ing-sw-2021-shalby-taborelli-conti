package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.Map;

public class MarketMarbleConversionMove extends PlayerMove {
    /**
     * Class which represent a {@link PlayerMove} which let the {@link Player} to convert white {@link it.polimi.ingsw.model.market.Marble}
     */
    private int first, second;

    public MarketMarbleConversionMove(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public static MarketMarbleConversionMove getInstance(int first, int second) {
        return new MarketMarbleConversionMove(first, second);
    }
    @Override
    public void execute(Match match) {
        match.marketMarbleConvertInteraction(first,second,getPlayer(),false);
    }
}
