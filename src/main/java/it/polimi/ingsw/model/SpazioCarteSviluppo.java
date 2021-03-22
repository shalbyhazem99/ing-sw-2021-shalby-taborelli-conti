package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Stack;

public class SpazioCarteSviluppo {
    private Stack<CartaSviluppo> carteSviluppo;

    public SpazioCarteSviluppo() {
        this.carteSviluppo = new Stack<>();
    }

    public static SpazioCarteSviluppo getInstance() {
        return new SpazioCarteSviluppo();
    }
    public boolean add(CartaSviluppo cartaSviluppo){
        //TODO: FA LA push se possibile
        return false;
    }
}
