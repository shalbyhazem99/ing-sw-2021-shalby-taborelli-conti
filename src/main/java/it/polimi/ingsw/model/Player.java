package it.polimi.ingsw.model;

import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
        this.name=name;
        popeFavorTiles = generatePopeFavorTiles();
        leaderCards = new ArrayList<>();
        developmentCardSpaces = new ArrayList<>();
        warehousesStandard = generateWarehouse();
        strongBox = new ArrayList<>();
        warehousesAdditional = new ArrayList<>();
        discounts = new ArrayList<>();
        conversionStrategies = new ArrayList<>();
        addedPower = generatePower();
    }

    public static Player getInstance(String name) {
        return new Player(name);
    }
    public static Player getInstance() {
        return new Player("unknown");
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
        temp.add(Warehouse.getInstance(3, ResourceType.ANY));
        temp.add(Warehouse.getInstance(2, ResourceType.ANY));
        temp.add(Warehouse.getInstance(1, ResourceType.ANY));
        return temp;
    }

    public static ArrayList<ProductivePower> generatePower() {
        return new ArrayList<>(Arrays.asList(ProductivePower.getInstance(
                new ArrayList<>(Arrays.asList(ResourcesCount.getInstance(2, ResourceType.ANY))),//todo: to see
                new ArrayList<>(Arrays.asList(Resource.getInstance(ResourceType.ANY))))));
    }

    //TODO: production
    /**
     * move the Faith Marker Ahead
     *
     * @param pos the number of movement
     */
    public void moveAheadFaith(int pos) {
        posFaithMarker += pos;
        posFaithMarker = Math.min(Utils.FAITH_LENGTH, posFaithMarker);
    }

    /**
     * @param developmentCards {@link ArrayList} of {@link DevelopmentCard} containing cards the player wants to buy
     * @return true <==> the {@link Player} (considering the {@link ArrayList} of discounts) can afford all the cards inside developmentCards' {@link ArrayList}
     */
    public boolean canAfford(ArrayList<DevelopmentCard> developmentCards) {
        //get all the resourceCount needed by the developmentCards the player wants to buy
        ArrayList<ResourcesCount> needed =
                (ArrayList<ResourcesCount>) developmentCards
                        .stream()
                        .flatMap(elem -> elem.getCosts().stream())
                        .collect(Collectors.toList());
        return isActionable(needed);
    }

    //getter

    /**
     * get the faith position
     *
     * @return the faith position
     */
    public int getPosFaithMarker() {
        return posFaithMarker;
    }

    /**
     * @return a shallow copy of the warehouseStandard {@link ArrayList} of {@link Warehouse} of the {@link Player}
     */
    public ArrayList<Warehouse> getWarehousesStandard() {
        return (ArrayList<Warehouse>) warehousesStandard.clone();
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
        return (ArrayList<Warehouse>) warehousesAdditional.clone();
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

    //setter
    public boolean discardLeaderCard(LeaderCard leaderCard){
        if(!leaderCard.isActive() && leaderCards.contains(leaderCard)){
            if(leaderCards.remove(leaderCard)){
                moveAheadFaith(1);
                return true;
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

    public void addResourceToStrongBox(Resource resource) {
        strongBox.add(resource);
    }
    public void addResourceToStrongBox(ArrayList<Resource> resources) {
        strongBox.addAll(resources);
    }

    public boolean addResourceToWarehouseAdditional(Resource resource, int index){
        if(index>warehousesAdditional.size())
            return false;
        if(!resource.getType().equals(warehousesAdditional.get(index).getResourceType()) || warehousesAdditional.get(index).getSpaceAvailable()< warehousesAdditional.get(index).getResources().size()+1)
            return false;
        warehousesAdditional.get(index).addResource(resource);
        return true;
    }

    public boolean addResourceToWarehouseAdditional(ArrayList<Resource> resources, int index){
        for (Resource resource:resources){
            if(!addResourceToWarehouseAdditional(resource,index))
                return false;
        }
        return true;
    }

    public boolean addResourceToWarehouseStandard(Resource resource, int index){
        if(index>warehousesStandard.size())
            return false;
        if(!resource.getType().equals(warehousesStandard.get(index).getResourceType()) || warehousesAdditional.get(index).getSpaceAvailable()< warehousesAdditional.get(index).getResources().size()+1)
            return false;
        //if there are other warehouses with the same type of card
        if(0 <warehousesStandard.stream()
                .filter(elem-> !elem.equals(warehousesStandard.get(index)) &&
                        elem.getResourceType().equals(warehousesStandard.get(index).getResourceType()))
                .count())
            return false;
        warehousesAdditional.get(index).addResource(resource);
        return true;
    }

    public boolean addResourceToWarehouseStandard(ArrayList<Resource> resources, int index){
        for (Resource resource:resources){
            if(!addResourceToWarehouseStandard(resource,index))
                return false;
        }
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
}
