package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player implements Serializable {
    private int posFaithMarker;
    private ArrayList<PopeFavorTiles> popeFavorTiles;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<DevelopmentCardSpace> developmentCardSpaces;
    private ArrayList<Warehouse> warehousesStandard;
    private ArrayList<Resource> strongBox;
    private ArrayList<Warehouse> warehousesAdditional;
    private ArrayList<ResourcesCount> discounts;
    private ArrayList<ResourceType> ConversionStrategies;
    private ArrayList<ProductivePower> addedPower;

    //todo: it has a power?
    public static Player generatePlayer(){
        //TODO:genera n giocatore
        return null;
    }
    //TODO: andrà rimosso inserito soltanto per testare col main
    public Player(ArrayList<Warehouse> w, ArrayList<Warehouse> a, ArrayList<Resource> r)
    {
        this.warehousesAdditional = a;
        this.strongBox = r;
        this.warehousesStandard = w;
    }

    public void procediPerorsoFede(int numPosizioni){
        //TODO: va avanti
    }

    public Boolean addCartaSiluppo(DevelopmentCard developmentCard){
        //TODO: fare il controllo se i pò agiungere e aggiungerla. bisogna vedere come ricevere lo spazio sviluppo in cui provare ad aggiungerla
        return false;
    }
    public void addRisorsaForziere(Resource resource){
        //TODO:Aahhiunge una risorsa al forziere
    }

    /**
     *
     * @param developmentCards {@link ArrayList} of {@link DevelopmentCard} containing cards the player wants to buy
     * @return true <==> the {@link Player} (considering the {@link ArrayList} of discounts) can afford all the cards inside developmentCards' {@link ArrayList}
     */
    public boolean canAfford(ArrayList<DevelopmentCard> developmentCards)
    {
        //get all the resourcecount needed by the developmerntcards the player wants to buy
        ArrayList<ResourcesCount> needed =
                (ArrayList<ResourcesCount>) developmentCards
                        .stream()
                        .flatMap(elem->elem.getCosts().stream())
                        .collect(Collectors.toList());
        //needed = Utils.applyDiscount(needed,getDiscounts()); //apply discount
        //return Utils.compareResources(getResources(),needed);
        return false;
    }

    /**
     *
     * @return an {@link ArrayList} of {@link Resource} containing all the resources coming from the warehouses and the strongbox
     */
    public ArrayList<Resource> getResources()
    {
        return (ArrayList<Resource>) Stream.concat(Stream.concat(getStrongBox().stream(),getWarehousesStandard().stream().flatMap(elem->elem.getResources().stream())),getWarehousesAdditional().stream().flatMap(elem->elem.getResources().stream())).collect(Collectors.toList());
    }

    /**
     *
     * @return a shallow copy of the discounts' {@link ArrayList} of {@link ResourcesCount} of the {@link Player}
     */
    public ArrayList<ResourcesCount> getDiscounts() {
        return (ArrayList<ResourcesCount>)discounts.clone();
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards(){
        return null;
    }
    public boolean hasDiscount(){
        return getDiscounts().size()>0;
    }

    public boolean isActivable(ArrayList<ResourcesCount> resourcesNeeded){
        ArrayList<ResourcesCount> resNeeded = resourcesNeeded;
        if(hasDiscount()){
            resNeeded = Utils.applyDiscount(resourcesNeeded,getDiscounts());
        }
        return Utils.compareResources(getResources(),resNeeded);
    }

    /**
     *
     * @return a shallow copy of the warehousestandar's {@link ArrayList} of {@link Warehouse} of the {@link Player}
     */
    public ArrayList<Warehouse> getWarehousesStandard() {
        return (ArrayList<Warehouse>)warehousesStandard.clone();
    }

    /**
     *
     * @return a shallow copy of the strongbox {@link ArrayList} of {@link Resource} of the {@link Player}
     */
    public ArrayList<Resource> getStrongBox() {
        return (ArrayList<Resource>)strongBox.clone();
    }

    /**
     *
     * @return a shallow copy of the {@link ArrayList} of additional {@link Warehouse} of the {@link Player}
     */
    public ArrayList<Warehouse> getWarehousesAdditional() {
        return (ArrayList<Warehouse>)warehousesAdditional.clone();
    }

    public void addDiscount(ResourcesCount resourcesCount){

    }

    public void addPower(ProductivePower productivePower){

    }

    public void addConversionStrategies(ResourceType resourceType){

    }

    public void addAdditionalWarehouse(Warehouse warehouse){

    }
}
