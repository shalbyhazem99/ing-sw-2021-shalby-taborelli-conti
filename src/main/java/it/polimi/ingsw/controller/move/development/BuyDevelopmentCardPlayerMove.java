package it.polimi.ingsw.controller.move.development;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

public class BuyDevelopmentCardPlayerMove extends PlayerMove {
    DevelopmentCardType type;
    DevelopmentCardLevel level;
    int posToAdd;

    public BuyDevelopmentCardPlayerMove(String name_of_user, DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd) {
        super(name_of_user);
        this.type = type;
        this.level = level;
        this.posToAdd = posToAdd;
    }

    @Override
    public void execute(Match match) {
        try {
            match.buyDevelopmentCardInteraction(type, level,getName_of_user(),posToAdd);
        }catch(NotEnoughResources e){
            e.printStackTrace();
            //todo:ask to make another move
        }
    }
}
