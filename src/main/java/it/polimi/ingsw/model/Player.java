package it.polimi.ingsw.model;

import java.io.Serializable;
import java.lang.reflect.Array;
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

    //TODO: to remove main
    public static void main (String [] args)
    {
        //warehouse, resource, warehouse
        ArrayList<Resource> res = new ArrayList<>();
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.SERVANT));
        Warehouse w1 = new Warehouse(1,null,res); //2 monete 1 servo
        res.add(new Resource(ResourceType.SERVANT));
        Warehouse w2 = new Warehouse(1,null,res); //2 monete 2 servo
        res.add(new Resource(ResourceType.SHIELD));
        ArrayList<Warehouse> tre_depositi_standard = new ArrayList<>(); //struttura che contiene i due depositi sopra
        tre_depositi_standard.add(w1);
        tre_depositi_standard.add(w2);
        Warehouse wadditional = new Warehouse(1,null,res); //2 monete 2 servo 1 scudo
        ArrayList<Warehouse> lista_additional = new ArrayList<>();
        lista_additional.add(wadditional);
        res.remove(0);
        res.remove(0);
        res.add(new Resource(ResourceType.FAITH));
        res.add(new Resource(ResourceType.FAITH));
        res.add(new Resource(ResourceType.FAITH));
        res.add(new Resource(ResourceType.FAITH));
        res.add(new Resource(ResourceType.FAITH));
        res.add(new Resource(ResourceType.FAITH)); //res sarebbe l'arraylist di risorse ovvero il forziere semplice e illimitato
        Player p = new Player(tre_depositi_standard,lista_additional,(ArrayList<Resource>)res.clone());
        ArrayList<Resource> risorsetotali = p.getResources();
        //2 monete 1 servo
        //2 monete 2 servo
        //2 monete 2 servo 1 scudo
        //0 monete 2 servo 1 scudo 6 fede
        //totale
        //6 monete 7 servo 2 scudo 6 fede
        //risorse totale = 21
    }
}
