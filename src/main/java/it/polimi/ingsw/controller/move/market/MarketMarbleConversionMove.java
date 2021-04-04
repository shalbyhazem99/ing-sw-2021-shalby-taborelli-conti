package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class MarketMarbleConversionMove extends PlayerMove {
    /**
     * Class which represent a {@link PlayerMove} which let the {@link Player} to convert white {@link it.polimi.ingsw.model.market.Marble}
     */
    private ArrayList<ResourceType> conversionStrategyList;
    /**
     * Default constructor
    * @param conversionStrategyList an {@link ArrayList} of {@link ResourceType} representing how to convert each white {@link it.polimi.ingsw.model.market.Marble}
     */
    public MarketMarbleConversionMove(ArrayList<ResourceType> conversionStrategyList)
    {
        this.conversionStrategyList = conversionStrategyList;
    }
    @Override
    public void execute(Match match) {
        match.marketMarbleConvertInteraction(conversionStrategyList);
    }
}
