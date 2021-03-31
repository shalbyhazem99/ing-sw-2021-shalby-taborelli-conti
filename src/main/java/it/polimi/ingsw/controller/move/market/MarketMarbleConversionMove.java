package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class MarketMarbleConversionMove extends PlayerMove {
    private ArrayList<ResourceType> convertionStrategyList;
    public MarketMarbleConversionMove(String name_of_user,ArrayList<ResourceType> convertionStrategyList)
    {
        super(name_of_user);
        this.convertionStrategyList =  convertionStrategyList;
    }
    @Override
    public void execute(Match match) {
        match.marketMarbleConvert(convertionStrategyList);
    }
}
