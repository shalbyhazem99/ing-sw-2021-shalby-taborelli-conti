package it.polimi.ingsw.controller.move.development;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.gui.GenericController;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.util.ArrayList;
import java.util.Scanner;

//todo:refactor nome sbagliato
public class BuyDevelopmentCardReponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} asks to buy a specific {@link DevelopmentCard}
     */
    private DevelopmentCardType type;
    private DevelopmentCardLevel level;
    private int posToAdd;
    private ArrayList<ResourcePick> resourceToUse;

    public BuyDevelopmentCardReponse(ArrayList<Player> players, int executePlayerPos, DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse,int hashToVerify) {
        super(players, executePlayerPos,hashToVerify);
        this.type = type;
        this.level = level;
        this.posToAdd = posToAdd;
        this.resourceToUse = resourceToUse;
    }

    public static BuyDevelopmentCardReponse getInstance(ArrayList<Player> players, int executePlayerPos, DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse,int hashToVerify) {
        return new BuyDevelopmentCardReponse(players, executePlayerPos, type, level, posToAdd, resourceToUse,hashToVerify);
    }


    @Override
    public void updateLocalMatch(Match match) {
        match.buyDevelopmentCardInteraction(type,level,match.getPlayerFromPosition(getExecutePlayerPos()),posToAdd,resourceToUse,true);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        return null;
    }

    @Override
    public void elaborateGUI(GenericController controller) {
        controller.buyDevelopmentCard(type,level,posToAdd,resourceToUse,getExecutePlayerPos());
    }
}
