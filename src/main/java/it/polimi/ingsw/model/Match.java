package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardReponse;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.market.MarketResponse;
import it.polimi.ingsw.controller.move.production.EnableProductionResponse;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesResponse;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.controller.move.swapWarehouse.SwapWarehouseResponse;
import it.polimi.ingsw.exceptions.EndRoundException;
import it.polimi.ingsw.exceptions.MoveResourcesException;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.market.MarketBoard;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.FileReader;
import it.polimi.ingsw.utils.Utils;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Abstract class containing the attributes and the methods shared by MatchSolo and MatchMulti
 */
public abstract class Match extends Observable<MoveResponse> implements Serializable {
    protected ArrayList<Player> players;
    protected Stack<LeaderCard> leaderCards;
    protected Stack<DevelopmentCard>[][] developmentCards;
    protected MarketBoard marketBoard;
    protected ArrayList<Resource> pendingResources;
    protected int numOfWhiteMarbleToBeConverted; //number of white marbles to be converted
    protected boolean canChangeTurn = false;

    //TODO: pensare a attributi aggiuntivi, esempio salvare su disco i record, memorizzare timestamp per sapere da quanto tempo si gioca ...

    /**
     * Constructor that intitialize the Match settings reading from a file all the information
     */
    public Match(int numberOfPlayers) {
        players = new ArrayList<>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) //to understand watch the addPLayer method
        {
            players.add(null);
        }
        leaderCards = FileReader.readLeaderCard();
        developmentCards = FileReader.readDevelopmentCards();
        marketBoard = MarketBoard.getInstance();
        pendingResources = new ArrayList<>();
    }

    public void startMatch() {
        for (Player player : players) {
            for (int i = 0; i < 4; i++)
                player.addLeaderCard(leaderCards.pop());
        }
    }

    public void notifyModel() {
        for (int i = 0; i < players.size(); i++) {
            notify(SendModel.getInstance(this, players.get(i), i));
        }
    }

    /*-----------------------------------------------------------------------------------------------
    GETTER
    -------------------------------------------------------------------------------------------------*/

    /**
     * Method to get a shallow copy of the {@link Player}'s {@link ArrayList}
     *
     * @return {@link Player} clone
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Method to get a shallow copy of the {@link LeaderCard}'s {@link ArrayList}
     *
     * @return {@link LeaderCard} clone
     */
    public ArrayList<LeaderCard> getLeaderCards() {
        return (ArrayList<LeaderCard>) leaderCards.clone();
    }

    /**
     * Method to get a shallow copy of the {@link DevelopmentCard} section
     *
     * @return {@link DevelopmentCard} clone
     */
    public Stack<DevelopmentCard>[][] getDevelopmentCards() {
        return (Stack<DevelopmentCard>[][]) developmentCards.clone();
    }

    /**
     * Method to get the marketboard
     *
     * @return {@link MarketBoard} clone, pay attention can be null
     */
    public MarketBoard getMarketBoard() {
        return marketBoard;
    }

    /**
     * Method to get the top {@link DevelopmentCard} of a {@link Stack} (combination of {@link DevelopmentCardLevel} and {@link DevelopmentCardType})
     *
     * @param type  {@link DevelopmentCardType} can be GREEN, BLUE, YELLOW,PURPLE
     * @param level {@link DevelopmentCardLevel} can be 1,2,3
     * @return The top {@link DevelopmentCard} of the {@link Stack}
     */
    public DevelopmentCard getDevelopmentCardOnTop(DevelopmentCardType type, DevelopmentCardLevel level) {
        return developmentCards[level.label][type.label].peek();
    }

    public boolean getCanChangeTurn() {
        return canChangeTurn;
    }
    /*-----------------------------------------------------------------------------------------------
    END GETTER
    -------------------------------------------------------------------------------------------------*/

    /**
     * Method used to add a player in the match, if the match is full the method will return false and also if a Player with that username is already present
     *
     * @param newPlayer the {@link Player} to be added to the {@link java.util.ArrayList}
     * @return true if the {@link Player} is correctly added, false if the {@link java.util.ArrayList} is already full or some error occur
     */
    public boolean addPlayer(Player newPlayer) {
        /*
            Frequency method will return how many not initialized players are there in the ArrayList, so how many players we've to wait for
            if frequency == 0 we've already initialized all the needed players
         */
        if (newPlayer == null) {
            return false;
        }
        if (players.indexOf(newPlayer) >= 0) {
            return false;
        }
        if ((Collections.frequency(getPlayers(), null)) == 0) {
            return false;
        }
        return players.set(getPlayers().indexOf(null), newPlayer) != null;
    }

    //TODO pensare a come ridefinire l'equals, possiamo mettere un nickname che deve essere univoco, quindi dovrei fare un ulteriore controllo in addplayer
    //TODO noi scegliamo di fare la persistenza, se sì l'utente va eliminato dopo x turni che non accede?
    public boolean removeDisconnectedPlayer(Player toRemove) {
        return false;
    }


    /**
     * Method to pick (get and remove) the top {@link DevelopmentCard} of a {@link Stack} (combination of {@link DevelopmentCardLevel} and {@link DevelopmentCardType})
     *
     * @param type  {@link DevelopmentCardType} can be GREEN, BLUE, YELLOW,PURPLE
     * @param level {@link DevelopmentCardLevel} can be 1,2,3
     * @return The top {@link DevelopmentCard} of the {@link Stack}
     */
    public DevelopmentCard pickDevelopmentCardOnTop(DevelopmentCardType type, DevelopmentCardLevel level) {
        return developmentCards[level.label][type.label].pop();
    }

    /*-----------------------------------------------------------------------------------------------
    PLAYER INTERACTION
    -------------------------------------------------------------------------------------------------*/

    /**
     * Method to discard a {@link LeaderCard}
     *
     * @param leaderCardPosition
     * @param player
     */
    public void discardLeaderCardInteraction(int leaderCardPosition, Player player) {
        if (player.discardLeaderCard(leaderCardPosition)) {
            notify(SendMessage.getInstance("Leader Card discarded, moved 1 position the faith path", player));
        } else {
            notify(SendMessage.getInstance("Something wrong, Leader Card cannot be discarded, retry", player));
        }
    }

    /**
     * Method to enable a {@link LeaderCard}
     *
     * @param leaderCardPosition
     * @param player
     */
    public void enableLeaderCardInteraction(int leaderCardPosition, Player player) {
        if (leaderCardPosition < player.getLeaderCards().size() && player.getLeaderCard(leaderCardPosition).active(player)) {
            notify(SendMessage.getInstance("Leader Card enabled", player));
        } else {
            notify(SendMessage.getInstance("Something wrong, Leader Card cannot be enabled, retry", player));
        }
    }

    /**
     * Method used to perform a {@link it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove} to let the {@link Player} to gain {@link Resource} from the {@link MarketBoard}
     * the resources gained are placed into pendingResources {@link ArrayList}
     *
     * @param moveType {@link MoveType} can be ROW / COlULMN
     * @param pos      which ROW/COLUMN to select
     * @param player   the {@link Player} performing the interaction
     */
    public void marketInteraction(MoveType moveType, int pos, Player player) {
        if ((moveType.equals(MoveType.ROW) && (pos < 0 || pos > 2)) || moveType.equals(MoveType.COLUMN) && (pos < 0 || pos > 3)) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
        } else {
            ArrayList<Resource> resourcesGained = marketBoard.getResources(moveType, pos);
            if (moveType.equals(MoveType.COLUMN)) {
                numOfWhiteMarbleToBeConverted = Utils.MARKET_COL_NUMBER - resourcesGained.size();   //how many white marbles are there in the selected COLUMN
            } else {
                numOfWhiteMarbleToBeConverted = Utils.MARKET_ROW_NUMBER - resourcesGained.size(); //how many white marbles are there in the selected ROW
            }
        /*
                If there are any white marbles selected and if the user which is requesting the marketInteraction
                has an additional strategy to convert white marbles we need to know if he wants to enable it,
                otherwise white marbles won't be converted
         */
            switch (player.numOfWhiteMarbleConversionStrategy()) {
                case 0:
                    numOfWhiteMarbleToBeConverted = 0;
                    break;
                case 1:
                    for (int i = 0; i < numOfWhiteMarbleToBeConverted; i++) {
                        resourcesGained.add(Resource.getInstance(player.getConversionStrategies().get(0)));
                    }
                    numOfWhiteMarbleToBeConverted = 0;
                    break;
            }
            //Faith resource conversion to faith position
            player.moveAheadFaith((int) resourcesGained.stream().filter(el -> el.getType().equals(ResourceType.FAITH)).count());
            pendingResources.addAll(resourcesGained.stream().filter(el -> el.getType() != ResourceType.FAITH).collect(Collectors.toList()));
            notifyModel();
            notify(MarketResponse.getInstance(resourcesGained, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player))));
        }
    }

    /**
     * Method used to perform the conversion of the white marbles, the {@link Resource} gained are placed into pendingResources {@link ArrayList}
     */
    public void marketMarbleConvertInteraction(int first, int second, Player player) {
        if ((first + second) <= numOfWhiteMarbleToBeConverted) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
            notify(MarketResponse.getInstance(pendingResources, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player))));
        } else {
            ArrayList<Resource> resourcesGained = (ArrayList<Resource>) pendingResources.clone();
            for (int i = 0; i < first; i++) {
                pendingResources.add(Resource.getInstance(player.getConversionStrategies().get(0)));
            }
            for (int i = 0; i < second; i++) {
                pendingResources.add(Resource.getInstance(player.getConversionStrategies().get(1)));
            }
            notifyModel();
            notify(MarketResponse.getInstance(resourcesGained, 0, new ArrayList<>(Arrays.asList(player))));
        }
    }

    /**
     * Method used by the {@link Player} to place {@link Resource} inside of {@link Warehouse}
     * If anything goes well the pendingResources {@link ArrayList} will be empty and the {@link Resource} which were inside of it will be stored inside {@link Warehouse}
     *
     * @param whereToPlace an {@link ArrayList} containing where to add (in which {@link Warehouse}) the iTH {@link Resource} of the pendingResources
     * @param player       the {@link Player} performing the action
     * @throws Exception an {@link Exception} to indicate if any error has happened
     */
    public void positioningResourcesInteraction(ArrayList<Integer> whereToPlace, Player player) throws Exception {
        /*
            Check
            1) if are there any resources to be placed
            2) if the 2 arraylist sizes are equal
            3) if the warehouses can correctly store everything (simulating all the transferring process using .clone())
            If no errors ==>
                            4) empty the pendingResources
                            if some Resource got discarded ==> 5) move ahead other players on the Faith Path
         */
        //CHECK 1)
        if (pendingResources.size() == 0) {
            return;
        }
        //CHECK 2)
        else if ((pendingResources.size() != whereToPlace.size())) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
            notify(MarketResponse.getInstance(pendingResources, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player))));
            return;
        }
        //CHECK 3)
        Gson gson = new Gson();
        String warehouseStandard = gson.toJson(player.getWarehousesStandard());
        String warehouseAdditional = gson.toJson(player.getWarehousesAdditional());
        ArrayList<Warehouse> standardTemp = player.getWarehousesStandard();
        ArrayList<Warehouse> additionalTemp = player.getWarehousesAdditional();

        int numberOfDiscardedResources = 0;
        int numberOfGainedResources = whereToPlace.size();
        for (int i = 0; i < whereToPlace.size(); i++) {
            if (whereToPlace.get(i) == null) {
                numberOfDiscardedResources++;
                numberOfGainedResources--;
            } else if (
                    (whereToPlace.get(i) >= 0 && whereToPlace.get(i) < 3
                            && (!standardTemp.get(whereToPlace.get(i)).addResource(pendingResources.get(i))))
                            || (whereToPlace.get(i) >= 3 && whereToPlace.get(i) < 5 && additionalTemp.size() > whereToPlace.get(i) - 3
                            && (!additionalTemp.get(whereToPlace.get(i)).addResource(pendingResources.get(i))))
            ) {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
                notify(MarketResponse.getInstance(pendingResources, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player))));
                ArrayList<Warehouse> temp = new ArrayList<>();
                Collections.addAll(temp, gson.fromJson(warehouseStandard, Warehouse[].class));
                player.setWarehousesStandard(temp);
                temp = new ArrayList<>();
                Collections.addAll(temp, gson.fromJson(warehouseAdditional, Warehouse[].class));
                player.setWarehousesAdditional(temp);
                return;
            }
        }
        //START 4)
        pendingResources = new ArrayList<>();
        //START 5)
        if (numberOfDiscardedResources != 0) {
            for (Player p : players) {
                if (!p.equals(player)) {
                    p.moveAheadFaith(numberOfDiscardedResources);
                }
            }
        }
        setCanChangeTurn(true, player);
        notifyModel();
        notify(PositioningResourcesResponse.getInstance(numberOfDiscardedResources, numberOfGainedResources, new ArrayList<>(Arrays.asList(player))));
    }

    /**
     * Method used to perform the user's purchase of a {@link DevelopmentCard}
     *
     * @param type     the {@link DevelopmentCardType} which specifies which column of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param level    the {@link DevelopmentCardLevel} which specifies which row of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param player   the {@link Player} which call the Move
     * @param posToAdd the int that specify in which user's {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace} the new {@link DevelopmentCard} has to be placed into
     */
    public void buyDevelopmentCardInteraction(DevelopmentCardType type, DevelopmentCardLevel level, Player player, int posToAdd, ArrayList<ResourcePick> resourceToUse) {
        //if the player can afford the development card requested
        ArrayList<ResourcesCount> resourcesCounts = resourceToUse.stream().map(elem -> ResourcesCount.getInstance(1,elem.getResourceType())).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Resource> resources = getDevelopmentCardOnTop(type, level).getPowers().getFrom().stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toCollection(ArrayList::new));
        //check if the resourto use are the required and if the player has this resources
        if (Utils.compareResources(resources, resourcesCounts) && player.canAfford(resourceToUse) && player.developmentCardCanBeAdded(DevelopmentCard.getInstance(level, type), posToAdd)) {
            DevelopmentCard temp_card = pickDevelopmentCardOnTop(type, level);
            if (player.addDevelopmentCard(temp_card, posToAdd)) //no errors
            {
                setCanChangeTurn(true, player);
                notify(BuyDevelopmentCardReponse.getInstance(temp_card, new ArrayList<>(Arrays.asList(player))));
            } else {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
            }
        } else if (!player.canAfford(resourceToUse)){
            notify(SendMessage.getInstance("Something wrong, Not enough resources", player));
        } else {
            notify(SendMessage.getInstance("Something wrong, Cannot be added (parameter error)", player));
        }
    }


    /**
     * Method used to perform the enable of the base interaction
     *
     * @param resourceToUse
     * @param to
     * @param player
     */
    public void enableProductionBaseInteraction(ArrayList<ResourcePick> resourceToUse, ResourceType to, Player player) {
        if (player.canAfford(resourceToUse)) {
            player.getStrongBox().add(Resource.getInstance(to));
        }
    }

    private void activateProductivePower(ProductivePower power, ArrayList<ResourcePick> resourceToUse, Player player) {
        //check if the resource to use are the same required for the leader card
        ArrayList<Resource> powerRequiredResources = power.getFrom().stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toCollection(ArrayList::new)); //arraylist di resources
        ArrayList<ResourcesCount> resourcesCounts = resourceToUse.stream().map(elem -> ResourcesCount.getInstance(1,elem.getResourceType())).collect(Collectors.toCollection(ArrayList::new));
        //check if the resourto use are the required and if the player has this resources
        if (Utils.compareResources(powerRequiredResources, resourcesCounts) && player.canAfford(resourceToUse)) {
            notify(EnableProductionResponse.getInstance(power.getTo(), new ArrayList<>(Arrays.asList(player))));
            player.getStrongBox().addAll(power.getTo());
        } else {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
        }
    }

    /**
     * Method used to perform the enable of the development interaction
     *
     * @param resourceToUse
     * @param positionOfDevelopmentCard
     * @param player
     */
    public void enableProductionDevelopmentInteraction(ArrayList<ResourcePick> resourceToUse, int positionOfDevelopmentCard, Player player) {
        if (positionOfDevelopmentCard >= player.getDevelopmentCardSpaces().size()) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
            return;
        }
        DevelopmentCard developmentCard = player.getDevelopmentCardSpaces().get(positionOfDevelopmentCard).pickTopCard();
        if (developmentCard == null) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
            return;
        }
        activateProductivePower(developmentCard.getPowers(), resourceToUse, player);
    }

    /**
     * Method used to perform the enable of the production interaction
     *
     * @param resourceToUse
     * @param positionOfProductivePower
     * @param player
     */
    public void enableProductionLeaderInteraction(ArrayList<ResourcePick> resourceToUse, int positionOfProductivePower, Player player) {
        if (positionOfProductivePower >= player.getAddedPower().size()) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player));
            return;
        }
        activateProductivePower(player.getAddedPower().get(positionOfProductivePower), resourceToUse, player);
    }

    /**
     * Method used by the {@link Player} to move {@link Resource} between two {@link Warehouse}
     *
     * @param indexFirstWarehouse  an int representing the first {@link Warehouse}
     * @param indexSecondWarehouse an int representing the second {@link Warehouse}
     * @param player               the {@link Player} that is performing the action
     * @throws MoveResourcesException the {@link MoveResourcesException} which is thrown if any error occur (not existing {@link Warehouse}, not enough available space ...)
     */
    public void MoveResourcesInteraction (int indexFirstWarehouse, int indexSecondWarehouse, int numberOfResources,  Player player) {

        if (indexFirstWarehouse == indexSecondWarehouse) {
            throw new MoveResourcesException();
        }
        if (indexFirstWarehouse == 3 || indexSecondWarehouse == 3) //check if the first additional warehouse exists
        {
            if (player.getWarehousesAdditional().size() == 0) {
                throw new MoveResourcesException();
            }
        }
        if (indexFirstWarehouse == 4 || indexSecondWarehouse == 4) //check if the second additional warehouse exists
        {
            if (player.getWarehousesAdditional().size() < 2) {
                throw new MoveResourcesException();
            }
        }
        if (player.moveResources(indexFirstWarehouse, indexSecondWarehouse, numberOfResources)) {
            notify(SwapWarehouseResponse.getInstance(new ArrayList<>(Arrays.asList(player))));
            return;
        }
        throw new MoveResourcesException();
    }

    public abstract void endRoundInteraction(Player player) throws EndRoundException;

    public void updateTurn() {
        /*
            Controllare:
            - è stata eseguita una mossa "sbloccante" (mercato, dev card, produzioni)
            - l'arraylist<Resource> pendingResources è vuoto
         */
    }

    public abstract boolean isMyTurn(Player player);

    public abstract void setCanChangeTurn(boolean canChangeTurn, Player player);

    /**
     * Method used to serialize the {@link Match} object
     *
     * @param location the path in which the {@link Match} has to be serialized
     * @return true if the {@link Match} is correctly serialized to the file system, false otherwise
     */
    public boolean writeToFileSystem(String location) {
        if (location == null) {
            return false;
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(location);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        System.out.println("MERCATO");
        System.out.println(marketBoard.getAdditionalMarble().toString());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(marketBoard.getRow(i).get(j).toString() + "|");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("CARTE SVILUPPO");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(developmentCards[i][j].peek().toString() + "|");
            }
            System.out.println();
        }
        System.out.println();
        return null;
    }
}
