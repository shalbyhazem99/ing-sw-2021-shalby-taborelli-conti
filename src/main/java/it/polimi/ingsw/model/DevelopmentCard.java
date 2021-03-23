package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: final tutti gli attributi
public class DevelopmentCard implements Serializable {
    private final DevelopmentCardLevel level;
    private final DevelopmentCardType type;
    private final int equivalentPoint;
    private ArrayList<ResourcesCount> costs;
    private final ProductivePower powers;

    /**
     *
     * @param level {@link DevelopmentCardLevel} can be 0,1,2
     * @param type {@link DevelopmentCardType} can be green,blue,yellow,red
     * @param equivalentPoint point to be considered at the end of the game
     * @param costs {@link ArrayList} of {@link ResourcesCount} representing how much resources a player has to pay in order to buy the card
     * @param powers {@link ArrayList} of {@link ProductivePower} representing how the card can convert resources
     */
    public DevelopmentCard(DevelopmentCardLevel level, DevelopmentCardType type, int equivalentPoint, ArrayList<ResourcesCount> costs, ProductivePower powers) {
        this.level = level;
        this.type = type;
        this.equivalentPoint = equivalentPoint;
        this.costs = costs;
        this.powers = powers;
    }
    /**
     *
     * @param level {@link DevelopmentCardLevel} can be 0,1,2
     * @param type {@link DevelopmentCardType} can be green,blue,yellow,red
     * @param equivalentPoint point to be considered at the end of the game
     * @param costs {@link ArrayList} of {@link ResourcesCount} representing how much resources a player has to pay in order to buy the card
     * @param powers {@link ArrayList} of {@link ProductivePower} representing how the card can convert resources
     */
    public static DevelopmentCard getInstance(DevelopmentCardLevel level, DevelopmentCardType type, int equivalentPoint, ArrayList<ResourcesCount> costs, ProductivePower powers) {
        return new DevelopmentCard(level, type, equivalentPoint, costs, powers);
    }

    /**
     *
     * @return {@link DevelopmentCardLevel} card's level
     */
    public DevelopmentCardLevel getLevel() {
        return level;
    }

    /**
     *
     * @return {@link DevelopmentCardType} card's type
     */
    public DevelopmentCardType getType() {
        return type;
    }

    /**
     *
     * @return equivalentPoint
     */
    public int getEquivalentPoint() {
        return equivalentPoint;
    }

    /**
     *
     * @return {@link ProductivePower} representing the productive power's of the card
     */
    public ProductivePower getPowers() {
        return powers;
    }

    /**
     *
     * @return a shallow copy of {@link ArrayList} of {@link ResourcesCount} representing the card's costs
     */
    public ArrayList<ResourcesCount> getCosts() {
        return (ArrayList<ResourcesCount>) costs.clone();
    }
    //TODO: remove main
    public static void main (String [] args)
    {
        ArrayList<ResourcesCount> t = new ArrayList<>();
        t.add(new ResourcesCount(1,ResourceType.COIN));
        t.add(new ResourcesCount(4,ResourceType.COIN));
        DevelopmentCard d = new DevelopmentCard(DevelopmentCardLevel.FIRST,DevelopmentCardType.BLUE,1,t,null);
        ArrayList<ResourcesCount> t1 = d.getCosts();
        t1.remove(1);
        System.out.println(t.size());
        System.out.println(t1.size());
        System.out.println(d.getCosts().size());
    }
}
