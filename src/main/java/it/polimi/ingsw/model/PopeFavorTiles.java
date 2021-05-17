package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PopeFavorTiles implements Serializable {
    private int points;
    private boolean active;

    public PopeFavorTiles(int points) {
        this.points = points;
        this.active = false;
    }

    public static PopeFavorTiles getInstance(int points){
        return new PopeFavorTiles(points);
    }

    public void active(){
        if(!active)
            active =true;
    }

    public void disable(){
        active();
        points=0;
    }

    public int getPoints(){
        return this.points;
    }
    public boolean getActive(){
        return this.active;
    }

}
