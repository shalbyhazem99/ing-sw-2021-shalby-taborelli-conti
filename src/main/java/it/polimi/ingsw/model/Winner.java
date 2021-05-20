package it.polimi.ingsw.model;

public class Winner {
    private String name;
    private int points;
    private int totalResources;
    private int position;

    public Winner(String name, int points, int totalResources, int position) {
        this.name = name;
        this.points = points;
        this.totalResources = totalResources;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getTotalResources() {
        return totalResources;
    }

    public int getPosition() {
        return position;
    }

    public static Winner getInstance (String name, int points, int totalResources, int position){
        return new Winner(name,points,totalResources,position);
    }

    @Override
    public String toString() {
        return name +"("+points+" points)";
    }
}
