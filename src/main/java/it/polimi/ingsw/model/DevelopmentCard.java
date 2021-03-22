package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

//TODO: final tutti gli attributi
public class DevelopmentCard implements Serializable {
    private final DevelopmentCardLevel level;
    private final DevelopmentCardType type;
    private final int equivalentPoint;
    private ArrayList<ResourcesCount> costs;
    private ProductivePower powers;

    public DevelopmentCard(DevelopmentCardLevel level, DevelopmentCardType type, int equivalentPoint, ArrayList<ResourcesCount> costs, ProductivePower powers) {
        this.level = level;
        this.type = type;
        this.equivalentPoint = equivalentPoint;
        this.costs = costs;
        this.powers = powers;
    }

    public static DevelopmentCard getInstance(DevelopmentCardLevel level, DevelopmentCardType type, int equivalentPoint, ArrayList<ResourcesCount> costs, ProductivePower powers) {
        return new DevelopmentCard(level, type, equivalentPoint, costs, powers);
    }

    public boolean isBuyableFrom(Player player){
        //TODO: CONTROLO SUI COSTI
        return false;
    }

    public DevelopmentCardLevel getLevel() {
        return level;
    }

    public DevelopmentCardType getType() {
        return type;
    }

    public int getEquivalentPoint() {
        return equivalentPoint;
    }

    public ProductivePower getPowers() {
        return powers;
    }
}
