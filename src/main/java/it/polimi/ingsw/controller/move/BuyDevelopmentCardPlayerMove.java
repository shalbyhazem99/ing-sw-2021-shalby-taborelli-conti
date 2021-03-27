package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

public class BuyDevelopmentCardPlayerMove implements PlayerMove {
    DevelopmentCardType type;
    DevelopmentCardLevel level;

    @Override
    public void exectute(Match match) {
        try {
            match.buyDevelopmentCard(type, level);
        }catch(NotEnoughResources e){
            e.printStackTrace();
            //todo:ask to make another move
        }
    }
}
