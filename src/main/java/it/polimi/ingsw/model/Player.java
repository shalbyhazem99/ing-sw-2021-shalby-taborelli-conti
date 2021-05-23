package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player implements Serializable {
    private final String name;
    private int posFaithMarker;
    private final ArrayList<PopeFavorTiles> popeFavorTiles;
    private final ArrayList<LeaderCard> leaderCards;
    private ArrayList<DevelopmentCardSpace> developmentCardSpaces;
    private ArrayList<Warehouse> warehousesStandard;
    private ArrayList<Resource> strongBox;
    private ArrayList<Warehouse> warehousesAdditional;
    private final ArrayList<ResourcesCount> discounts;
    private final ArrayList<ResourceType> conversionStrategies;
    private final ArrayList<ProductivePower> addedPower;

    public Player(String name) {
        this.name = name;
        //create all the needed Arraylist
        popeFavorTiles = new ArrayList<>();
        leaderCards = new ArrayList<>();
        warehousesStandard = new ArrayList<>();
        strongBox = new ArrayList<>();
        warehousesAdditional = new ArrayList<>();
        discounts = new ArrayList<>();
        conversionStrategies = new ArrayList<>();
        addedPower = new ArrayList<>();
        //generate powers
        generatePopeFavorTiles();
        generateDevelopmentCardSpaces();
        generateWarehouse();
    }

    public static Player getInstance(String name) {
        return new Player(name);
    }

    public static Player getInstance() {
        return new Player("unknown");
    }

    //------------------------------------GENERATE PLAYER SUPPORT METHOD---------------------------------------
    private void generateDevelopmentCardSpaces() {
        developmentCardSpaces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            developmentCardSpaces.add(DevelopmentCardSpace.getInstance());
        }
    }

    private void generatePopeFavorTiles() {
        popeFavorTiles.add(PopeFavorTiles.getInstance(2));
        popeFavorTiles.add(PopeFavorTiles.getInstance(3));
        popeFavorTiles.add(PopeFavorTiles.getInstance(4));
    }

    public void generateWarehouse() {
        warehousesStandard.add(Warehouse.getInstance(1, ResourceType.ANY));
        warehousesStandard.add(Warehouse.getInstance(2, ResourceType.ANY));
        warehousesStandard.add(Warehouse.getInstance(3, ResourceType.ANY));
    }

    //------------------------------------------SIMPLE GETTER------------------------------------------------------
    public String getName() {
        return name;
    }

    public ArrayList<ProductivePower> getAddedPower() {
        return addedPower;
    }

    public ArrayList<ResourceType> getConversionStrategies() {
        return conversionStrategies;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public ArrayList<DevelopmentCardSpace> getDevelopmentCardSpaces() {
        return developmentCardSpaces;
    }

    public ArrayList<PopeFavorTiles> getPopeFavorTiles() {
        return popeFavorTiles;
    }

    public LeaderCard getLeaderCard(int position) {
        if (position < leaderCards.size())
            return leaderCards.get(position);
        return null;
    }

    public int getPosFaithMarker() {
        return posFaithMarker;
    }

    public ArrayList<Warehouse> getWarehousesStandard() {
        return warehousesStandard;
    }

    public ArrayList<Warehouse> getWarehousesAdditional() {
        return warehousesAdditional;
    }

    public ArrayList<Resource> getStrongBox() {
        return strongBox;
    }

    public ArrayList<ResourcesCount> getDiscounts() {
        return discounts;
    }

    public boolean hasDiscount() {
        return getDiscounts().size() > 0;
    }

    public int numOfWhiteMarbleConversionStrategy() {
        return conversionStrategies.size();
    }

    //------------------------------------------COMPLEX GETTER---------------------------------------------

    /**
     * all the {@link Player} {@link Resource}
     *
     * @return an {@link ArrayList} of {@link Resource} containing all the resources coming from the warehouses and the strongbox
     */
    public ArrayList<Resource> getResources() {
        return (ArrayList<Resource>) Stream.concat(
                Stream.concat(getStrongBox().stream(),
                        getWarehousesStandard().stream().flatMap(elem -> elem.getResources().stream())),
                getWarehousesAdditional().stream().flatMap(elem -> elem.getResources().stream()))
                .collect(Collectors.toList());
    }

    /**
     * return all the {@link DevelopmentCard} even if covered
     *
     * @return an {@link ArrayList} of all the {@link Player} {@link DevelopmentCard}
     */
    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return (ArrayList<DevelopmentCard>) developmentCardSpaces.stream()
                .flatMap(elem -> elem.linearize().stream())
                .collect(Collectors.toList());
    }

    //------------------------------------------SETTER------------------------------------------------------
    public void setWarehousesStandard(ArrayList<Warehouse> warehousesStandard) {
        this.warehousesStandard = warehousesStandard;
    }

    public void setWarehousesAdditional(ArrayList<Warehouse> warehousesAdditional) {
        this.warehousesAdditional = warehousesAdditional;
    }

    public void setStrongBox(ArrayList<Resource> strongBox) {
        this.strongBox = strongBox;
    }

    //---------------------------------------SIMPLE ADDER------------------------------------------------------
    public void addLeaderCard(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    public void addDiscount(ResourcesCount resourcesCount) {
        discounts.add(resourcesCount);
    }

    public void addPower(ProductivePower productivePower) {
        addedPower.add(productivePower);
    }

    public void addConversionStrategies(ResourceType resourceType) {
        conversionStrategies.add(resourceType);
    }

    public void addAdditionalWarehouse(Warehouse warehouse) {
        warehousesAdditional.add(warehouse);
    }

    public void addResourceToStrongBox(Resource resource) {
        strongBox.add(resource);
    }

    public void addResourceToStrongBox(ArrayList<Resource> resources) {
        strongBox.addAll(resources);
    }

    //---------------------------------------COMPLEX ADDER------------------------------------------------------
    public boolean addResourceToWarehouseAdditional(Resource resource, int index) {
        if (index >= warehousesAdditional.size() || !resource.getType().equals(warehousesAdditional.get(index).getResourceType()) || warehousesAdditional.get(index).getSpaceAvailable() < 1)
            return false;
        return warehousesAdditional.get(index).addResource(resource);
    }

    public boolean addResourceToWarehouseAdditional(ArrayList<Resource> resources, int index) {
        String warehousesAdditional =new Gson().toJson(getWarehousesAdditional());
        for (Resource resource : resources) {
            if (!addResourceToWarehouseAdditional(resource, index)) {
                List<Warehouse> list = new ArrayList<>();
                for (Warehouse warehouse : new Gson().fromJson(warehousesAdditional, Warehouse[].class)) {
                    list.add(warehouse);
                }
                setWarehousesAdditional(new ArrayList<Warehouse>(list));
                return false;
            }
        }
        return true;
    }

    public boolean addResourceToWarehouseStandard(Resource resource, int index) {
        if (index >= warehousesStandard.size())
            return false;
        if (!warehousesStandard.get(index).getResourceType().equals(ResourceType.ANY) &&
                (!resource.getType().equals(warehousesStandard.get(index).getResourceType()) ||
                        warehousesStandard.get(index).getSpaceAvailable() < 1))
            return false;
        //if there are other warehouses with the same type of card
        if (warehousesStandard.stream()
                .anyMatch(elem -> !elem.equals(warehousesStandard.get(index)) && // different Warehouse index
                        !elem.getResourceType().equals(ResourceType.ANY) && //not necessary but could prevents coding error
                        elem.getResourceType().equals(warehousesStandard.get(index).getResourceType())))  // the same resource type
            return false;
        return warehousesStandard.get(index).addResource(resource);
    }

    public boolean addResourceToWarehouseStandard(ArrayList<Resource> resources, int index) {
        String warehouseStandard =new Gson().toJson(getWarehousesStandard());
        for (Resource resource : resources) {
            if (!addResourceToWarehouseStandard(resource, index)) {
                List<Warehouse> list = new ArrayList<>();
                for (Warehouse warehouse : (new Gson().fromJson(warehouseStandard, Warehouse[].class))) {
                    list.add(warehouse);
                }
                setWarehousesStandard(new ArrayList<>(list));
                return false;
            }
        }
        return true;
    }

    //------------------------------------------ACTION METHOD-------------------------------------------------

    /**
     * move the Faith Marker Ahead
     *
     * @param pos the number of movement
     */
    public void moveAheadFaith(int pos) {
        posFaithMarker += pos;
        posFaithMarker = Math.min(Utils.FAITH_LENGTH, posFaithMarker);
    }

    public boolean isActionable(ArrayList<ResourcesCount> resourcesNeeded) {
        return resourcesNeeded != null && Utils.compareResources(getResources(), resourcesNeeded);
    }

    /**
     * Verify if the {@link Player} could activate the power and remove the resources
     *
     * @param resourceToUse
     * @return
     */
    public boolean canAfford(ArrayList<ResourcePick> resourceToUse) {
        if (resourceToUse == null)
            return false;
        Gson gson = new Gson();
        String warehouseStandard = gson.toJson(getWarehousesStandard());
        String warehouseAdditional = gson.toJson(getWarehousesAdditional());
        String strongBox = gson.toJson(getStrongBox());
        ArrayList<Warehouse> standardTemp = getWarehousesStandard();
        ArrayList<Warehouse> additionalTemp = getWarehousesAdditional();
        ArrayList<Resource> strongBoxTemp = getStrongBox();

        //check if the player has the resource
        for (ResourcePick resourcePick : resourceToUse) {
            ResourceType resourceType = resourcePick.getResourceType();
            ArrayList<Resource> resToBeRemoved = null;
            switch (resourcePick.getResourceWarehouseType()) {
                case WAREHOUSE:
                    if (resourcePick.getWarehousePosition() >= 0 && resourcePick.getWarehousePosition() < 3) {
                        resToBeRemoved = standardTemp.get(resourcePick.getWarehousePosition()).getResources();
                        standardTemp.get(resourcePick.getWarehousePosition()).incrementAvailability();
                        if (standardTemp.get(resourcePick.getWarehousePosition()).getResources().size() == 1) {
                            standardTemp.get(resourcePick.getWarehousePosition()).changeResourceType(ResourceType.ANY);
                        }
                    } else if (additionalTemp.size() > resourcePick.getWarehousePosition() - 3 && resourcePick.getWarehousePosition() >= 3 && resourcePick.getWarehousePosition() < 5) {
                        resToBeRemoved = additionalTemp.get(resourcePick.getWarehousePosition() - 3).getResources();
                        additionalTemp.get(resourcePick.getWarehousePosition()-3).incrementAvailability();
                    } else {
                        ArrayList<Warehouse> temp = new ArrayList<>();
                        Collections.addAll(temp, gson.fromJson(warehouseStandard, Warehouse[].class));
                        setWarehousesStandard(temp);
                        temp = new ArrayList<>();
                        Collections.addAll(temp, gson.fromJson(warehouseAdditional, Warehouse[].class));
                        setWarehousesAdditional(temp);
                        ArrayList<Resource> temp2 = new ArrayList<>();
                        Collections.addAll(temp2, gson.fromJson(strongBox, Resource[].class));
                        setStrongBox(temp2);
                        return false;
                    }
                    break;
                case STRONGBOX:
                    resToBeRemoved = strongBoxTemp;
                    break;
            }
            if (!resToBeRemoved.remove(Resource.getInstance(resourceType))) {
                ArrayList<Warehouse> temp = new ArrayList<>();
                Collections.addAll(temp, gson.fromJson(warehouseStandard, Warehouse[].class));
                setWarehousesStandard(temp);
                temp = new ArrayList<>();
                Collections.addAll(temp, gson.fromJson(warehouseAdditional, Warehouse[].class));
                setWarehousesAdditional(temp);
                ArrayList<Resource> temp2 = new ArrayList<>();
                Collections.addAll(temp2, gson.fromJson(strongBox, Resource[].class));
                setStrongBox(temp2);
                return false;
            }
        }
        return true;
    }

    public boolean developmentCardCanBeAdded(DevelopmentCard developmentCard, int spacePos) {
        if (developmentCardSpaces.size() <= spacePos) {
            return false;
        }
        return (developmentCardSpaces.get(spacePos).canBeAdded(developmentCard));
    }

    /**
     * add a {@link DevelopmentCard} to the {@link DevelopmentCardSpace}
     *
     * @param developmentCard
     * @param spacePos        the {@link DevelopmentCardSpace} where the {@link DevelopmentCard} must be placed, and it start fro zero
     * @return true if successful, false otherwise
     */
    public boolean addDevelopmentCard(DevelopmentCard developmentCard, int spacePos) {
        if (developmentCardSpaces.size() > spacePos) {
            return developmentCardSpaces.get(spacePos).add(developmentCard);
        }
        return false;
    }

    /**
     * Method to discard a {@link LeaderCard} from the player list
     *
     * @param leaderCardPosition the position in the {@link ArrayList} of the {@link LeaderCard}
     * @return true if possible, false otherwise
     */
    public boolean discardLeaderCard(int leaderCardPosition) {
        if (leaderCardPosition < leaderCards.size()) {
            LeaderCard leaderCard = leaderCards.get(leaderCardPosition);
            if (!leaderCard.isActive()) {
                if (leaderCards.remove(leaderCard)) {
                    moveAheadFaith(1);
                    return true;
                }
            }
        }
        return false;
    }

    //------------------------------------------REVIEW-------------------------------------------------

    /**
     * {@link Player} choose how many {@link Resource} to move from the selected Warehouse and where to put them
     *
     * @param indexFirstWarehouse         index of the source Warehouse
     * @param indexSecondWarehouse        index of the destination Warehouse
     * @param resourcesFromFirstWarehouse number of the Resources to move
     * @return result of the operation
     */
    public boolean moveResources(int indexFirstWarehouse, int indexSecondWarehouse, int resourcesFromFirstWarehouse) {
        Warehouse w1, w2;
        boolean firstIsStandard = true, secondIsStandard = true;
        //getting the warehouses from the player
        if (indexFirstWarehouse == 3 || indexFirstWarehouse == 4) {
            w1 = getWarehousesAdditional().get(indexFirstWarehouse - 3);
            firstIsStandard = false;
        } else {
            w1 = getWarehousesStandard().get(indexFirstWarehouse);
        }
        if (indexSecondWarehouse == 3 || indexSecondWarehouse == 4) {
            w2 = getWarehousesAdditional().get(indexSecondWarehouse - 3);
            secondIsStandard = false;
        } else {
            w2 = getWarehousesStandard().get(indexSecondWarehouse);
        }

        // Different Cases
        if (w1.getResources().size() < resourcesFromFirstWarehouse) {
            return false;
        }
        //S<->S
        if (firstIsStandard && secondIsStandard && resourcesFromFirstWarehouse < w2.getSpaceAvailable() + w2.getResources().size()) {
            if (w2.getResourceType() == ResourceType.ANY && resourcesFromFirstWarehouse != w1.getResources().size()) {
                return false;
            }
            ArrayList<Resource> temp = w1.getResources();
            ResourceType resourceType_w1 = w1.getResourceType();
            ResourceType resourceType_w2 = w2.getResourceType();

            w1.changeResources(w2.getResources());
            w1.changeResourceType(resourceType_w2);
            if (w1.getResources().size() == 0) {
                w1.changeResourceType(ResourceType.ANY);
                w1.changeAvailability(indexFirstWarehouse + 1);
            }
            w1.changeAvailability(indexFirstWarehouse + 1 - w1.getResources().size());
            w2.changeResources(temp);
            w2.changeResourceType(resourceType_w1);
            if (w2.getResources().size() == 0) {
                w2.changeResourceType(ResourceType.ANY);
                w2.changeAvailability(indexSecondWarehouse + 1);
            }
            w2.changeAvailability(indexSecondWarehouse + 1 - w2.getResources().size());
            return true;
        }
        // S->A || A->S the Resources are moved only if the two warehouses has the same ResourceType
        else if ((w1.getResourceType() == w2.getResourceType() || w2.getResourceType() == ResourceType.ANY) && (resourcesFromFirstWarehouse <= w2.getSpaceAvailable())) {
            if (w2.getResourceType() == ResourceType.ANY) {
                w2.changeResourceType(w1.getResourceType());
            }
            for (int i = 0; i < resourcesFromFirstWarehouse; i++) {
                w1.getResources().remove(0);
                w1.changeAvailability(w1.getSpaceAvailable() + 1);
                w2.getResources().add(Resource.getInstance(w2.getResourceType()));
                w2.changeAvailability(w2.getSpaceAvailable() - 1);
            }
            if (w1.getResources().size() == 0 && firstIsStandard) {
                w1.changeResourceType(ResourceType.ANY);
            }
            return true;
        }
        return false;
    }
}