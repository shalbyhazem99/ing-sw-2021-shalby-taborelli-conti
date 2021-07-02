package it.polimi.ingsw.model.resource;

import java.io.Serializable;

public enum ResourceType implements Serializable {
    COIN("⚫"),
    STONE("💎"),
    SERVANT("⚔"),
    SHIELD("⛨"),
    FAITH("+"),
    ANY("A");

    public final String symbol;


    ResourceType(String symbol) {
        this.symbol = symbol;
    }
}
