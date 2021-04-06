package it.polimi.ingsw.controller.move.development;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;

import java.util.Scanner;

public class BuyDevelopmentCardReponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} asks to buy a specific {@link DevelopmentCard}
     */
    private DevelopmentCard developmentCard;

    /**
     * Default constructor
     * @param developmentCard the {@link DevelopmentCard} that has to be retrieved to the {@link it.polimi.ingsw.model.Player}
     */
    public BuyDevelopmentCardReponse(DevelopmentCard developmentCard) {
        this.developmentCard = developmentCard;
    }

    /**
     * Default get instance method
     * @param developmentCard the {@link DevelopmentCard} that has to be retrieved to the {@link it.polimi.ingsw.model.Player}
     * @return an instance of {@link BuyDevelopmentCardReponse}
     */
    public static BuyDevelopmentCardReponse getInstance(DevelopmentCard developmentCard) {
        return new BuyDevelopmentCardReponse(developmentCard);
    }

    @Override
    public PlayerMove elaborateCliInput(String message, Scanner stdin) {
        return null;
    }
}
