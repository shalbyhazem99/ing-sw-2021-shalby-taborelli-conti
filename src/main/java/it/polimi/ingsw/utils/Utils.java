package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.production.move.ResourceWarehouseType;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * describe a set of methods that are util for the whole project
 */
public class Utils {
    /**
     * the Faith path length
     */
    public static final int FAITH_LENGTH = 20;
    public static final int DEV_CARD_ROW_NUMBER=3;
    public static final int DEV_CARD_COL_NUMBER=4;
    public static final int MARKET_COL_NUMBER = 4;
    public static final int MARKET_ROW_NUMBER = 3;



    /**
     * compare two {@link ArrayList} to see if the first include the second one
     *
     * @param a the first {@link ArrayList}
     * @param b the second {@link ArrayList}
     * @return true if a include b, false otherwise
     */
    public static boolean compareResources(ArrayList<Resource> a, ArrayList<ResourcesCount> b) {
        return a.containsAll (b.stream().flatMap(elem->elem.toArrayListResources().stream()).collect(Collectors.toList()));
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

    /**
     * Method to ask client from where he wants to get the resources for some operation
     * @param resourcesCounts
     * @param stdin
     * @param match
     * @return
     */
    public static ArrayList<ResourcePick> getRequiredResourceFrom(ArrayList<ResourcesCount> resourcesCounts, Scanner stdin, Match match) {
        ArrayList<ResourcePick> resourcePicks = new ArrayList<>();
        for (ResourcesCount resourcesCount : resourcesCounts) {
            for (int i = 0; i < resourcesCount.getCount(); i++) {
                System.out.println("From where you get " + resourcesCount.getType()+ "(0-> Warehouse, 1-> Strongbox)");
                switch (stdin.nextInt()) {
                    case 0:
                        int position;
                        boolean valid_parameter;
                        do {
                            System.out.println("insert the position of the warehouse (0,...,4)");
                            valid_parameter = true;
                            position = stdin.nextInt();
                            if(position<0||position>4)
                            {
                                valid_parameter = false;
                                System.err.println("Error, index not valid!");
                            }
                        } while (!valid_parameter);
                        resourcePicks.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, position, resourcesCount.getType()));
                        break;
                    case 1:
                        resourcePicks.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, resourcesCount.getType()));
                        break;
                }
            }
        }
        return resourcePicks;
    }
}
