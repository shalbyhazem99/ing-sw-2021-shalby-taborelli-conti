package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.io.Serializable;

public class DamageCounterAction implements Serializable {
    private TypeDamageCounter action;
    private int count;
    private DevelopmentCardType type2Discard;


    public void setAction(TypeDamageCounter action) {
        this.action = action;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType2Discard(DevelopmentCardType type2Discard) {
        this.type2Discard = type2Discard;
    }
}
