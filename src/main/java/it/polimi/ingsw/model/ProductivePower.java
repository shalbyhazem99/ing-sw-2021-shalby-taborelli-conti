package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductivePower implements Serializable {
    private java.util.ArrayList<ResourcesCount> from;
    private java.util.ArrayList<Resource> to;

    public ProductivePower(ArrayList<ResourcesCount> from, ArrayList<Resource> to) {
        this.from = from;
        this.to = to;
    }

    public static ProductivePower getInstance(ArrayList<ResourcesCount> from, ArrayList<Resource> to){
        return new ProductivePower(from,to);
    }

    public ArrayList<ResourcesCount> getFrom() {
        return (ArrayList<ResourcesCount>) from.clone();
    }

    public ArrayList<Resource> getTo() {
        return (ArrayList<Resource>) to.clone();
    }


    @Override
    public String toString() {
        String s = Utils.formatResourcesCount(from);
        s += " ▶ ";
        s += Utils.formatResourcesCount(Utils.fromResourcesToResourceCount(to));
        return s;//from.toString()+"-->"+to.toString();
    }
}
