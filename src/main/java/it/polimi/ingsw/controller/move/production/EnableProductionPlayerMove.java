package it.polimi.ingsw.controller.move.production;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.exceptions.NotEnoughResources;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.util.ArrayList;

public class EnableProductionPlayerMove extends PlayerMove {
    /**
     * Class representing the {@link PlayerMove} that lets the {@link Player} to enable a set of {@link ProductivePower}
     */
    ArrayList<ProductivePower> productivePowers;
    ArrayList<Integer> devCardProductivePlayerSelected = null;

    /**
     * Default constructor
     * @param player {@link Player} which performs the {@link PlayerMove}
     * @param defaultProductivePowers {@link ArrayList} of {@link ProductivePower} containing what we want to enable
     * @param devCardProductivePlayerSelected {@link ArrayList} of {@link Integer} containing the indexes of the {@link ProductivePower} of the {@link it.polimi.ingsw.model.developmentCard.DevelopmentCard}
     */
    public EnableProductionPlayerMove(Player player, ArrayList<ProductivePower> defaultProductivePowers,ArrayList<Integer> devCardProductivePlayerSelected) {
        super(player);
        this.productivePowers = (ArrayList<ProductivePower>) defaultProductivePowers.clone();
        this.devCardProductivePlayerSelected = (ArrayList<Integer>) devCardProductivePlayerSelected.clone();
    }

    @Override
    public void execute(Match match) {
        try{
            match.enableProductionInteraction(productivePowers,devCardProductivePlayerSelected,getPlayer());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
