package it.polimi.ingsw.model;

public enum DevelopmentCardType {
    GREEN(0),
    BLUE(1),
    YALLOW(2),
    PURPLE(3);
    public final int label;

    private DevelopmentCardType(int label) {
        this.label = label;
    }
}
