package it.polimi.ingsw.model;

import java.util.ArrayList;

public class DevelopmentCard {
    private DevelopmentCardLevel level;
    private DevelopmentCardType type;
    private int victoryPoints;
    private java.util.ArrayList<CountResources> costs;
    private ProductionPower power;

    public DevelopmentCard(DevelopmentCardLevel level, DevelopmentCardType type, int victoryPoints, ArrayList<CountResources> costs, ProductionPower power) {
        this.level = level;
        this.type = type;
        this.victoryPoints = victoryPoints;
        this.costs = costs;
        this.power = power;
    }

    public static DevelopmentCard getInstance(DevelopmentCardLevel level, DevelopmentCardType type, int victoryPoints, ArrayList<CountResources> costs, ProductionPower power) {
        return new DevelopmentCard(level, type, victoryPoints, costs, power);
    }

    public boolean isBuyableFrom(Playeratore player){
        //TODO: check costs
        return false;
    }

    public DevelopmentCardLevel getLevel() {
        return level;
    }

    public DevelopmentCardType getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ProductionPower getPower() {
        return power;
    }
}
