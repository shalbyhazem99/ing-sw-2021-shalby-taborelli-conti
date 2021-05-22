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
import java.util.Arrays;
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
    public static final int DEV_CARD_ROW_NUMBER = 3;
    public static final int DEV_CARD_COL_NUMBER = 4;
    public static final int MARKET_COL_NUMBER = 4;
    public static final int MARKET_ROW_NUMBER = 3;
    public static final String DISABLE_MOVE = " (Insert -1 to come back to move menu)";


    /**
     * compare two {@link ArrayList} to see if the first include the second one
     *
     * @param a the first {@link ArrayList}
     * @param b the second {@link ArrayList}
     * @return true if a include b, false otherwise
     */
    public static boolean compareResources(ArrayList<Resource> a, ArrayList<ResourcesCount> b) {
        return a.containsAll(b.stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toList()));
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

    public static ResourceType[] getUsableResourcesType(){
        return Arrays.stream(ResourceType.values())
                .filter(elem -> !(elem.equals(ResourceType.ANY) || elem.equals(ResourceType.FAITH)))
                .collect(Collectors.toList()).toArray(ResourceType[]::new);
    }

    /**
     * Method to ask client from where he wants to get the resources for some operation
     *
     * @param resourcesCounts
     * @param stdin
     * @param match
     * @return
     */
    public static ArrayList<ResourcePick> getRequiredResourceFrom(ArrayList<ResourcesCount> resourcesCounts, Scanner stdin, Match match) {
        ArrayList<ResourcePick> resourcePicks = new ArrayList<>();
        for (ResourcesCount resourcesCount : resourcesCounts) {
            ArrayList<Resource> resourceArrayList = resourcesCount.toArrayListResources();
            for (Resource res: resourceArrayList) {
                ResourceType resType = res.getType();
                System.out.println("From where you get " + Utils.resourceTypeToString(res.getType()) + "(0-> Warehouse, 1-> Strongbox)");
                switch (stdin.nextInt()) {
                    case 0:
                        int position;
                        boolean valid_parameter;
                        do {
                            System.out.println("insert the position of the warehouse (0,...,4)");
                            valid_parameter = true;
                            position = stdin.nextInt();
                            if (position < 0 || position > 4) {
                                valid_parameter = false;
                                System.err.println("Error, index not valid!");
                            }
                        } while (!valid_parameter);
                        if (position < 3)
                            resourcePicks.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, position, match.getCurrentPlayer().getWarehousesStandard().get(position).getResourceType()));
                        else
                            resourcePicks.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, position, match.getCurrentPlayer().getWarehousesAdditional().get(position-3).getResourceType()));
                        break;
                    case 1:
                        if (res.getType().equals(ResourceType.ANY)) {
                            ResourceType[] resourceTypes = getUsableResourcesType();
                            int index;
                            do {

                                System.out.print("Which type of resource you want to use ( ");
                                for (int j = 0; j < resourceTypes.length; j++) {
                                    System.out.print(j + "->" + Utils.resourceTypeToString(resourceTypes[j]) + "   ");
                                }
                                System.out.println(")");
                                index = stdin.nextInt();
                            } while (index < 0 || index >= resourceTypes.length);
                            resType=resourceTypes[index];
                        }
                        resourcePicks.add(ResourcePick.getInstance(ResourceWarehouseType.STRONGBOX, 0, resType));
                        break;
                    default:
                        System.err.println("Error, index not valid!");
                        break;
                }
            }
        }
        return resourcePicks;
    }

    public static int getMaxLengthStringDevCard(ArrayList<DevelopmentCard> devcard) {
        int max = devcard.get(0).getCostsFormatted().length();
        for (int i = 0; i < devcard.size(); i++) {
            String u = devcard.get(i).getPowersFormatted();
            String uu = devcard.get(i).getCostsFormatted();
            if (u.length() > max) {
                max = u.length();
            }
            if (uu.length() > max) {
                max = uu.length();
            }
        }
        return max;
    }

    public static ArrayList<DevelopmentCard> getColour(Stack<DevelopmentCard>[][] dev, DevelopmentCardType type) {
        ArrayList<DevelopmentCard> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            temp.add(dev[i][type.label].peek());
        }
        return temp;
    }

    public static String fillSpaces(int l1, int l2) {
        String s = "";
        for (int i = 0; i < (l1 - l2); i++) {
            s = s + " ";
        }
        return s;
    }

    public static ArrayList<ResourcesCount> fromResourcesToResourceCount(ArrayList<Resource> resources) {
        ArrayList<ResourcesCount> temp = new ArrayList<>();
        ResourcesCount coin = new ResourcesCount(0, ResourceType.COIN);
        ResourcesCount faith = new ResourcesCount(0, ResourceType.FAITH);
        ResourcesCount shield = new ResourcesCount(0, ResourceType.SHIELD);
        ResourcesCount stone = new ResourcesCount(0, ResourceType.STONE);
        ResourcesCount servant = new ResourcesCount(0, ResourceType.SERVANT);
        ResourcesCount any = new ResourcesCount(0, ResourceType.ANY);
        temp.add(coin);
        temp.add(faith);
        temp.add(shield);
        temp.add(stone);
        temp.add(servant);
        temp.add(any);
        for (int i = 0; i < resources.size(); i++) {
            switch (resources.get(i).getType()) {
                case COIN:
                    coin.addCount();
                    break;
                case FAITH:
                    faith.addCount();
                    break;
                case STONE:
                    stone.addCount();
                    break;
                case ANY:
                    any.addCount();
                    break;
                case SHIELD:
                    shield.addCount();
                    break;
                case SERVANT:
                    servant.addCount();
                    break;
            }
        }
        return temp;
    }

    public static String resourceTypeToString(ResourceType r) {
        switch (r) {
            case COIN:
                return "⚫";
            case FAITH:
                return "✝";
            case SERVANT:
                return "⚔";
            case ANY:
                return "A";
            case SHIELD:
                return "\uD83D\uDEE1️";
            case STONE:
                return "\uD83D\uDC8E";
        }
        return null;
    }

    public static String formatResourcesCount(ArrayList<ResourcesCount> costs) {
        String str = "[";
        for (int i = 0; i < costs.size(); i++) {
            switch (costs.get(i).getType()) {
                case COIN:
                    if (costs.get(i).getCount() != 0) str = str + " " + costs.get(i).getCount() + "⚫";
                    break;
                case FAITH:
                    if (costs.get(i).getCount() != 0) str = str + " " + costs.get(i).getCount() + "✝";
                    break;
                case SERVANT:
                    if (costs.get(i).getCount() != 0) str = str + " " + costs.get(i).getCount() + "⚔";
                    break;
                case ANY:
                    if (costs.get(i).getCount() != 0) str = str + " " + costs.get(i).getCount() + "A";
                    break;
                case SHIELD:
                    if (costs.get(i).getCount() != 0) str = str + " " + costs.get(i).getCount() + "\uD83D\uDEE1️";
                    break;
                case STONE:
                    if (costs.get(i).getCount() != 0) str = str + " " + costs.get(i).getCount() + "\uD83D\uDC8E";
                    break;
            }
        }
        str = str + "]";
        return str;
    }
}
