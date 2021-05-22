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
