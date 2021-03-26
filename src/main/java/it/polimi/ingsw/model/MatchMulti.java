package it.polimi.ingsw.model;

import java.io.Serializable;


public class MatchMulti extends Match implements Serializable {
    /**
     * Class concerning a Match of many players
     */
    private int posInkwell;

    /**
     * Call method of superclass, posInkwell will be initially set to '-1' and then it will be correctly modified just before the match's starting
     * The {@link Player} {@link java.util.ArrayList} will be initalized at a fixed length
     */
    public MatchMulti(int numberOfPlayers) {
        super(numberOfPlayers);
        posInkwell = -1;
    }

    /**
     * Get the index of the first player in the playing order
     * @return the index of the first plauer in the playing order
     */
    public int getPosInkwell() {
        return posInkwell;
    }

    /**
     * The method randomly choose the player with the inkwell
     * @return the index of the {@link Player} with the inkwell
     */
    public int randomlyPickInkwellPlayer()
    {
        if(this.posInkwell!=-1) { return this.posInkwell; }
        this.posInkwell = (int) (Math.random() * (0 - getPlayers().size())) + getPlayers().size();
        return this.posInkwell;
    }
}
