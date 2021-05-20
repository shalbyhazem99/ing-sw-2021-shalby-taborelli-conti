package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player implements Serializable {
    private String name;
    private int posFaithMarker;
    private ArrayList<PopeFavorTiles> popeFavorTiles;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<DevelopmentCardSpace> developmentCardSpaces;
    private ArrayList<Warehouse> warehousesStandard;
    private ArrayList<Resource> strongBox;
    private ArrayList<Warehouse> warehousesAdditional;
    private ArrayList<ResourcesCount> discounts;
    private ArrayList<ResourceType> conversionStrategies;
    private ArrayList<ProductivePower> addedPower;

    public Player(String name) {
        this.name = name;
        popeFavorTiles = generatePopeFavorTiles();
        leaderCards = new ArrayList<>();
        geneDevelopmentCardSpaces();
        warehousesStandard = generateWarehouse();
        strongBox = new ArrayList<>();
        warehousesAdditional = new ArrayList<>();
        discounts = new ArrayList<>();
        conversionStrategies = new ArrayList<>();
        addedPower = generatePower();
    }

    public void geneDevelopmentCardSpaces() {
        developmentCardSpaces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            developmentCardSpaces.add(DevelopmentCardSpace.getInstance());
        }
    }

    public String getName() {
        return name;
    }

    public static Player getInstance(String name) {
        return new Player(name);
    }

    public static Player getInstance() {
        return new Player("unknown");
    }

    public ArrayList<PopeFavorTiles> getPopeFavorTiles() {
        return popeFavorTiles;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public LeaderCard getLeaderCard(int position) {
        if (position < leaderCards.size())
            return leaderCards.get(position);
        return null;
    }

    public ArrayList<ResourceType> getConversionStrategies() {
        return conversionStrategies;
    }

    public ArrayList<ProductivePower> getAddedPower() {
        return addedPower;
    }

    public static ArrayList<PopeFavorTiles> generatePopeFavorTiles() {
        ArrayList<PopeFavorTiles> temp = new ArrayList<>();
        temp.add(PopeFavorTiles.getInstance(2));
        temp.add(PopeFavorTiles.getInstance(3));
        temp.add(PopeFavorTiles.getInstance(4));
        return temp;
    }

    public static ArrayList<Warehouse> generateWarehouse() {
        ArrayList<Warehouse> temp = new ArrayList<>();

        temp.add(Warehouse.getInstance(1, ResourceType.ANY));
        temp.add(Warehouse.getInstance(2, ResourceType.ANY));
        temp.add(Warehouse.getInstance(3, ResourceType.ANY));
        return temp;
    }

    public static ArrayList<ProductivePower> generatePower() {
        return new ArrayList<>(Arrays.asList(ProductivePower.getInstance(
                new ArrayList<>(Arrays.asList(ResourcesCount.getInstance(2, ResourceType.ANY))),//todo: to see
                new ArrayList<>(Arrays.asList(Resource.getInstance(ResourceType.ANY))))));
    }

    public void addLeaderCard(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    /**
     * move the Faith Marker Ahead
     *
     * @param pos the number of movement
     */
    public void moveAheadFaith(int pos) {
        posFaithMarker += pos;
        posFaithMarker = Math.min(Utils.FAITH_LENGTH, posFaithMarker);
        //tODO: CONTROL IF THE PLAYER ACTIVATE ANY CARD, MUST GET THE PLAYER LIST
    }

    //todo:testing

    /**
     * Verify if the {@link Player} could activate the power and remove the resources
     *
     * @param resourceToUse
     * @return
     */
    public boolean canAfford(ArrayList<ResourcePick> resourceToUse) {
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
                    } else if (additionalTemp.size() > resourcePick.getWarehousePosition() - 3 && resourcePick.getWarehousePosition() >= 3 && resourcePick.getWarehousePosition() < 5) {
                        resToBeRemoved = additionalTemp.get(resourcePick.getWarehousePosition() - 3).getResources();
                    } else {
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


    /**
     * get the faith position
     *
     * @return the faith position
     */
    public int getPosFaithMarker() {
        return posFaithMarker;
    }

    public void setWarehousesStandard(ArrayList<Warehouse> warehousesStandard) {
        this.warehousesStandard = warehousesStandard;
    }

    public void setWarehousesAdditional(ArrayList<Warehouse> warehousesAdditional) {
        this.warehousesAdditional = warehousesAdditional;
    }

    public void setStrongBox(ArrayList<Resource> strongBox) {
        this.strongBox = strongBox;
    }

    /**
     * @return a shallow copy of the warehouseStandard {@link ArrayList} of {@link Warehouse} of the {@link Player}
     */
    public ArrayList<Warehouse> getWarehousesStandard() {
        return warehousesStandard;
    }

    /**
     * @return a shallow copy of the strongbox {@link ArrayList} of {@link Resource} of the {@link Player}
     */
    public ArrayList<Resource> getStrongBox() {
        return (ArrayList<Resource>) strongBox.clone();
    }

    /**
     * @return a shallow copy of the {@link ArrayList} of additional {@link Warehouse} of the {@link Player}
     */
    public ArrayList<Warehouse> getWarehousesAdditional() {
        return warehousesAdditional;
    }

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
     * @return a shallow copy of the discounts' {@link ArrayList} of {@link ResourcesCount} of the {@link Player}
     */
    public ArrayList<ResourcesCount> getDiscounts() {
        return (ArrayList<ResourcesCount>) discounts.clone();
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

    public ArrayList<DevelopmentCardSpace> getDevelopmentCardSpaces() {
        return developmentCardSpaces;
    }

    public boolean hasDiscount() {
        return getDiscounts().size() > 0;
    }


    public boolean isActionable(ArrayList<ResourcesCount> resourcesNeeded) {
        ArrayList<ResourcesCount> resNeeded = resourcesNeeded;
        if (hasDiscount()) {
            resNeeded = Utils.applyDiscount(resourcesNeeded, getDiscounts());
        }
        return Utils.compareResources(getResources(), resNeeded);
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
            if (!leaderCard.isActive() && leaderCards.contains(leaderCard)) {
                if (leaderCards.remove(leaderCard)) {
                    moveAheadFaith(1);
                    return true;
                }
            }
        }
        return false;
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

    //todo: do we still use this method?

    /**
     * Method used to know if the {@link Player} has enough {@link Resource} to enable the {@link ArrayList} of {@link ProductivePower}
     *
     * @param productivePowers the {@link ArrayList} containing all the {@link ProductivePower} that the {@link Player} want to enable
     * @return true <==> all the {@link ProductivePower} can be enabled
     */
    public boolean canEnableProductivePowers(ArrayList<ProductivePower> productivePowers) {
        //Each productivePower contains an ArrayList of ResourceCount "from" which represents the resources needed to perform the production
        //Utils.compare(a,b) return true <==> a include b
        //setOfE.parallelStream().anyMatch(e->eval(e));
        /*
        boolean anyTrue() {
            for (Element e : setOfE) {
                if (eval(e)) {
                    return true;
                  }
                }
            return false;
            }
         */
        return productivePowers.parallelStream().anyMatch(elem -> Utils.compareResources(getResources(), elem.getFrom()));
    }

    public boolean developmentCardCanBeAdded(DevelopmentCard developmentCard, int spacePos) {
        if (developmentCardSpaces.size() <= spacePos) {
            return false;
        }
        return (developmentCardSpaces.get(spacePos).canBeAdded(developmentCard));
    }

    public void addResourceToStrongBox(Resource resource) {
        strongBox.add(resource);
    }

    //todo: do we still use this method?
    public void addResourceToStrongBox(ArrayList<Resource> resources) {
        strongBox.addAll(resources);
    }

    /**
     * Class used only during the testing phase
     */
    public boolean addResourceToWarehouseAdditional(Resource resource, int index) {
        if (index > warehousesAdditional.size())
            return false;
        if(warehousesAdditional.get(index).getResourceType()==resource.getType()){
            warehousesAdditional.get(index).addResource(resource);
            return true;
        }
        return false;
    }

    /**
     * It's a dump class just used during the testing in order to make easier the phase
     */
    public boolean addResourceToWarehouseStandard(Resource resource, int index) {

        if (index > warehousesStandard.size())
            return false;
        warehousesStandard.get(index).addResource(resource);
        return true;
    }

    //setter Leader Card
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

    public int numOfWhiteMarbleConversionStrategy() {
        return conversionStrategies.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        return this.name.equals(((Player) obj).getName());
    }

    //todo: do we still use this method?

    /**
     * Method used to remove the {@link Resource} specified in the parameter from the {@link Warehouse} of the {@link Player} if they exists
     * PAY ATTENTION -> if
     *
     * @param resources the {@link ArrayList} containing the {@link Resource} to be removed
     * @return true <==> no error occurs in the removing
     */
    public boolean removeResources(ArrayList<Resource> resources) {
        if (resources == null) {
            return true;
        }
        ArrayList<Warehouse> warehouses = (ArrayList<Warehouse>) Stream.concat(getWarehousesStandard().stream(), getWarehousesAdditional().stream()).collect(Collectors.toList());
        for (int resourceIndex = 0; resourceIndex < resources.size(); resourceIndex++) {
            boolean removed = false;
            int warehouseIndex = 0;
            do {
                removed = warehouses.get(warehouseIndex).getResource(resources.get(resourceIndex));
                warehouseIndex++;
            } while (!removed && warehouseIndex < warehouses.size());
            if (!removed) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@link Player} choose how many {@link Resource} to move from the selected Warehouse and where to put them
     * @param indexFirstWarehouse index of the source Warehouse
     * @param indexSecondWarehouse index of the destination Warehouse
     * @param resourcesFromFirstWarehouse number of the Resources to move
     * @return result of the operation
     */
    public boolean moveResources(int indexFirstWarehouse, int indexSecondWarehouse, int resourcesFromFirstWarehouse){
        Warehouse w1, w2;
        boolean firstIsStandard = true, secondIsStandard = true;
        //getting the warehouses from the player
        if (indexFirstWarehouse == 3 || indexFirstWarehouse == 4) {
            w1 = getWarehousesAdditional().get(indexFirstWarehouse - 3);
            firstIsStandard = false;
        }
        else {
            w1 = getWarehousesStandard().get(indexFirstWarehouse);
        }
        if (indexSecondWarehouse == 3 || indexSecondWarehouse == 4) {
            w2 = getWarehousesAdditional().get(indexSecondWarehouse - 3);
            secondIsStandard = false;
        }
        else{
            w2 = getWarehousesStandard().get(indexSecondWarehouse);
        }

        // Different Cases
        if (w1.getResources().size()<resourcesFromFirstWarehouse){
            return false;
        }
        //S<->S
        if (firstIsStandard && secondIsStandard && resourcesFromFirstWarehouse<w2.getSpaceAvailable()+w2.getResources().size() ){
            if (w2.getResourceType()==ResourceType.ANY && resourcesFromFirstWarehouse!= w1.getResources().size()){
                return false;
            }
            ArrayList<Resource> temp = w1.getResources();
            ResourceType resourceType_w1 = w1.getResourceType();
            ResourceType resourceType_w2 = w2.getResourceType();

            w1.changeResources(w2.getResources());
            w1.changeResourceType(resourceType_w2);
            if (w1.getResources().size()==0){
                w1.changeResourceType(ResourceType.ANY);
                w1.changeAvailability(indexFirstWarehouse+1);
            }
            w1.changeAvailability(indexFirstWarehouse+1-w1.getResources().size());
            w2.changeResources(temp);
            w2.changeResourceType(resourceType_w1);
            if (w2.getResources().size()==0){
                w2.changeResourceType(ResourceType.ANY);
                w2.changeAvailability(indexSecondWarehouse+1);
            }
            w2.changeAvailability(indexSecondWarehouse+1-w2.getResources().size());
            return true;
        }
        // S->A || A->S the Resources are moved only if the two warehouses has the same ResourceType
        else if((w1.getResourceType()==w2.getResourceType() || w2.getResourceType()== ResourceType.ANY) && (resourcesFromFirstWarehouse<=w2.getSpaceAvailable())){
            if (w2.getResourceType()== ResourceType.ANY){
                w2.changeResourceType(w1.getResourceType());
            }
            for (int i = 0; i < resourcesFromFirstWarehouse; i++) {
                w1.getResources().remove(0);
                w1.changeAvailability(w1.getSpaceAvailable()+1);
                w2.getResources().add(Resource.getInstance(w2.getResourceType()));
                w2.changeAvailability(w2.getSpaceAvailable()-1);
            }
            if(w1.getResources().size()==0 && firstIsStandard){
                w1.changeResourceType(ResourceType.ANY);
            }
            return true;
        }
        return false;
    }


}
