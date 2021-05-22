package it.polimi.ingsw.model;

public class Winner {
    private String name;
    private int points;
    private int totalResources;

    public Winner(String name, int points, int totalResources) {
        this.name = name;
        this.points = points;
        this.totalResources = totalResources;
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

    public static Winner getInstance (String name, int points, int totalResources){
        return new Winner(name,points,totalResources);
    }

    @Override
    public String toString() {
        return name +"("+points+" points)";
    }
}
