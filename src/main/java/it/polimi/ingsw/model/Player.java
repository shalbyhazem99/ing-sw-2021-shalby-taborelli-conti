package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.PlayerMove;
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
    private ArrayList<DevelopmentCardSpace> developmentCardSpaces; //percè non pila?
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

    public String getName()
    {
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

    public void addLeaderCard(LeaderCard leaderCard){
        leaderCards.add(leaderCard);
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

    /**
     * Method used to know if the {@link Player} has enough {@link Resource} to enable the {@link ArrayList} of {@link ProductivePower}
     * @param productivePowers the {@link ArrayList} containing all the {@link ProductivePower} that the {@link Player} want to enable
     * @return true <==> all the {@link ProductivePower} can be enabled
     */
    public boolean canEnableProductivePowers(ArrayList<ProductivePower> productivePowers)
    {
        //Each productivePower contains an ArrayList of ResourceCount "from" which represents the resources needed to perform the production
        //Utils.comapre(a,b) return true <==> a include b
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
        //TODO: testare assolutamente e eventualmente rimuovere commenti sopra
        return productivePowers.parallelStream().anyMatch(elem->Utils.compareResources(getResources(), elem.getFrom()));
    }

    public boolean developmentCardCanBeAdded(DevelopmentCard developmentCard,int spacePos)
    {
        if (developmentCardSpaces.size() <= spacePos) {
            return false;
        }
        return (developmentCardSpaces.get(spacePos).canBeAdded(developmentCard));
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

    //TODO: da implementare
    public boolean hasWhiteMurbleConvertionStrategy()
    {
        return false;
    }
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Player)) {
            return false;
        }
        return this.name.equals(((Player) obj).getName());
    }

    /**
     * Method used to remove the {@link Resource} specified in the parameter from the {@link Warehouse} of the {@link Player} if they exists
     * PAY ATTENTION -> if
     * @param resources the {@link ArrayList} containing the {@link Resource} to be removed
     * @return true <==> no error occurs in the removing
     */
    //TODO: da rimuovere commento, ho testato col main e funziona
    public boolean removeResources(ArrayList<Resource> resources)
    {
        if(resources==null) { return true; }
        ArrayList<Warehouse> warehouses = (ArrayList<Warehouse>) Stream.concat(getWarehousesStandard().stream(),getWarehousesAdditional().stream()).collect(Collectors.toList());
        for (int resourceIndex = 0;resourceIndex<resources.size();resourceIndex++)
        {
            boolean removed = false;
            int warehouseIndex = 0;
            do{
                removed = warehouses.get(warehouseIndex).getResource(resources.get(resourceIndex));
                warehouseIndex++;
            } while (!removed && warehouseIndex<warehouses.size());
            if(!removed)
            {
                return false;
            }
        }
        return true;
    }
    //TODO : remove
    public boolean addToWar(Resource r,int n)
    {
        return warehousesStandard.get(n).addResource(r);

    }

    /**
     * Va fatto dentro player necessariamente perchè non possiamo esporre l'oggetto warehouse, i controlli sui parametri chiamati devono già essere stati fatti
     * @param indexFirstWarehouse
     * @param indexSecondWarehouse
     * @return
     */
    public int swapWarehouses(int indexFirstWarehouse, int indexSecondWarehouse)
    {
        /*
            CASES THAT MAY HAPPEN (S=STD,A=ADDITIONAL)
               w1    w2
            1) S <--> S
            2) A <--> A
            3) A <--> S
            4) S <--> A
         */
        Warehouse w1,w2;
        boolean firstIsStandard = true,secondIsStandard = true;
        if(indexFirstWarehouse==3||indexFirstWarehouse==4)
        {
            w1 = getWarehousesAdditional().get(indexFirstWarehouse-3);
            firstIsStandard = false;
        }
        else //0,1,2
        {
            w1 = getWarehousesStandard().get(indexFirstWarehouse);
        }
        if(indexSecondWarehouse==3||indexSecondWarehouse==4)
        {
            w2 = getWarehousesAdditional().get(indexSecondWarehouse-3);
            secondIsStandard = false;
        }
        else //0,1,2
        {
            w2 = getWarehousesStandard().get(indexSecondWarehouse);
        }
        if(firstIsStandard&&secondIsStandard) //1)
        {
            /*
                      WAR.SPACE      #RES CONTAINED

                    1) spaceMax = 1, 1 resource
                    3) spacemax = 3, 1 resource
                    ---------------------------
                    1) SWAP 3) ==> OKAY

                    1) spaceMax = 1, 1 resource
                    3) spacemax = 3, 2 resource
                    ---------------------------
                    1) SWAP 3) ==> ERROR

             */
            if(w1.getResources().size()<=w2.getSpaceAvailable() && w1.getResources().size()<=w2.getSpaceAvailable()) //check for space
            {
                //simply change the arraylist of resources
                ArrayList<Resource> temp = w1.getResources();
                w1.changeResources(w2.getResources());
                w2.changeResources(temp);
                return -1;
            }
            else
            {
                return -1;
            }
        }
        else if(!firstIsStandard&&!secondIsStandard) //2) A <==> A, we need to check the resType correctness, no space check, both of them can store max 2 resources
        {
            if(w1.getResourceType()!=w2.getResourceType())
            {
                return -1;
            }
            //simply change the arraylist of resources
            ArrayList<Resource> temp = w1.getResources();
            w1.changeResources(w2.getResources());
            w2.changeResources(temp);
            return w1.getResources().size()+w2.getResources().size();

        }
        else  //3) A <==> S 0R 4) S <==> A
        {
            if(w1.getResourceType()!=w2.getResourceType())
            {
                return -1;
            }
            if(w1.getResources().size()<=w2.getSpaceAvailable() && w1.getResources().size()<=w2.getSpaceAvailable()) //check for space
            {
                //simply change the arraylist of resources
                ArrayList<Resource> temp = w1.getResources();
                w1.changeResources(w2.getResources());
                w2.changeResources(temp);
                return w1.getResources().size()+w2.getResources().size();
            }
            else
            {
                return -1;
            }
        }
    }

    public static void main (String [] args)
    {
        //[0]-->3
        //[1]-->2
        //[2]-->1
        Player p = new Player("user");
        p.addToWar(new Resource(ResourceType.COIN),0);
        p.addToWar(new Resource(ResourceType.COIN),0);
        p.addToWar(new Resource(ResourceType.COIN),0);
        p.addToWar(new Resource(ResourceType.SHIELD),1);
        p.addToWar(new Resource(ResourceType.SHIELD),1);
        p.addToWar(new Resource(ResourceType.COIN),2);
        ArrayList<Resource> v = new ArrayList<>();
        v.add(new Resource(ResourceType.COIN));
        v.add(new Resource(ResourceType.COIN));
        v.add(new Resource(ResourceType.SHIELD));
        v.add(new Resource(ResourceType.SHIELD));
        v.add(new Resource(ResourceType.SHIELD));
        boolean a = p.removeResources(null);
        System.out.println("asa");

    }
}
