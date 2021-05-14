package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;

public enum MarbleColor implements Serializable,Cloneable {
    WHITE(null),
    BLUE(ResourceType.SHIELD),
    GREY(ResourceType.STONE),
    YELLOW(ResourceType.COIN),
    PURPLE(ResourceType.SERVANT),
    RED(ResourceType.FAITH);

    public final ResourceType equivalent;

    private MarbleColor(ResourceType equivalent) {
        this.equivalent = equivalent;
    }

}
