package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;

import java.util.ArrayList;

public class EnableProductionPlayerMove implements PlayerMove {
    ArrayList<ProductivePower> productivePowers;
    @Override
    public void exectute(Match match) {
        try {
            match.enableProductionMove(productivePowers);
        }catch (NotEnoughResources e){
            e.printStackTrace();
        }
    }
}
