package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

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
     * @param points
     * @return an instance of the Pope's Tale
     */
    public static PopeFavorTiles getInstance(int points){
        return new PopeFavorTiles(points);
    }


    public void active(){
        if(!active)
            active =true;
    }

    public int getPoints(){
        if(active){
            return this.points;
        }
        else{
            return 0;
        }
    }
    public boolean isActive(){
        return this.active;
    }

}
