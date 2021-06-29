package it.polimi.ingsw.model;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class used to represent the Productive Power of a {@link it.polimi.ingsw.model.leaderCard.LeaderCard}
 */
public class ProductivePower implements Serializable {
    private java.util.ArrayList<ResourcesCount> from;
    private java.util.ArrayList<Resource> to;

    /**
     * Constructor
     * @param from ArrayList of {@link ResourcesCount} of the {@link Resource} needed to activate the production
     * @param to ArrayList of {@link Resource} of the products of the power
     */
    public ProductivePower(ArrayList<ResourcesCount> from, ArrayList<Resource> to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Create an instance of the Productive Power
     * @param from ArrayList of {@link ResourcesCount} of the {@link Resource} needed to activate the production
     * @param to ArrayList of {@link Resource} of the products of the power
     * @return an instance of the Productive Power
     */
    public static ProductivePower getInstance(ArrayList<ResourcesCount> from, ArrayList<Resource> to){
        return new ProductivePower(from,to);
    }

    /**
     *
     * @return ArrayList of {@link ResourcesCount} of the {@link Resource} needed to activate the production
     */
    public ArrayList<ResourcesCount> getFrom() {
        return from;
    }

    /**
     *
     * @return ArrayList of {@link Resource} of the products of the power
     */
    public ArrayList<Resource> getTo() {
        return to;
    }


    @Override
    public String toString() {
        String s = Utils.formatResourcesCount(from);
        s += " ▶ ";
        s += Utils.formatResourcesCount(Utils.fromResourcesToResourceCount(to));
        return s;//from.toString()+"-->"+to.toString();
    }
}
