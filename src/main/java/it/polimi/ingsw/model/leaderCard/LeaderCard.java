package it.polimi.ingsw.model.leaderCard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardNeeded;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The abstract class that represent the {@link LeaderCard}
 */
public abstract class LeaderCard implements Serializable {
    protected boolean active;
    protected final int points;
    protected final ResourceType resourceTypeRelated;
    //requirements
    protected final ArrayList<ResourcesCount> resourcesNeeded;
    protected final ArrayList<DevelopmentCardNeeded> developmentCardNeeded;

    /**
     * constructor of {@link LeaderCard}
     * @param points points that the {@link LeaderCard} give if activated
     * @param resourceTypeRelated the {@link ResourceType} that is associated to the {@link LeaderCard} power
     * @param resourcesNeeded an {@link ArrayList} of the {@link ResourcesCount} needed to activate the {@link LeaderCard} power
     * @param developmentCardNeeded an {@link ArrayList} of the {@link DevelopmentCardNeeded} to activate the {@link LeaderCard} power
     */
    public LeaderCard(int points, ResourceType resourceTypeRelated, ArrayList<ResourcesCount> resourcesNeeded, ArrayList<DevelopmentCardNeeded> developmentCardNeeded) {
        this.points = points;
        this.resourceTypeRelated = resourceTypeRelated;
        this.resourcesNeeded = resourcesNeeded;
        this.developmentCardNeeded = developmentCardNeeded;
        this.active=false;
    }

    /**
     * verify if the player has enough {@link Resource} and development card to active the power
     * @param player the player to verify
     * @return true if tha power is actionable, false otherwise
     */
    protected boolean isActionable(Player player){
        if(developmentCardNeeded !=null) {
            for (DevelopmentCardNeeded dev : developmentCardNeeded) {
                if (dev.getCount() > player.getDevelopmentCards().stream().filter(elem -> elem.getType().equals(dev.getType())
                        && (dev.getLevel()==null || elem.getLevel().equals(dev.getLevel()))).count())
                    return false;
            }
        }
        if(!resourcesNeeded.isEmpty()) { //for future development we decide to not use else
            return player.isActionable(resourcesNeeded);
        }
        return true;
    }

    /**
     * active the power of the card by doing some operation on the {@link Player}
     * @param player the {@link Player} how request the power activation
     * @return true if the power is activated, false otherwise
     */
    public abstract boolean active(Player player);

   /**
     *
     * @return true if the card is active, false otherwise
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * @return the point to add to the final score, so it returns zero if the card was never activated during the match
     */
    public int getPoints() {
        if (active)
            return points;
        else
            return 0;
    }

    @Override
    public String toString() {
        String s = "";
        if(active)
        {
            s+="  ✔  |";
        }
        else
        {
            s+="  ❌  |";
        }
        s+="  "+points+"  |  "+ Utils.resourceTypeToString(resourceTypeRelated)+" | "+ Utils.formatResourcesCount(resourcesNeeded) + Utils.fillSpaces(25,Utils.formatResourcesCount(resourcesNeeded).length())+" | "+developmentCardNeeded.toString()+Utils.fillSpaces(25,developmentCardNeeded.toString().length());

        return s;
    }
}