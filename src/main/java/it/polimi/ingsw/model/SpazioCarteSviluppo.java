package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Stack;

public class DevelopmentCardSpace {
    private Stack<DevelopmentCard> developmentCard;

    public DevelopmentCardSpace() {
        this.developmentCard = new Stack<>();
    }

    public static DevelopmentCardSpace getInstance() {
        return new DevelopmentCardSpace();
    }
    public boolean add(CartaSviluppo developmentCard){
        //TODO: makes the push if it can
        return false;
    }
}
