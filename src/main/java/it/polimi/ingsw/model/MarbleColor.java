package it.polimi.ingsw.model;

import java.io.Serializable;

public enum MarbleColor implements Serializable {
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
