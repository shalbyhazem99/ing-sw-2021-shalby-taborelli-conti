package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * the {@link LeaderCard} that add productive power to the {@link Player}
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
        if(!active && isActionable(player)) { //if not active and it's not actionable
            if (resourceTypeRelated!=null){
                player.addPower(ProductivePower.getInstance(
                        new ArrayList<>(Collections.singletonList(ResourcesCount.getInstance(1, resourceTypeRelated))),
                        new ArrayList<>(Arrays.asList(
                                Resource.getInstance(ResourceType.ANY),
                                Resource.getInstance(ResourceType.FAITH))))
                );
                }
            this.active=true;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return super.toString()+" | NEW PRODUCTION : [ 1"+ Utils.resourceTypeToString(resourceTypeRelated)+"] ▶ [ 1A , 1"+ResourceType.FAITH.symbol+" ]";
    }
}
