package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * the {@link LeaderCard} that add produtive power to the {@link Player}
 */
public class LeaderCardAddProductive extends LeaderCard implements Serializable {
    /**
     * constructor of {@link LeaderCardAddProductive}
     *
     * @param points                points that the {@link LeaderCardAddProductive} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardAddProductive} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardAddProductive} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardAddProductive} power
     */
    public LeaderCardAddProductive(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     * create a new instance of the {@link LeaderCardAddProductive}
     *
     * @param points                points that the {@link LeaderCardAddProductive} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardAddProductive} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardAddProductive} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardAddProductive} power
     * @return  an Instance of the {@link LeaderCardAddProductive}
     */
    public static LeaderCardAddProductive getInstance(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardAddProductive(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     * add an Element to the {@link Player} addedPowers parameter
     *
     * @param player the {@link Player} how request the power activation
     * @return true if activation goes well, false otherwise
     */
    @Override
    public boolean active(Player player) {
        if(!active && isActivable(player)) { //if not active and it's not activable
            player.addPower(ProductivePower.getInstance(
                    new ArrayList<>(Arrays.asList(ResourcesCount.getInstance(1, resourceTypeRelated))),//todo: to see
                    new ArrayList<>(Arrays.asList(
                            Resource.getInstance(ResourceType.ANY), //todo: to see
                            Resource.getInstance(ResourceType.FAITH))))
            );
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
     * clone {@link LeaderCardAddProductive}
     *
     * @return a clone of {@link LeaderCardAddProductive}
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return getInstance(points, resourceTypeRelated, (ArrayList<ResourcesCount>) resourcesNeeded.clone(), (ArrayList<DevelopmentCardNeeded>) developmentCardNeeded.clone());
    }
}
