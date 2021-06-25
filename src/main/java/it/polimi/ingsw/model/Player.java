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
    private boolean offline;

    public Player(String name) {
        this.name = name;
        this.offline = false;
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

    public boolean isOffline(){
        return offline;
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

    public Warehouse getWarehouseFromPosition(int pos){
        if(pos>=0 && pos<warehousesStandard.size()){
            return warehousesStandard.get(pos);
        }else if(pos>=warehousesStandard.size() && pos<warehousesStandard.size()+warehousesAdditional.size()){
            return warehousesAdditional.get(pos-warehousesStandard.size());
        }else {
            return null;
        }
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

    public void setOffline(){
        this.offline = true;
    }

    public void returnOnline(){
        offline=false;
    }

    public void makeOffline(){
        offline=true;
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
        String warehousesAdditional = new Gson().toJson(getWarehousesAdditional());
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
        String warehouseStandard = new Gson().toJson(getWarehousesStandard());
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
                        additionalTemp.get(resourcePick.getWarehousePosition() - 3).incrementAvailability();
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

    /**
     * {@link Player} choose how many {@link Resource} to move from the selected Warehouse and where to put them
     *
     * @param indexFirstWarehouse  index of the source Warehouse
     * @param indexSecondWarehouse index of the destination Warehouse
     * @param indexFirstWarehouse  index of the source Warehouse
     * @param indexSecondWarehouse index of the destination Warehouse
     * @param how_many_first       number of  {@link Resource} to get from the first {@link Warehouse}
     * @param how_many_second      number of {@link Resource} to get from the second {@link Warehouse}
     * @return result of the operation
     */
    public boolean moveResources(int indexFirstWarehouse, int indexSecondWarehouse, int how_many_first, int how_many_second) {
        Warehouse w1, w2;
        boolean firstIsStandard = true, secondIsStandard = true;
        if(indexFirstWarehouse<0 || indexSecondWarehouse<0 || indexFirstWarehouse>=3+getWarehousesAdditional().size()|| indexSecondWarehouse>=3+getWarehousesAdditional().size())
            return false;
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

        //S<->S
        if (firstIsStandard && secondIsStandard) { //if both standard I mustn't consider how many parameters, I must swap warehouses
            //if both warehouses are not empty and they've different types stored ==> error
            //they obviously are of different type (a part of if they're ANY)
            //check that the first warehouse can store the amount of resources taken from the second warehouse and viceversa
            if (w1.getSpaceAvailable() + w1.getResources().size() < w2.getResources().size()) {
                return false;
            }
            if (w2.getSpaceAvailable() + w2.getResources().size() < w1.getResources().size()) {
                return false;
            }
            ArrayList<Resource> temp = w1.getResources();
            ResourceType resourceType_w1 = w1.getResourceType();
            ResourceType resourceType_w2 = w2.getResourceType();

            w1.changeResources(w2.getResources());
            w1.changeResourceType(resourceType_w2);
            w1.changeAvailability(indexFirstWarehouse + 1 - w1.getResources().size());

            w2.changeResources(temp);
            w2.changeResourceType(resourceType_w1);
            w2.changeAvailability(indexSecondWarehouse + 1 - w2.getResources().size());
            return true;
        }

        int diff = how_many_first - how_many_second;
        if (diff == 0)
            return true;
        //A<->A, NOW HOW MANY WILL MATTER
        if (!firstIsStandard && !secondIsStandard) {
            if (!w1.getResourceType().equals(w2.getResourceType())) //resource type must be the same (it cannot be ANY)
            {
                return false;
            }
            //CHECK IF HOW_MANY indexes are correct
            if (how_many_first < 0 || how_many_first > w1.getResources().size() || how_many_second < 0 || how_many_second > w2.getResources().size()) {
                return false;
            }
            //CHECK IF THE ACTION CAN BE PERFORMED
            //w1_new_space_available = how many things I can store before the action + how many things I "take out" from the warehouse
            //- how many things I have to put in
            int w1_new_space_available = w1.getSpaceAvailable() + how_many_first - how_many_second;
            if (w1_new_space_available < 0) {
                return false;
            }
            int w2_new_space_available = w2.getSpaceAvailable() + how_many_second - how_many_first;
            if (w2_new_space_available < 0) {
                return false;
            }
            w1.changeAvailability(w1_new_space_available);
            w2.changeAvailability(w2_new_space_available);
            ArrayList<Resource> from_w1_to_w2 = new ArrayList<>();
            for (int i = 0; i < how_many_first; i++) {
                from_w1_to_w2.add(w1.getResources().remove(0));
            }
            ArrayList<Resource> from_w2_to_w1 = new ArrayList<>();
            for (int i = 0; i < how_many_second; i++) {
                from_w2_to_w1.add(w2.getResources().remove(0));
            }
            for (Resource res : from_w1_to_w2) {
                w2.getResources().add(res);
            }
            for (Resource res : from_w2_to_w1) {
                w1.getResources().add(res);
            }
            return true;
        }
        //A->S becomes S->A
        if (!firstIsStandard && secondIsStandard) {
            int temp = indexFirstWarehouse;
            indexFirstWarehouse = indexSecondWarehouse;
            indexSecondWarehouse = temp;

            temp=how_many_first;
            how_many_first = how_many_second;
            how_many_second = temp;

            firstIsStandard = true;
            secondIsStandard = false;

            Warehouse tempWarehouse =w1;
            w1=w2;
            w2=tempWarehouse;

            diff *=-1; //the index are swapped
        }
        // S->A
        if (firstIsStandard && !secondIsStandard) {
            /*
                We've 3 cases:
                1) Withdraw from w1 and put to w2
                2) Withdraw from w2 and put to w1
                3) Withdraw from both become the first or the second case
             */
            //3)
            if (diff > 0) {
                how_many_first -= how_many_second;
                how_many_second = 0;
            } else {
                how_many_second -= how_many_first;
                how_many_first = 0;
            }
            //1)
            if (how_many_first != 0 && how_many_second == 0) {
                //check how_many_first compatible with w1 and w2, check type
                if (!(how_many_first <= w1.getResources().size() && how_many_first <= w2.getSpaceAvailable() && w1.getResourceType().equals(w2.getResourceType()))) {
                    return false;
                }
                for (int i = 0; i < how_many_first; i++) {
                    w2.addResource(w1.getResources().remove(0)); //add resource will automatically manage spaceavailability
                }
                w1.changeAvailability(w1.getSpaceAvailable() + how_many_first);
                if(w1.getResources().size()==0){
                    w1.changeResourceType(ResourceType.ANY);
                }
                return true;
            }
            //2)
            else if (how_many_first == 0 && how_many_second != 0) {
                //check how_many_first compatible with w1 and w2, check type
                if (!(how_many_second <= w2.getResources().size() && how_many_second <= w1.getSpaceAvailable())) {
                    return false;
                }
                ArrayList<Resource> temp_from_additional = new ArrayList<>();
                for (int i = 0; i < how_many_second; i++) {
                    temp_from_additional.add(w2.getResources().remove(0));
                }
                w2.changeAvailability(w2.getSpaceAvailable() + how_many_second);
                if(!addResourceToWarehouseStandard(temp_from_additional,indexFirstWarehouse)){
                    addResourceToWarehouseAdditional(temp_from_additional,indexSecondWarehouse-3);
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}