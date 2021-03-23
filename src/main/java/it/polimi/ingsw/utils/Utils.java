package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourcesCount;

import java.util.ArrayList;

/**
 * describe a set of methods that are util for the whole project
 */
public class Utils {
    /**
     * the Faith path length
     */
    public static int FAITH_LENGTH = 20;


    /**
     * compare two {@link ArrayList} to see if the first include the second one
     *
     * @param a the first {@link ArrayList}
     * @param b the second {@link ArrayList}
     * @return true if a include b, false otherwise
     */
    public static boolean compareResources(ArrayList<Resource> a, ArrayList<ResourcesCount> b) {
        for (ResourcesCount res : b) {
            if (res.getCount() != a.stream().filter(elem -> elem.getType().equals(res.getType())).count())
                return false;
        }
        return true;
    }

    /**
     * apply the discount to an {@link ArrayList} of ResourcesCounts
     *
     * @param resourcesCounts {@link ArrayList} of ResourcesCount to discount
     * @param discount        arraylist of discount to apply
     * @return a new (clone) {@link ArrayList} of the discount aaplied
     */
    public static ArrayList<ResourcesCount> applyDiscount(ArrayList<ResourcesCount> resourcesCounts, ArrayList<ResourcesCount> discount) {
        ArrayList<ResourcesCount> elaborated = (ArrayList<ResourcesCount>) resourcesCounts.clone();
        for (ResourcesCount res : elaborated) {
            discount.stream().filter(elem -> elem.getType().equals(res.getType())).forEach(
                    elem -> {
                        //todo:add setCount to ResourceCount
                        //res.setCount(res.getCount()- elem.getCount());
                    }
            );
        }
        return elaborated;
    }
}
