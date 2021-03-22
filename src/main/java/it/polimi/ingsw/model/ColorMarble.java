package it.polimi.ingsw.model;

public enum ColorMarble {
    WHITE(null),
    BLUE(ResourceType.SHILDS),
    GREY(ResourceType.STONES),
    YALLOW(ResourceType.COINS),
    PURPLE(ResourceType.SERVANTS),
    RED(ResourceType.FAITH);

    public final ResourceType equivalent;

    private ColorMarble(ResourceType equivalent) {
        this.equivalent = equivalent;
    }

}
