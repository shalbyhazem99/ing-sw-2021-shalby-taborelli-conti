package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Stack;

public class DevelopmentCardSpace implements Serializable {
    private Stack<DevelopmentCard> carteSviluppo;

    public DevelopmentCardSpace() {
        this.carteSviluppo = new Stack<>();
    }

    public static DevelopmentCardSpace getInstance() {
        return new DevelopmentCardSpace();
    }
    public boolean add(DevelopmentCard developmentCard){
        //TODO: FA LA push se possibile
        return false;
    }
}
