package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.production.move.ResourceWarehouseType;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

/**
 * describe a set of methods that are util for the whole project
 */
public class Utils {

    /**
     * Connection info
     */
    public static String ip = "127.0.0.1";
    public static int port = 62941;

    /**
     * the Faith path length
     */
    public static final int FAITH_LENGTH = 24;
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
        return a != null && b != null && a.containsAll(b.stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toList()));
    }

    /**
     * compare two {@link ArrayList} to see if the first is equals to the second one
     *
     * @param a the first {@link ArrayList}
     * @param b the second {@link ArrayList}
     * @return true if a equals to b, false otherwise
     */
    public static boolean compareResourcesEquals(ArrayList<Resource> a, ArrayList<Resource> b) {
        return a != null && b != null && a.size()== b.size() && Utils.fromResourcesToResourceCount(a).containsAll(Utils.fromResourcesToResourceCount(b));
    }




    /**
     * apply the discount to an {@link ArrayList} of ResourcesCounts
     *
     * @param resourcesCounts {@link ArrayList} of ResourcesCount to discount
     * @param discount        arraylist of discount to apply
     * @return a new (clone) {@link ArrayList} of the discount applied
     */
    public static ArrayList<ResourcesCount> applyDiscount(ArrayList<ResourcesCount> resourcesCounts, ArrayList<ResourcesCount> discount) {
        ArrayList<ResourcesCount> discounted = ResourcesCount.cloneList(resourcesCounts);
        discount.stream()
                .forEach(x -> {
                    discounted.stream()
                            .filter(elem -> elem.getType().equals(x.getType()))
                            .findFirst().ifPresent(elem -> elem.decreaseCount());
                });
        //remove the resourcesCount with 0 count: may create problems
        return (ArrayList<ResourcesCount>) discounted.stream().filter(elem -> elem.getCount() > 0).collect(Collectors.toList());
    }

    /**
     * Convert an array of mixed {@link Resource} into an ArrayList of {@link ResourcesCount}
     *
     * @param resources to convert into ResourceCount
     * @return the ArrayList of {@link ResourcesCount} containing all the {@link Resource} passed
     */
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
        for (int i = 0; resources != null && i < resources.size(); i++) {
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

    /**
     * Method to convert an ArrayList of {@link ResourcesCount} to an ArrayList of {@link Resource}
     * @param resourcesCounts ArrayList of {@link ResourcesCount} to convert
     * @return an ArrayList of {@link Resource} containing all the {@link Resource} int the ArrayList of {@link ResourcesCount}
     */
    public static ArrayList<Resource> fromResourceCountToResources(ArrayList<ResourcesCount> resourcesCounts) {
        ArrayList<Resource> temp = new ArrayList<>();
        for (ResourcesCount r : resourcesCounts) {
            for (int i = 0; i < r.getCount(); i++) {
                temp.add(Resource.getInstance(r.getType()));
            }
        }
        return temp;
    }

    /**
     * Method to convert an ArrayList of {@link ResourcePick} to an ArrayList of {@link Resource}
     * @param res ArrayList of {@link ResourcePick} to convert
     * @return an ArrayList of {@link Resource} containing all the {@link Resource} int the ArrayList of {@link ResourcePick}
     */
    public static ArrayList<Resource> fromResourcePickToResources(ArrayList<ResourcePick> res) {
        ArrayList<Resource> temp = new ArrayList<>();
        for (ResourcePick r : res) {
            temp.add(Resource.getInstance(r.getResourceType()));
        }
        return temp;
    }


    public static ResourceType[] getUsableResourcesType() {
        return Arrays.stream(ResourceType.values())
                .filter(elem -> !(elem.equals(ResourceType.ANY) || elem.equals(ResourceType.FAITH)))
                .collect(Collectors.toList()).toArray(ResourceType[]::new);
    }

    public static String mapResTypeToImage(ResourceType a) {
        return a.toString().toLowerCase(Locale.ROOT) + "_c";
    }

    /**
     * Method to ask client from where he wants to get the resources for some operation
     *
     * @param resourcesCounts {@link ResourcesCount} to convert
     * @param stdin passing a scanner of the input
     * @param match {@link Match} where is using the method
     * @return ArrayList of {@link ResourcePick} the equivalent {@link ResourcesCount} passed
     */
    public static ArrayList<ResourcePick> getRequiredResourceFrom(ArrayList<ResourcesCount> resourcesCounts, Scanner stdin, Match match) {
        ArrayList<ResourcePick> resourcePicks = new ArrayList<>();
        for (ResourcesCount resourcesCount : resourcesCounts) {
            ArrayList<Resource> resourceArrayList = resourcesCount.toArrayListResources();
            for (Resource res : resourceArrayList) {
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
                            resourcePicks.add(ResourcePick.getInstance(ResourceWarehouseType.WAREHOUSE, position, match.getCurrentPlayer().getWarehousesAdditional().get(position - 3).getResourceType()));
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
                            resType = resourceTypes[index];
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

    /**
     * Method to know which is the maximum length of the toString of an ArrayList of {@link DevelopmentCard}
     * @param devcard ArrayList of the {@link DevelopmentCard} to count the length
     * @return the maximum length of the string that can be generated from toString of {@link DevelopmentCard}
     */
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

    /**
     * Method to obtain an ArrayList of {@link DevelopmentCard}, all with the {@link DevelopmentCardType} given, from the DevelopmentCard Market
     * @param dev DevelopmentCard market
     * @param type {@link DevelopmentCardType} of the {@link DevelopmentCard} to get
     * @return an ArrayList of {@link DevelopmentCard}, all with the {@link DevelopmentCardType} given, from the DevelopmentCard Market
     */
    public static ArrayList<DevelopmentCard> getColour(Stack<DevelopmentCard>[][] dev, DevelopmentCardType type) {
        ArrayList<DevelopmentCard> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            temp.add(dev[i][type.label].peek());
        }
        return temp;
    }

    /**
     * Method used to fill the empty space during a call of toString
     * @param l1 Index of the first space to fill
     * @param l2 Index of the last space to fill
     * @return a string of the given length containing empty spaces
     */
    public static String fillSpaces(int l1, int l2) {
        String s = "";
        for (int i = 0; i < (l1 - l2); i++) {
            s = s + " ";
        }
        return s;
    }

    /**
     * Method to convert into string the symbol of a {@link Resource}
     * @param r {@link ResourceType} to convert
     * @return the equivalent string of the {@link ResourceType} given
     */
    public static String resourceTypeToString(ResourceType r) {
        return r.symbol;
    }

    /**
     * Method to format the cost of an ArrayList of {@link ResourcesCount}
     * @param costs ArrayList of {@link ResourcesCount} to convert
     * @return String with the equivalent conversion of the ArrayList of {@link ResourcesCount} given
     */
    public static String formatResourcesCount(ArrayList<ResourcesCount> costs) {
        String str = "[";
        for (int i = 0; i < costs.size(); i++) {
            if (costs.get(i).getCount() != 0)
                str = str + " " + costs.get(i).getCount() + costs.get(i).getType().symbol;
        }
        str = str + "]";
        return str;
    }

    /**
     * Method use to convert the word of a {@link ResourceType} contained in a string into the equivalent {@link ResourceType}
     * @param s string to control
     * @return equivalent {@link ResourceType} contained in the string given
     */
    public static ResourceType getResourceTypeFromUrl(String s) {
        if (s.contains("coin")) {
            return ResourceType.COIN;
        } else if (s.contains("shield")) {
            return ResourceType.SHIELD;
        } else if (s.contains("servant")) {
            return ResourceType.SERVANT;
        } else if (s.contains("stone")) {
            return ResourceType.STONE;
        }
        return null;

    }

    /**
     * Method used to convert the {@link MarbleColor} into the equivalent Color used to represent in in the GUI
     * @param marbleColor {@link MarbleColor} to convert
     * @return the equivalent color used in the GUI
     */
    public static Color fromMarbleColorToJavaFXColor(MarbleColor marbleColor) {
        Color color = null;
        if (marbleColor == MarbleColor.BLUE) {
            color = Color.AQUA;
        } else if (marbleColor == MarbleColor.RED) {
            color = Color.RED;
        } else if (marbleColor == MarbleColor.GREY) {
            color = Color.GREY;
        } else if (marbleColor == MarbleColor.PURPLE) {
            color = Color.PURPLE;
        } else if (marbleColor == MarbleColor.YELLOW) {
            color = Color.YELLOW;
        } else if (marbleColor == MarbleColor.WHITE) {
            color = Color.WHITE;
        }
        return color;
    }

}
