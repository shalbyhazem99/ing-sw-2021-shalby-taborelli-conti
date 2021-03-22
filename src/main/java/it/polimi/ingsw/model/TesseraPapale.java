package it.polimi.ingsw.model;

public class PopeTales {
    private int points;
    private boolean actvive;

    public PopeTales(int points) {
        this.points = points;
        this.actvive = false;
    }

    public static PopeTales getInstance(int points){
        return new PopeTales(points);
    }

    public void abilita(){
        if(!actvive)
            actvive=true;
    }

}
