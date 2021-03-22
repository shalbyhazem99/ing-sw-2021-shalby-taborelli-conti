package it.polimi.ingsw.model;

import java.util.ArrayList;

public class ProductivePower {
    private java.util.ArrayList<CountResources> from;
    private java.util.ArrayList<Resource> to;

    public ProductivePower(ArrayList<CountResources> from, ArrayList<Resource> to) {
        this.from = from;
        this.to = to;
    }

    public static ProductivePower getInstance(ArrayList<CountResources> from, ArrayList<Resource> to){
        return new ProductivePower(from,to);
    }
}
