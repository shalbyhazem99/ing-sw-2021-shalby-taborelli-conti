package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardReponse;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.market.MarketResponse;
import it.polimi.ingsw.controller.move.production.EnableProductionResponse;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesResponse;
import it.polimi.ingsw.controller.move.response.IllegalMoveResponse;
import it.polimi.ingsw.controller.move.settings.SendModel;
import it.polimi.ingsw.exceptions.DevelopmentSpaceException;
import it.polimi.ingsw.exceptions.NotEnoughResourcesException;
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
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract class containing the attributes and the methods shared by MatchSolo and MatchMulti
 */
public abstract class Match extends Observable<MoveResponse> implements Serializable {
    protected ArrayList<Player> players;
    protected Stack<LeaderCard> leaderCards;
    protected Stack<DevelopmentCard>[][] developmentCards;
    protected MarketBoard marketBoard;
    protected ArrayList<Resource> pendingResources;
    //TODO: pensare a attributi aggiuntivi, esempio salvare su disco i record, memorizzare timestamp per sapere da quanto tempo si gioca ...

    /**
     * Constructor that intitialize the Match settings reading from a file all the information
     */
    //TODO: leggere da file
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
        notify(SendModel.getInstance(this));
    }


    /**
     * Method to get a shallow copy of the {@link Player}'s {@link ArrayList}
     *
     * @return {@link Player} clone
     */
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players.clone();
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

    public void nextRound() {
        //TODO: go ahead in the round
        //TODO: serialize
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

    public void discardLeaderCard(LeaderCard leaderCard) {
    }

    public void enableLeaderCard(LeaderCard leaderCard) throws NotEnoughResourcesException {
    }

    public void enableProductionMove(ArrayList<ProductivePower> productivePowers) throws NotEnoughResourcesException {
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
        ArrayList<Resource> resourcesGained = marketBoard.getResources(moveType, pos);
        int numOfWhiteMarbleToBeConverted; //number of white marbles to be converted
        if (moveType.equals(MoveType.COLONNA)) {
            numOfWhiteMarbleToBeConverted = Utils.MARKET_COL_NUMBER - resourcesGained.size();   //how many white marbles are there in the selected COLUMN
        } else {
            numOfWhiteMarbleToBeConverted = Utils.MARKET_ROW_NUMBER - resourcesGained.size(); //how many white marbles are there in the selected ROW
        }
        /*
                If there are any white marbles selected and if the user which is requesting the marketInteraction
                has an additional strategy to convert white marbles we need to know if he wants to enable it,
                otherwise white marbles won't be converted
         */
        //TODO: dovrei fare un .size
        if (!player.hasWhiteMurbleConvertionStrategy() || numOfWhiteMarbleToBeConverted == 0) {
            numOfWhiteMarbleToBeConverted = 0; //we don't need to convert anything (we have no white marbles or we don't have additional convertion strategies
        }
        resourcesGained.stream().map(el -> pendingResources.add(el)); //add the Resources to the Box containing the resources waiting to be placed
        notify(MarketResponse.getInstance(resourcesGained, numOfWhiteMarbleToBeConverted));
    }

    /**
     * Method used to perform the conversion of the white marbles, the {@link Resource} gained are placed into pendingResources {@link ArrayList}
     *
     * @param conversionStrategyList the {@link ArrayList} of {@link ResourceType} which represent in which type of {@link Resource} convert each white {@link it.polimi.ingsw.model.market.Marble}
     */
    public void marketMarbleConvertInteraction(ArrayList<ResourceType> conversionStrategyList) {
        ArrayList<Resource> resourcesGained = (ArrayList<Resource>) conversionStrategyList.stream().map(el -> Resource.getInstance(el)).collect(Collectors.toList());
        resourcesGained.stream().map(el -> pendingResources.add(el)); //add the resources to the box conitaning the resources waiting to be placed
        notify(MarketResponse.getInstance(resourcesGained, 0));
    }

    /**
     * Method used to perform the user's purchase of a {@link DevelopmentCard}
     *
     * @param type     the {@link DevelopmentCardType} which specifies which column of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param level    the {@link DevelopmentCardLevel} which specifies which row of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param player   the {@link Player} which call the Move
     * @param posToAdd the int that specify in which user's {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace} the new {@link DevelopmentCard} has to be placed into
     */
    public void buyDevelopmentCardInteraction(DevelopmentCardType type, DevelopmentCardLevel level, Player player, int posToAdd) {
        //if the player can afford the development card requested
        if (player.canAfford(new ArrayList<DevelopmentCard>() {{
            add(getDevelopmentCardOnTop(type, level));
        }}) && player.developmentCardCanBeAdded(DevelopmentCard.getInstance(level, type), posToAdd)) {
            DevelopmentCard temp_card = pickDevelopmentCardOnTop(type, level);
            if (player.addDevelopmentCard(temp_card, posToAdd)) //no errors
            {
                notify(BuyDevelopmentCardReponse.getInstance(temp_card));
            } else {
                notify(IllegalMoveResponse.getInstance((new DevelopmentSpaceException()).getMessage()));
            }
        } else if (!player.canAfford(new ArrayList<DevelopmentCard>() {{
            add(getDevelopmentCardOnTop(type, level));
        }})) {
            notify(IllegalMoveResponse.getInstance((new NotEnoughResourcesException()).getMessage()));
        }
    }

    /**
     * Method used to perform the enable of the interaction
     *
     * @param productivePowers                {@link ArrayList} containing the default {@link ProductivePower} enabled
     * @param devCardProductivePlayerSelected {@link ArrayList} containing {@link Integer} representing which {@link DevelopmentCard} 's {@link ProductivePower} is enabled
     * @param p                               {@link Player} performing the {@link it.polimi.ingsw.controller.move.PlayerMove}
     * @throws NotEnoughResourcesException {@link NotEnoughResourcesException} thrown if the Productions can't be enabled
     */
    public void enableProductionInteraction(ArrayList<ProductivePower> productivePowers, ArrayList<Integer> devCardProductivePlayerSelected, Player p) throws NotEnoughResourcesException {
        //Player p = getPlayers().get(getPlayers().indexOf(Player.getInstance(name_of_user))); //todo: possiamo anche rimuoverlo
        if (devCardProductivePlayerSelected != null) {
            //list containing the merging of the card productive powers and the "default" productive powers
            productivePowers = (ArrayList<ProductivePower>)
                    Stream.concat(
                            devCardProductivePlayerSelected.stream()
                                    .map(elem -> p.getDevelopmentCards().get(elem).getPowers()),
                            productivePowers.stream())
                            .collect(Collectors.toList());
        }
        //productivePowers contains all the ProductivePower that has to be enabled
        if (p.canEnableProductivePowers(productivePowers)) {
            //the Player has enough resources to enable all the ProductivePowers
            //potere produttivo->resourcecount->resource
            ArrayList<Resource> res = (ArrayList<Resource>) productivePowers.stream().
                    flatMap(el -> el.getFrom().stream()).
                    flatMap(el -> el.toArrayListResources().stream()).
                    collect(Collectors.toList());
            p.removeResources(res);
            //add resources to strongbox
            ArrayList<Resource> resGot = (ArrayList<Resource>) productivePowers.stream()
                    .flatMap(el -> el.getTo().stream())
                    .collect(Collectors.toList());
            resGot.stream().map(el -> p.getStrongBox().add(el)); //add to strongbox
            notify(EnableProductionResponse.getInstance(resGot));
        } else {
            throw new NotEnoughResourcesException();
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
                            4) add resources to warehouse
                            5) empty the pendingResources
                            if some Resource got discarded ==> 6) move ahead other players on the Faith Path
         */
        //CHECK 1)
        if (pendingResources.size() == 0) {
            throw new Exception("No Resources to be placed");
        }
        //CHECK 2)
        else if ((pendingResources.size() != whereToPlace.size())) {
            throw new Exception("Error with the positioning array");
        }
        //CHECK 3)
        ArrayList<Warehouse> standardTemp = player.getWarehousesStandard(); //TODO: CONTROLLARE CHE NON INCASINI IL MODEL IMPATTANDO DIRETTAMENTE (CON CLONE NON DOVREBBE SUCCEDERE)
        ArrayList<Warehouse> additionalTemp = player.getWarehousesAdditional();
        for (int i = 0; i < whereToPlace.size(); i++) {
            if (whereToPlace.get(i) == null) {
                //the resource will be discarded
            } else if (whereToPlace.get(i) >= 0 && whereToPlace.get(i) < 3) {
                if (!standardTemp.get(whereToPlace.get(i)).addResource((Resource) pendingResources.get(i).clone())) //if any error happens -> notify error
                {
                    throw new Exception("error in adding res #" + i + "to warehouse #" + whereToPlace.get(i));
                }
            } else {
                if (!additionalTemp.get(whereToPlace.get(i)).addResource((Resource) pendingResources.get(i).clone())) //if any error happens -> notify error
                {
                    throw new Exception("error in adding res #" + i + "to warehouse #" + whereToPlace.get(i));
                }
            }
        }
        //no errors --> we can correctly perform all the actions
        //START 4)
        int numberOfDiscardedResources = 0;
        int numberOfGainedResources = whereToPlace.size();
        for (int i = 0; i < whereToPlace.size(); i++) {
            if (whereToPlace.get(i) == null) {
                //the resource will be discarded
                numberOfDiscardedResources++;
                numberOfGainedResources--;
            } else if (whereToPlace.get(i) >= 0 && whereToPlace.get(i) < 3) {
                player.getWarehousesStandard().get(whereToPlace.get(i)).addResource(pendingResources.get(i));
            } else {
                player.getWarehousesAdditional().get(whereToPlace.get(i)).addResource(pendingResources.get(i));
            }
        }
        //START 5)
        pendingResources = new ArrayList<>();
        //START 6)
        if (numberOfDiscardedResources != 0) {
            for (Player p : players) {
                if (!p.equals(player)) {
                    p.moveAheadFaith(numberOfDiscardedResources);
                }
            }
        }
        notify(PositioningResourcesResponse.getInstance(numberOfDiscardedResources, numberOfGainedResources));
    }

    public void updateTurn() {
        /*
            Controllare:
            - è stata eseguita una mossa "sbloccante" (mercato, dev card, produzioni)
            - l'arraylist<Resource> pendingResources è vuoto
         */
    }
}
