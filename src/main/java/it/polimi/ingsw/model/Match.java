package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.leaderCard.DiscardTwoLeaderCardsResponse;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardReponse;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.market.MarketResponse;
import it.polimi.ingsw.controller.move.production.EnableProductionResponse;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesResponse;
import it.polimi.ingsw.controller.move.settings.AskForMove;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.controller.move.moveResources.MoveResourcesResponse;
import it.polimi.ingsw.controller.move.leaderCard.DiscardLeaderCardResponse;
import it.polimi.ingsw.controller.move.leaderCard.EnableLeaderCardResponse;
import it.polimi.ingsw.exceptions.MoveResourcesException;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.market.MarketBoard;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
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
    protected ArrayList<Resource> pendingMarketResources;
    protected boolean productionActive;
    protected ArrayList<Resource> pendingProductionResources;
    protected int numOfWhiteMarbleToBeConverted; //number of white marbles to be converted
    protected boolean canChangeTurn = false;
    protected int numPlayerWhoDiscard = 0;
    protected transient int whoAmI;

    //when something wrong: resent the entire model
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
        pendingMarketResources = new ArrayList<>();
        pendingProductionResources = new ArrayList<>();
    }

    public void startMatch() {
        for (Player player : players) {
            for (int i = 0; i < 4; i++)
                player.addLeaderCard(leaderCards.pop());
        }
        notifyModel();
        //todo: to remove
        for (Player player : players) {
            ArrayList<ResourcesCount> aa = new ArrayList<>();
            aa.add(ResourcesCount.getInstance(10, ResourceType.COIN));
            aa.add(ResourcesCount.getInstance(10, ResourceType.SERVANT));
            aa.add(ResourcesCount.getInstance(10, ResourceType.SHIELD));
            aa.add(ResourcesCount.getInstance(10, ResourceType.STONE));
            Warehouse w1,w2;
            w1 = new Warehouse(2,ResourceType.COIN);
            w2 = new Warehouse(2,ResourceType.SHIELD);
            /*w1.addResource(Resource.getInstance(ResourceType.COIN));
            w1.addResource(Resource.getInstance(ResourceType.COIN));
            w2.addResource(Resource.getInstance(ResourceType.COIN));
            */
            player.addAdditionalWarehouse(w1);
            player.addAdditionalWarehouse(w2);
            player.addResourceToStrongBox((ArrayList<Resource>) aa.stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toList()));
        }
        askForDiscardLeaderCard();
    }

    public void askForDiscardLeaderCard() {
        for (int i = 0; i < players.size(); i++) {
            notify(DiscardTwoLeaderCardsResponse.getInstance(players.get(i), i, this.hashCode(),0));
        }
    }

    public void notifyModel() {
        for (int i = 0; i < players.size(); i++) {
            notify(SendModel.getInstance(this, players.get(i), i, this.hashCode()));
        }
    }

    /*-----------------------------------------------------------------------------------------------
    GETTER
    -------------------------------------------------------------------------------------------------*/

    /**
     * Return the {@link Player} int the given position
     *
     * @param pos the position of the {@link Player} to return
     * @return the {@link Player} in the given position
     */
    public Player getPlayerFromPosition(int pos) {
        return players.get(pos);
    }

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

    public void setWhoAmI(int whoAmI) {
        this.whoAmI = whoAmI;
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

    public void discardTwoLeaderCardInteraction(int posFirst, int posSecond, Player player, ResourceType resourceTypeFirst, ResourceType resourceTypeSecond) {
        if (posFirst != posSecond && posFirst >= 0 && posSecond >= 0 && posFirst < player.getLeaderCards().size() && posSecond < player.getLeaderCards().size()) {
            LeaderCard first = player.getLeaderCard(posFirst);
            LeaderCard second = player.getLeaderCard(posSecond);
            player.getLeaderCards().remove(first);
            player.getLeaderCards().remove(second);
            numPlayerWhoDiscard++;
        } else {
            notify(SendMessage.getInstance("Something wrong, Leader Cards cannot be discarded, retry", player, players.indexOf(player), this.hashCode()));
            notify(DiscardTwoLeaderCardsResponse.getInstance(player, players.indexOf(player), this.hashCode(),0 ) );
            return;
        }
    }


    /**
     * Method to discard a {@link LeaderCard}
     *
     * @param leaderCardPosition
     * @param player
     */
    public void discardLeaderCardInteraction(int leaderCardPosition, Player player, boolean noControl) {
        if (noControl) {
            player.discardLeaderCard(leaderCardPosition);
            return;
        }
        if (player.discardLeaderCard(leaderCardPosition)) {
            notify(DiscardLeaderCardResponse.getInstance(players, players.indexOf(player), leaderCardPosition, this.hashCode()));
        } else {
            notify(SendMessage.getInstance("Something wrong, Leader Card cannot be discarded, retry", player, players.indexOf(player), this.hashCode()));
        }
        askForMove();
    }

    /**
     * Method to enable a {@link LeaderCard}
     *
     * @param leaderCardPosition
     * @param player
     */
    public void enableLeaderCardInteraction(int leaderCardPosition, Player player, boolean noControl) {
        if (noControl) {
            player.getLeaderCard(leaderCardPosition).active(player);
            return;
        }
        if (leaderCardPosition < player.getLeaderCards().size() && player.getLeaderCard(leaderCardPosition).active(player)) {
            notify(EnableLeaderCardResponse.getInstance(players, players.indexOf(player), leaderCardPosition, this.hashCode()));
        } else {
            notify(SendMessage.getInstance("Something wrong, Leader Card cannot be enabled, retry", player, players.indexOf(player), this.hashCode()));
        }
        askForMove();
    }

    public void askForMove() {
        ArrayList<MovePlayerType> possibleMove = new ArrayList<>();
        if (!canChangeTurn) {
            possibleMove.add(MovePlayerType.MARKET_INTERACTION);
            possibleMove.add(MovePlayerType.BUY_DEVELOPMENT_CARD);
        }
        //the player didn't do anything before or he plays another production before
        if (!canChangeTurn || productionActive) {
            possibleMove.add(MovePlayerType.ENABLE_PRODUCTION);
        }
        possibleMove.add(MovePlayerType.ENABLE_LEADER_CARD);
        possibleMove.add(MovePlayerType.DISCARD_LEADER_CARD);
        possibleMove.add(MovePlayerType.MOVE_RESOURCES);
        possibleMove.add(MovePlayerType.END_TURN);
        notify(AskForMove.getInstance(new ArrayList<>(Arrays.asList(getCurrentPlayer())), possibleMove, players.indexOf(getCurrentPlayer()), this.hashCode()));
    }

    /**
     * Method used to perform a {@link it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove} to let the {@link Player} to gain {@link Resource} from the {@link MarketBoard}
     * the resources gained are placed into pendingResources {@link ArrayList}
     *
     * @param moveType {@link MoveType} can be ROW / COlULMN
     * @param pos      which ROW/COLUMN to select
     * @param player   the {@link Player} performing the interaction
     */
    public void marketInteraction(MoveType moveType, int pos, Player player, boolean noControl) {
        if (!noControl && ((moveType.equals(MoveType.ROW) && (pos < 0 || pos > 2)) || moveType.equals(MoveType.COLUMN) && (pos < 0 || pos > 3))) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters 1", player, players.indexOf(player), this.hashCode()));
            askForMove();
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
            controlPopePath();
            pendingMarketResources.addAll(resourcesGained.stream().filter(el -> el.getType() != ResourceType.FAITH).collect(Collectors.toList()));
            //notifyModel();
            if (!noControl)
                notify(MarketResponse.getInstance(resourcesGained, numOfWhiteMarbleToBeConverted, players, players.indexOf(player), moveType, pos, this.hashCode()));
        }
    }

    /**
     * Method used to perform the conversion of the white marbles, the {@link Resource} gained are placed into pendingResources {@link ArrayList}
     */
    public void marketMarbleConvertInteraction(int first, int second, Player player, boolean noControl) {
        if (!noControl && ((first + second) > numOfWhiteMarbleToBeConverted)) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters 2", player, players.indexOf(player), this.hashCode()));
            notify(MarketResponse.getInstance(pendingMarketResources, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player)), players.indexOf(player), -1, 0, this.hashCode()));
        } else {
            ArrayList<Resource> resourcesGained = (ArrayList<Resource>) pendingMarketResources.clone();
            for (int i = 0; i < first; i++) {
                pendingMarketResources.add(Resource.getInstance(player.getConversionStrategies().get(0)));
            }
            for (int i = 0; i < second; i++) {
                pendingMarketResources.add(Resource.getInstance(player.getConversionStrategies().get(1)));
            }
            //notifyModel();
            if (!noControl)
                notify(MarketResponse.getInstance(resourcesGained, 0, players, players.indexOf(player), first, second, this.hashCode()));
        }
    }

    /**
     * Method used by the {@link Player} to place {@link Resource} inside of {@link Warehouse}
     * If anything goes well the pendingResources {@link ArrayList} will be empty and the {@link Resource} which were inside of it will be stored inside {@link Warehouse}
     *
     * @param whereToPlace an {@link ArrayList} containing where to add (in which {@link Warehouse}) the iTH {@link Resource} of the pendingResources
     * @param player       the {@link Player} performing the action
     */
    public void positioningResourcesInteraction(ArrayList<Integer> whereToPlace, Player player, boolean noControl) {
        /*
            Check
            1) if are there any resources to be placed
            2) if the 2 arraylist sizes are equal
            3) if the warehouses can correctly store everything (simulating all the transferring process using .clone())
            If no errors ==>
                            4) empty the pendingResources
                            if some Resource got discarded ==> 5) move ahead other players on the Faith Path
         */
        if (!noControl) {
            //CHECK 1)
            if (pendingMarketResources.size() == 0) {
                askForMove();return;
            }
            //CHECK 2)
            else if ((pendingMarketResources.size() != whereToPlace.size())) {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters 3", player, players.indexOf(player), this.hashCode()));
                notify(MarketResponse.getInstance(pendingMarketResources, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player)), players.indexOf(player), -1, 0, this.hashCode()));
                return;
            }
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
                            && !player.addResourceToWarehouseStandard(pendingMarketResources.get(i),whereToPlace.get(i)))
                            || (whereToPlace.get(i) >= 3 && whereToPlace.get(i) < 5 && player.getWarehousesAdditional().size() > whereToPlace.get(i) - 3
                            && !player.addResourceToWarehouseAdditional(pendingMarketResources.get(i),whereToPlace.get(i)-3))
            ) {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters 4", player, players.indexOf(player), this.hashCode()));
                notify(MarketResponse.getInstance(pendingMarketResources, numOfWhiteMarbleToBeConverted, new ArrayList<>(Arrays.asList(player)), players.indexOf(player), -1, 0, this.hashCode()));
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters 41", player, players.indexOf(player), this.hashCode()));
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
        pendingMarketResources = new ArrayList<>();
        //START 5)
        if (numberOfDiscardedResources != 0) {
            for (Player p : players) {
                if (!p.equals(player)) {
                    p.moveAheadFaith(numberOfDiscardedResources);
                }
            }
            controlPopePath();
        }
        setCanChangeTurn(true, player);
        //notifyModel();
        if (!noControl) {
            notify(PositioningResourcesResponse.getInstance(numberOfDiscardedResources, numberOfGainedResources, players, players.indexOf(player), whereToPlace, this.hashCode()));
            askForMove();
        }
    }

    /**
     * Method used to perform the user's purchase of a {@link DevelopmentCard}
     *
     * @param type     the {@link DevelopmentCardType} which specifies which column of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param level    the {@link DevelopmentCardLevel} which specifies which row of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param player   the {@link Player} which call the Move
     * @param posToAdd the int that specify in which user's {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace} the new {@link DevelopmentCard} has to be placed into
     */
    public void buyDevelopmentCardInteraction(DevelopmentCardType type, DevelopmentCardLevel level, Player player, int posToAdd, ArrayList<ResourcePick> resourceToUse, boolean noControl) {
        //if the player can afford the development card requested
        ArrayList<ResourcesCount> resourcesCounts = resourceToUse.stream().map(elem -> ResourcesCount.getInstance(1, elem.getResourceType())).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Resource> resources = getDevelopmentCardOnTop(type, level).getCosts(player).stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toCollection(ArrayList::new));
        //check if the resourto use are the required and if the player has this resources
        if (Utils.compareResources(resources, resourcesCounts) && player.canAfford(resourceToUse) && player.developmentCardCanBeAdded(DevelopmentCard.getInstance(level, type), posToAdd)) {
            DevelopmentCard temp_card = pickDevelopmentCardOnTop(type, level);
            if (player.addDevelopmentCard(temp_card, posToAdd)) //no errors
            {
                setCanChangeTurn(true, player);
                notify(BuyDevelopmentCardReponse.getInstance(players, players.indexOf(player), type, level, posToAdd, resourceToUse, this.hashCode()));
            } else {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            }
        } else if (!player.canAfford(resourceToUse)) {
            notify(SendMessage.getInstance("Something wrong, Not enough resources", player, players.indexOf(player), this.hashCode()));
        } else {
            notify(SendMessage.getInstance("Something wrong, Cannot be added (parameter error)", player, players.indexOf(player), this.hashCode()));
        }
        askForMove();
    }


    public void activateProductivePower(ProductivePower power, ArrayList<ResourcePick> resourceToUse, Player player, boolean noControl) {
        //check if the resource to use are the same required for the leader card
        ArrayList<Resource> powerRequiredResources = power.getFrom().stream().flatMap(elem -> elem.toArrayListResources().stream()).collect(Collectors.toCollection(ArrayList::new)); //arraylist di resources
        ArrayList<ResourcesCount> resourcesCounts = resourceToUse.stream().map(elem -> ResourcesCount.getInstance(1, elem.getResourceType())).collect(Collectors.toCollection(ArrayList::new));
        //check if the resourto use are the required and if the player has this resources
        if (Utils.compareResources(powerRequiredResources, resourcesCounts) && player.canAfford(resourceToUse)) {
            player.moveAheadFaith((int) power.getTo().stream().filter(elem -> elem.getType().equals(ResourceType.FAITH)).count());
            controlPopePath();
            pendingProductionResources.addAll(power.getTo().stream().filter(elem -> !elem.getType().equals(ResourceType.FAITH)).collect(Collectors.toList()));
            productionActive=true;
            setCanChangeTurn(true, player);
            if (!noControl) {
                notify(EnableProductionResponse.getInstance(power, resourceToUse, players, players.indexOf(player), this.hashCode()));
                askForMove();
            }
        } else if (!noControl) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            askForMove();
        }
    }

    /**
     * Method used to perform the enable of the base interaction
     *
     * @param resourceToUse
     * @param toType
     * @param player
     */
    public void enableProductionBaseInteraction(ArrayList<ResourcePick> resourceToUse, ResourceType toType, Player player, boolean noControl) {
        ArrayList<ResourcesCount> from = new ArrayList<>();
        from.add(ResourcesCount.getInstance(1, resourceToUse.get(0).getResourceType()));
        from.add(ResourcesCount.getInstance(1, resourceToUse.get(1).getResourceType()));
        ArrayList<Resource> to = new ArrayList<>();
        to.add(Resource.getInstance(toType));
        //ACTIVATE THE POWER
        activateProductivePower(ProductivePower.getInstance(from, to), resourceToUse, player, noControl);
    }

    /**
     * Method used to perform the enable of the development interaction
     *
     * @param resourceToUse
     * @param positionOfDevelopmentCard
     * @param player
     */
    public void enableProductionDevelopmentInteraction(ArrayList<ResourcePick> resourceToUse, int positionOfDevelopmentCard, Player player, boolean noControl) {
        if (!noControl && positionOfDevelopmentCard >= player.getDevelopmentCardSpaces().size()) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            askForMove();
            return;
        }
        DevelopmentCard developmentCard = player.getDevelopmentCardSpaces().get(positionOfDevelopmentCard).pickTopCard();
        if (!noControl && developmentCard == null) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            askForMove();
            return;
        }
        activateProductivePower(developmentCard.getPowers(), resourceToUse, player, noControl);
    }

    /**
     * Method used to perform the enable of the production interaction
     *
     * @param resourceToUse
     * @param positionOfProductivePower
     * @param player
     */
    public void enableProductionLeaderInteraction(ArrayList<ResourcePick> resourceToUse, int positionOfProductivePower, ResourceType resourceType, Player player, boolean noControl) {
        if (!noControl && positionOfProductivePower >= player.getAddedPower().size() && positionOfProductivePower > 0) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            askForMove();
            return;
        }
        ProductivePower power = ProductivePower.getInstance(
                player.getAddedPower().get(positionOfProductivePower).getFrom(),
                new ArrayList<>(Arrays.asList(
                        Resource.getInstance(resourceType),
                        Resource.getInstance(ResourceType.FAITH)))
        );
        activateProductivePower(power, resourceToUse, player, noControl);
    }

    /**
     * Method used by the {@link Player} to swap {@link Resource} between two {@link Warehouse}
     *
     * @param indexFirstWarehouse  an int representing the first {@link Warehouse}
     * @param indexSecondWarehouse an int representing the second {@link Warehouse}
     * @param player               the {@link Player} that is performing the action
     * @throws MoveResourcesException the {@link MoveResourcesException} which is thrown if any error occur (not existing {@link Warehouse}, not enough available space ...)
     */
    public void MoveResourcesInteraction(int indexFirstWarehouse, int indexSecondWarehouse, int how_may_first,int how_many_second, Player player, boolean noControl) {
        if (indexFirstWarehouse == indexSecondWarehouse) {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            askForMove();
        }
        if (indexFirstWarehouse == 3 || indexSecondWarehouse == 3) //check if the first additional warehouse exists
        {
            if (player.getWarehousesAdditional().size() < 1) {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
                askForMove();
            }
        }
        if (indexFirstWarehouse == 4 || indexSecondWarehouse == 4) //check if the second additional warehouse exists
        {
            if (player.getWarehousesAdditional().size() < 2) {
                notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
                askForMove();
            }
        }
        if (player.moveResources(indexFirstWarehouse, indexSecondWarehouse, how_may_first,how_many_second)) {
            if (!noControl) {
                notify(MoveResourcesResponse.getInstance(players, players.indexOf(player), this.hashCode(), how_may_first, indexFirstWarehouse, indexSecondWarehouse));
                askForMove();
            }
            return;
        } else {
            notify(SendMessage.getInstance("Something wrong, Insert valid parameters", player, players.indexOf(player), this.hashCode()));
            askForMove();
        }
    }

    public void endRoundInteraction(Player player, boolean noControl) {
        player.getStrongBox().addAll(pendingProductionResources);
        pendingProductionResources = new ArrayList<>();
        productionActive=false;
    }

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

    //todo: testing
    public void controlPopePath() {
        // 5-8 (2 points, first pope favor tiles)
        // 12-16 (3 points, second pope favor tiles)
        // 19-24 (4 points, third pope favor tiles)
        //remove popeFavor put it = null;

        for (Player player : players) {
            if (player.getPosFaithMarker() == 24) {
                for (Player tempP : players) {
                    if (tempP.getPosFaithMarker() >= 19) {
                        if (tempP.getPopeFavorTiles().get(2) != null) {
                            tempP.getPopeFavorTiles().get(2).active();
                        }
                    } else {
                        tempP.getPopeFavorTiles().set(2, null);
                    }
                }
            } else if (player.getPosFaithMarker() >= 16) {
                for (Player tempP : players) {
                    if (tempP.getPosFaithMarker() >= 12) {
                        if (tempP.getPopeFavorTiles().get(1) != null) {
                            tempP.getPopeFavorTiles().get(1).active();
                        }
                    } else {
                        tempP.getPopeFavorTiles().set(1, null);
                    }
                }
            } else if (player.getPosFaithMarker() >= 8) {
                for (Player tempP : players) {
                    if (tempP.getPosFaithMarker() >= 5) {
                        if (tempP.getPopeFavorTiles().get(0) != null) {
                            tempP.getPopeFavorTiles().get(0).active();
                        }
                    } else {
                        tempP.getPopeFavorTiles().set(0, null);
                    }
                }
            }
        }
    }

    public ArrayList<Winner> whoIsWinner() {
        ArrayList<Winner> winners = new ArrayList<>();
        for (Player player : players) {
            int totalPoints = 0;
            //Adding the points from the development card
            totalPoints += player.getDevelopmentCards().stream().mapToInt(elem -> elem.getEquivalentPoint()).sum();
            //Adding the points from the Faith Track
            if (player.getPosFaithMarker() < 3) {
                totalPoints += 0;
            } else if (player.getPosFaithMarker() < 6) {
                totalPoints += 1;
            } else if (player.getPosFaithMarker() < 9) {
                totalPoints += 2;
            } else if (player.getPosFaithMarker() < 12) {
                totalPoints += 4;
            } else if (player.getPosFaithMarker() < 15) {
                totalPoints += 6;
            } else if (player.getPosFaithMarker() < 18) {
                totalPoints += 9;
            } else if (player.getPosFaithMarker() < 21) {
                totalPoints += 12;
            } else if (player.getPosFaithMarker() < 24) {
                totalPoints += 16;
            } else {
                totalPoints += 20;
            }
            //adding the point from the active Pope's Tales
            for (int j = 0; j < 3; j++) {
                if (player.getPopeFavorTiles().get(j) != null && player.getPopeFavorTiles().get(j).isActive())
                    totalPoints += player.getPopeFavorTiles().get(j).getPoints();
            }
            //adding the points from the LeaderCards that are activated
            totalPoints += player.getLeaderCards().stream().filter(elem -> elem.isActive()).mapToInt(elem -> elem.getPoints()).sum();
            //Counting all the Resources stored
            int countRes = player.getResources().size();
            totalPoints += Math.floorDiv(countRes, 5); // countRes/5;

            //creating the ArrayList of Winners
            if (winners.isEmpty()) {
                winners.add(Winner.getInstance(player.getName(), totalPoints, countRes));
            } else {
                if(winners.get(0).getPoints()< totalPoints){ //th winner is the considered player
                    winners.clear();
                    winners.add(Winner.getInstance(player.getName(), totalPoints, countRes));
                }else if(winners.get(0).getPoints()== totalPoints){
                    if(countRes == winners.get(0).getTotalResources()){ // draw
                        winners.add(Winner.getInstance(player.getName(), totalPoints, countRes));
                    }else if(countRes > winners.get(0).getTotalResources()) {//th winner is the considered player
                        winners.clear();
                        winners.add(Winner.getInstance(player.getName(), totalPoints, countRes));
                    }
                }
            }
        }
        return winners;
    }

    public String toString() {
        String temp = "";
        try {
            for (int i = 0; i < 30; i++) //Hint From Ing. Conti
            {
                temp += "\n";
            }
            temp += ("-------------------------------------------------------------------------------------------------------\n");
            temp += ("|MERCATO |\n");
            temp += ("---------\n");
            temp += ("       _\n");
            temp += ("      |" + marketBoard.getAdditionalMarble().toString().toCharArray()[0] + "|\n");
            temp += (" _ _ _ _\n");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {

                    if (j != 3) {
                        temp += ("|" + marketBoard.getRow(i).get(j).toString().toCharArray()[0]);
                    } else {
                        String code = "";
                        switch (i) {
                            case 0:
                                code = "W ▶ ?        B ▶ "+ResourceType.SHIELD.symbol;
                                break;
                            case 1:
                                code = "Y ▶ "+ResourceType.COIN.symbol+"        G ▶ "+ResourceType.STONE.symbol;
                                break;
                            case 2:
                                code = "P ▶ "+ResourceType.SERVANT.symbol+"        R ▶ "+ResourceType.FAITH.symbol;
                                break;
                        }
                        temp += ("|" + marketBoard.getRow(i).get(j).toString().toCharArray()[0] + "|  ◀  (" + i + ")           " + code + "\n");
                        temp += (" _ _ _ _\n");
                    }
                }
            }
            temp += (" ▲ ▲ ▲ ▲\n");
            temp += (" 0 1 2 3\n");
            temp += "\n";
            temp += ("-------------------------------------------------------------------------------------------------------\n");
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            temp += ("|DEVELOPMENT CARDS|            (P=Point, C=Costs, PP = Productive Powers)\n");
            temp += ("---------------\n");
            temp += ("\t\t\t\t\t\t  " + DevelopmentCardType.GREEN + " \t\t\t\t\t\t  " + DevelopmentCardType.BLUE + " \t\t\t\t\t\t " + DevelopmentCardType.YELLOW + " \t\t\t\t\t\t " + DevelopmentCardType.PURPLE + "\n");
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            int max = 30;
            for (int i = 0; i < 3; i++) {
                //controllare bene se la lista è vuota
                temp += ("        | P:| "
                        + developmentCards[i][0].peek().getEquivalentPoint() + Utils.fillSpaces(max, Integer.valueOf(developmentCards[i][0].peek().getEquivalentPoint()).toString().length()) + "|"
                        + developmentCards[i][1].peek().getEquivalentPoint() + Utils.fillSpaces(max, Integer.valueOf(developmentCards[i][1].peek().getEquivalentPoint()).toString().length()) + "|"
                        + developmentCards[i][2].peek().getEquivalentPoint() + Utils.fillSpaces(max, Integer.valueOf(developmentCards[i][2].peek().getEquivalentPoint()).toString().length()) + "|"
                        + developmentCards[i][3].peek().getEquivalentPoint() + Utils.fillSpaces(max, Integer.valueOf(developmentCards[i][3].peek().getEquivalentPoint()).toString().length()) + "|\n");
                temp += ("LVL = " + (i + 1) + " | C:| " + developmentCards[i][0].peek().getCostsFormatted() + Utils.fillSpaces(max, developmentCards[i][0].peek().getCostsFormatted().length()) + "|" + developmentCards[i][1].peek().getCostsFormatted() + Utils.fillSpaces(max, developmentCards[i][1].peek().getCostsFormatted().length()) + "|" + developmentCards[i][2].peek().getCostsFormatted() + Utils.fillSpaces(max, developmentCards[i][2].peek().getCostsFormatted().length()) + "|" + developmentCards[i][3].peek().getCostsFormatted() + Utils.fillSpaces(max, developmentCards[i][3].peek().getCostsFormatted().length()) + "|\n");
                temp += ("        |PP:| " + developmentCards[i][0].peek().getPowersFormatted() + Utils.fillSpaces(max, developmentCards[i][0].peek().getPowersFormatted().length()) + "|" + developmentCards[i][1].peek().getPowersFormatted() + Utils.fillSpaces(max, developmentCards[i][1].peek().getPowersFormatted().length()) + "|" + developmentCards[i][2].peek().getPowersFormatted() + Utils.fillSpaces(max, developmentCards[i][2].peek().getPowersFormatted().length()) + "|" + developmentCards[i][3].peek().getPowersFormatted() + Utils.fillSpaces(max, developmentCards[i][3].peek().getPowersFormatted().length()) + "|\n");
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            }
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            temp += "|How many left|\n";
            temp += "---------------\n";
            for(int i = 0;i<Utils.DEV_CARD_ROW_NUMBER;i++)
            {
                for(int j = 0;j<Utils.DEV_CARD_COL_NUMBER;j++)
                {
                    temp += "["+developmentCards[i][j].size()+"]\t";
                }
                temp +="\n";
            }
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            temp += "\n";
            for (Player player : getPlayers()) {
                temp += ("Player: " + player.getName() + "\n");
                if (player == null) {
                    break;
                }
                temp += ("Faith pos: " + player.getPosFaithMarker() +" " +ResourceType.FAITH.symbol+ "\n");
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                temp += ("|WAREHOUSE STD|\n");
                temp += ("---------------\n");
                temp += ("   #   |Dim|Space|Type|      Resources\n");
                temp += ("----------------------------------------------------------------------------------\n");
                for (int i = 0; i < player.getWarehousesStandard().size(); i++) {
                    temp += ("  (" + i + ")  |"+"("+ (i+1)+")|" + player.getWarehousesStandard().get(i).toString() + "\n");
                }
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                temp += ("|WAREHOUSE ADDITIONAL|\n");
                temp += ("----------------------\n");
                temp += ("   #   |Dim|Space|Type|      Resources\n");
                for (int i = 0; i < player.getWarehousesAdditional().size(); i++) {
                    temp += ("  (" + (i+3) + ")  |"+" "+ (i+1)+" |" + player.getWarehousesAdditional().get(i).toString() + "\n");
                }
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                temp += ("|STRONGBOX|\n");
                temp += ("-----------\n");
                temp += (Utils.formatResourcesCount(Utils.fromResourcesToResourceCount(player.getStrongBox())) + "\n");
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                temp += ("|LEADER CARD PRODUCTIVE POWERS|\n");
                temp += ("-------------------------------\n");
                for(int i = 0;i<player.getAddedPower().size();i++)
                {
                    temp += temp + " ("+i+")  "+player.getAddedPower().get(i).toString()+"\n";
                }
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                temp += ("|CARD SPACES|\n");
                temp += ("-------------\n");
                temp += ("|CARDS ON TOP|\n");
                temp += ("--------------\n");
                temp += ("|INDEX| Type |  Level  | Points |   Costs   |   Productive Powers\n");
                String card_spaces = "";
                for (int a = 0; a < player.getDevelopmentCardSpaces().size(); a++) {
                    temp+="  ("+a+")   ";
                    if(player.getDevelopmentCardSpaces().get(a).pickTopCard()!=null)
                    {
                        DevelopmentCard d = player.getDevelopmentCardSpaces().get(a).pickTopCard();
                        temp += (d.getType()+"|"+d.getLevel()+"|  "+d.getEquivalentPoint()+"  | "
                                +d.getCostsFormatted()+Utils.fillSpaces(30,d.getCostsFormatted().length()))
                                +"| " + d.getPowersFormatted()+"\n";
                    }
                    else
                    {
                        temp += "  Empty\n";
                    }
                }
                temp += ("-----------\n");
                temp += ("|ALL CARDS|\n");
                temp += ("-----------\n");
                int pos=0;
                for (DevelopmentCardSpace dev_space:player.getDevelopmentCardSpaces()) {
                    temp += "("+pos+") ==> [";
                    for (DevelopmentCard dev_card: dev_space.linearize()) {
                        temp += dev_card.getType() + ", " + dev_card.getLevel() + " / ";
                    }
                    pos++;
                    temp += "]\n";
                }
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                temp += ("|LEADER CARDS|\n");
                temp += ("-------------\n");
                temp += ("Ind |Active|Point|Type|         Res needed        |      DevCardNeeded         |    Powers\n");
                int uo = 0;
                for (LeaderCard leaderCard : player.getLeaderCards()) {
                    String str_leader="";
                    if(!leaderCard.isActive()&&(whoAmI!=players.indexOf(player))) //if the leader card is not active and it's not "mine", I can't see it
                    {
                        str_leader = "\uD83D\uDEAB LeaderCard not activated yet \uD83D\uDEAB";
                    }
                    else
                    {
                        str_leader = leaderCard.toString();
                    }
                    temp += ("(" + uo + ") |" + str_leader + "\n");
                    uo++;
                }
                temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                if(!pendingProductionResources.isEmpty())
                {
                    temp += ("|RESOURCES GOT FROM PRODUCTION|   ([?]       Resources obtained during this turn from the production        [?]) ");
                    temp += ("                                  ([?] they are frozen, at the end of the round they'll go to the strongbox [?]) ");
                    temp += (Utils.formatResourcesCount(Utils.fromResourcesToResourceCount(pendingProductionResources)));
                    temp += ("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public int hashCode() {
        //System.err.println((new Gson()).toJson(this));
        return (new Gson()).toJson(this).hashCode();
    }

    /**
     * Method used to retrieve the {@link Player} which is playing
     *
     * @return the {@link Player} which is playing
     */
    public abstract Player getCurrentPlayer();

    //needed for match solo
    public String executeAction(ActionToken action, Player player, boolean noControl){
        return "";
    }
}
