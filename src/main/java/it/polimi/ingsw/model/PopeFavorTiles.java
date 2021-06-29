package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class that represents a Pope's tale of the game
 */
public class PopeFavorTiles implements Serializable {
    private int points;
    private boolean active;

    /**
     * Constructor
     * @param points of the Pope's tale
     */
    public PopeFavorTiles(int points) {
        this.points = points;
        this.active = false;
    }

    /**
     * Create an instance of the Pope's Tale
     * @param points equivalent points of the Pope's Tale
     * @return an instance of the Pope's Tale
     */
    public static PopeFavorTiles getInstance(int points){
        return new PopeFavorTiles(points);
    }


    /**
     * Method to activate the Pope's Tale
     */
    public void active(){
        if(!active)
            active =true;
    }

    /**
     *
     * @return 0 if the Pope's Tale is not active else returns the equivalent points
     */
    public int getPoints(){
        if(active){
            return this.points;
        }
        else{
            return 0;
        }
    }

    /**
     *
     * @return the state of the Pope's Tale
     */
    public boolean isActive(){
        return this.active;
    }

}
