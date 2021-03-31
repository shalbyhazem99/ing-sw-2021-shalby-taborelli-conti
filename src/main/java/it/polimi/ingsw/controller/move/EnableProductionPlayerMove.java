package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;

import java.util.ArrayList;

public class EnableProductionPlayerMove extends PlayerMove {
    ArrayList<ProductivePower> productivePowers;
    @Override
    public void execute(Match match) {
        try {
            match.enableProductionMove(productivePowers);
        }catch (NotEnoughResources e){
            e.printStackTrace();
        }
    }
    public EnableProductionPlayerMove(String name_of_user)
    {
        super(name_of_user);
    }
}
