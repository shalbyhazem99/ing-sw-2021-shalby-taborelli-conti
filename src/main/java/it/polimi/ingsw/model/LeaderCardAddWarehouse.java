package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * the {@link LeaderCard} that add a warehouse to the {@link Player}
 */
public class LeaderCardAddWarehouse extends LeaderCard implements Serializable {
    /**
     * constructor of {@link LeaderCardAddWarehouse}
     *
     * @param points                points that the {@link LeaderCardAddWarehouse} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardAddWarehouse} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardAddWarehouse} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardAddWarehouse} power
     */
    public LeaderCardAddWarehouse(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     * create a new instance of the {@link LeaderCardAddWarehouse}
     *
     * @param points                points that the {@link LeaderCardAddWarehouse} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardAddWarehouse} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardAddWarehouse} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardAddWarehouse} power
     * @return an Instance of the {@link LeaderCardAddWarehouse}
     */
    public static LeaderCardAddWarehouse getInstance(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardAddWarehouse(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     * add an Element to the {@link Player} additional warehouse
     *
     * @param player the {@link Player} how request the power activation
     * @return true if activation goes well, false otherwise
     */
    @Override
    public boolean active(Player player) {
        if (!active && isActivable(player)) { //if not active and it's not activable
            player.addAdditionalWarehouse(Warehouse.getInstance(2, resourceTypeRelated)); //todo: to see
            return true;
        }
        return false;
    }
}
