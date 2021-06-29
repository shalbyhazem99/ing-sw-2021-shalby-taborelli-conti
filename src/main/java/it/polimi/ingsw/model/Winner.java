package it.polimi.ingsw.model;

/**
 * Class used to represent the winner of the match
 */
public class Winner {
    private String name;
    private int points;
    private int totalResources;

    /**
     * Constructor
     * @param name of the {@link Player}
     * @param points sum of all the points gain by the {@link Player}
     * @param totalResources sum of all the {@link it.polimi.ingsw.model.resource.Resource} that the {@link Player} has stored in any container
     */
    public Winner(String name, int points, int totalResources) {
        this.name = name;
        this.points = points;
        this.totalResources = totalResources;
    }

    /**
     *
     * @return the name of the winner
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return amount of the point of the winner
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @return the total amount of the {@link it.polimi.ingsw.model.resource.Resource} stored by the {@link Player} in any container
     */
    public int getTotalResources() {
        return totalResources;
    }

    /**
     * Creating an instance of winner
     * @param name of the {@link Player}
     * @param points sum of all the points gain by the {@link Player}
     * @param totalResources sum of all the {@link it.polimi.ingsw.model.resource.Resource} that the {@link Player} has stored in any container
     * @return an instance of winner
     */
    public static Winner getInstance (String name, int points, int totalResources){
        return new Winner(name,points,totalResources);
    }

    @Override
    public String toString() {
        return name +"("+points+" points)";
    }
}
