package it.polimi.ingsw.model.developmentCard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class DevelopmentCard implements Serializable {
    private final DevelopmentCardLevel level;
    private final DevelopmentCardType type;
    private int equivalentPoint;
    private ArrayList<ResourcesCount> costs;
    private ProductivePower powers;
    protected String image;

    /**
     * Default constructor
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
     * Constructor
     * @param level {@link DevelopmentCardLevel} can be 0,1,2
     * @param type {@link DevelopmentCardType} can be green,blue,yellow,red
     */
    public DevelopmentCard(DevelopmentCardLevel level, DevelopmentCardType type) {
        this.level = level;
        this.type = type;
    }
    /**
     *
     * @param level {@link DevelopmentCardLevel} can be 0,1,2
     * @param type {@link DevelopmentCardType} can be green,blue,yellow,red
     * @param equivalentPoint point to be considered at the end of the game
     * @param costs {@link ArrayList} of {@link ResourcesCount} representing how much resources a player has to pay in order to buy the card
     * @param powers {@link ArrayList} of {@link ProductivePower} representing how the card can convert resources
     * @return an instance of DevelopmentCard
     */
    public static DevelopmentCard getInstance(DevelopmentCardLevel level, DevelopmentCardType type, int equivalentPoint, ArrayList<ResourcesCount> costs, ProductivePower powers) {
        return new DevelopmentCard(level, type, equivalentPoint, costs, powers);
    }

    /**
     *
     * @param level {@link DevelopmentCardLevel} can be 0,1,2
     * @param type {@link DevelopmentCardType} can be green,blue,yellow,red
     * @return an instance of DevelopmentCard with only a Level and Type
     */
    public static DevelopmentCard getInstance(DevelopmentCardLevel level, DevelopmentCardType type)
    {
        return new DevelopmentCard(level, type);
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
    public ArrayList<ResourcesCount> getCosts(Player player) {
        ArrayList<ResourcesCount> costsTemp = (ArrayList<ResourcesCount>) costs.clone();
        if(player.hasDiscount())
           return Utils.applyDiscount(costsTemp,player.getDiscounts());
        return costsTemp;
    }

    /**
     *
     * @return the conversion in String of the DevelopmentCard
     */
    @Override
    public String toString() {
        return "/eq_point:"+equivalentPoint+"/Cost:"+costs.toString();
        //return level.toString()+"/"+type.toString()+"/eq_point:"+equivalentPoint+"/Pot_prod:"+powers.toString()+"/Cost:"+costs.toString()+"|||||";
    }

    /**
     *
     * @return the string of the coasts formatted
     */
    public String getCostsFormatted()
    {
        String str = "[";
        for(int i = 0;i<costs.size();i++)
        {
            str = str + " " + costs.get(i).getCount()+ costs.get(i).getType().symbol;
        }
        return str+"]";
    }

    /**
     *
     * @return the string of powers formatted
     */
    public String getPowersFormatted()
    {
        ArrayList<ResourcesCount> costs = powers.getFrom();
        String str = "[";
        for(int i = 0;i<costs.size();i++) {
            str = str + " " + costs.get(i).getCount() + costs.get(i).getType().symbol;
        }
        str = str + "] ▶ ";
        str = str + Utils.formatResourcesCount(Utils.fromResourcesToResourceCount(powers.getTo()));
        return str;
    }

    /**
     *
     * @return the number of the image linked to the DevelopmentCard
     */
    public String getImage(){
        return image;
    }
}
