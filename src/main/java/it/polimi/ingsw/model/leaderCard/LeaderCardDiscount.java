package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * the {@link LeaderCard} that add a discount to the {@link Player}
 */
public class LeaderCardDiscount extends LeaderCard implements Serializable {
    /**
     * constructor of {@link LeaderCardDiscount}
     *
     * @param points                points that the {@link LeaderCardDiscount} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardDiscount} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardDiscount} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardDiscount} power
     */
    public LeaderCardDiscount(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        super(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     * create a new instance of the {@link LeaderCardDiscount}
     *
     * @param points                points that the {@link LeaderCardDiscount} give if activated
     * @param resourceTypeRelated   the {@link ResourceType} that is associated to the {@link LeaderCardDiscount} power
     * @param resourcesNeeded       an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCardDiscount} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCardDiscount} power
     * @return an Instance of the {@link LeaderCardDiscount}
     */
    public static LeaderCardDiscount getInstance(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        return new LeaderCardDiscount(points, resourceTypeRelated, resourcesNeeded, developmentCardNeeded);
    }

    /**
     *
     * @param player the {@link Player} how request the power activation
     * @return true if activation goes well, false otherwise
     */
    @Override
    public boolean active(Player player) {
        if (!active && isActionable(player)) { //if not active and it's not activable
            if (resourceTypeRelated != null){
                player.addDiscount(ResourcesCount.getInstance(1,resourceTypeRelated));
            }
            this.active = true;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString()+" | NEW DISCOUNT : -1 "+ Utils.resourceTypeToString(resourceTypeRelated);
    }
}
