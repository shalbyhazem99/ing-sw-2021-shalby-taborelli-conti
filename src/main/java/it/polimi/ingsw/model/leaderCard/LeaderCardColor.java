package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * the {@link LeaderCard} that add the ability to change white marble into resource to the {@link Player}
 */
public class LeaderCardColor extends LeaderCard implements Serializable {
    /**
     * constructor of {@link LeaderCardColor}
     *
     * @param points                points that the {@link LeaderCardColor} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardColor} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardColor} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardColor} power
     */
    public LeaderCardColor(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     * create a new instance of the {@link LeaderCardColor}
     *
     * @param points                points that the {@link LeaderCardColor} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardColor} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardColor} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardColor} power
     * @return an Instance of the {@link LeaderCardColor}
     */
    public static LeaderCardColor getInstance(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardColor(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     *
     * @param player the {@link Player} how request the power activation
     * @return true if activation goes well, false otherwise
     */
    @Override
    public boolean active(Player player) {
        if (!active && isActivable(player)) { //if not active and it's not activable
            player.addConversionStrategies(resourceTypeRelated);
            return true;
        }
        return false;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * clone {@link LeaderCardColor}
     *
     * @return a clone of {@link LeaderCardColor}
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return getInstance(points, resourceTypeRelated, (ArrayList<ResourcesCount>) resourcesNeeded.clone(), (ArrayList<DevelopmentCardNeeded>) developmentCardNeeded.clone());
    }
}
