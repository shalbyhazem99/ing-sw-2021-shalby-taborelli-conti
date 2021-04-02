package it.polimi.ingsw.controller.move.production;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.util.ArrayList;

public class EnableProductionPlayerMove extends PlayerMove {
    /**
     * Class representing the communication
     */
    ArrayList<ProductivePower> productivePowers;
    ArrayList<Integer> devCardProductivePlayerSelected = null;

    public EnableProductionPlayerMove(String name_of_user, ArrayList<ProductivePower> productivePowers) {
        super(name_of_user);
        this.productivePowers = (ArrayList<ProductivePower>) productivePowers.clone();
    }
    public EnableProductionPlayerMove(String name_of_user, ArrayList<ProductivePower> defaultProductivePowers,ArrayList<Integer> devCardProductivePlayerSelected) {
        super(name_of_user);
        this.productivePowers = (ArrayList<ProductivePower>) defaultProductivePowers.clone();
        this.devCardProductivePlayerSelected = (ArrayList<Integer>) devCardProductivePlayerSelected.clone();
    }

    @Override
    public void execute(Match match) {

    }
}
