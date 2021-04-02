package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardReponse;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.market.MarketResponse;
import it.polimi.ingsw.controller.move.production.EnableProductionResponse;
import it.polimi.ingsw.exceptions.NotEnoughResources;
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
    protected ArrayList<LeaderCard> leaderCards;
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
        if(players.indexOf(newPlayer)>=0){
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
     * @param type {@link DevelopmentCardType} can be GREEN, BLUE, YELLOW,PURPLE
     * @param level {@link DevelopmentCardLevel} can be 1,2,3
     * @return The top {@link DevelopmentCard} of the {@link Stack}
     */
    public DevelopmentCard getDevelopmentCardOnTop(DevelopmentCardType type, DevelopmentCardLevel level)
    {
        return developmentCards[level.label][type.label].peek();
    }

    /**
     * Method to pick (get and remove) the top {@link DevelopmentCard} of a {@link Stack} (combination of {@link DevelopmentCardLevel} and {@link DevelopmentCardType})
     * @param type {@link DevelopmentCardType} can be GREEN, BLUE, YELLOW,PURPLE
     * @param level {@link DevelopmentCardLevel} can be 1,2,3
     * @return The top {@link DevelopmentCard} of the {@link Stack}
     */
    public DevelopmentCard pickDevelopmentCardOnTop(DevelopmentCardType type, DevelopmentCardLevel level)
    {
        return developmentCards[level.label][type.label].pop();
    }

    public void discardLeaderCard(LeaderCard leaderCard) {
    }

    public void enableLeaderCard(LeaderCard leaderCard) throws NotEnoughResources {
    }

    public void enableProductionMove(ArrayList<ProductivePower> productivePowers) throws NotEnoughResources {
    }

    public void marketInteraction(MoveType moveType, int pos, String name_of_user) {
        ArrayList<Resource> resourcesGained = marketBoard.getResources(moveType, pos);
        int numOfWhiteMarbleToBeConverted; //number of white marbles to be converted
         if (moveType.equals(MoveType.COLONNA))
         {
             numOfWhiteMarbleToBeConverted = Utils.COLUMNNUMBER - resourcesGained.size();   //how many white marbles are there in the selected COLUMN
         }
        else
        {
            numOfWhiteMarbleToBeConverted = Utils.ROWNUMBER - resourcesGained.size(); //how many white marbles are there in the selected ROW
        }
        /*
                If there are any white marbles selected and if the user which is requesting the marketInteraction
                has an additional strategy to convert white marbles we need to know if he wants to enable it,
                otherwise white marbles won't be converted
         */
        //TODO: dovrei fare un .size
        if(!getPlayers().get(getPlayers().indexOf(Player.getInstance(name_of_user))).hasWhiteMurbleConvertionStrategy()||numOfWhiteMarbleToBeConverted==0)
        {
            numOfWhiteMarbleToBeConverted = 0; //we don't need to convert anything (we have no white marbles or we don't have additional convertion strategies
        }
        resourcesGained.stream().map(el->pendingResources.add(el)); //add the Resources to the Box containing the resources waiting to be placed
        notify(MarketResponse.getInstance(resourcesGained, numOfWhiteMarbleToBeConverted));
    }

    /**
     * Method used to perform the conversion of the white marbles
     * @param conversionStrategyList the {@link ArrayList} of {@link ResourceType} which represent in which type of {@link Resource} convert each white {@link it.polimi.ingsw.model.market.Marble}
     */
    public void marketMarbleConvertInteraction(ArrayList<ResourceType> conversionStrategyList)
    {
        ArrayList<Resource> resourcesGained = (ArrayList<Resource>) conversionStrategyList.stream().map(el->Resource.getInstance(el)).collect(Collectors.toList());
        resourcesGained.stream().map(el->pendingResources.add(el)); //add the resources to the box conitaning the resources waiting to be placed
        notify(MarketResponse.getInstance(resourcesGained, 0));
    }

    /**
     * Method used to perform the user's purchase of a {@link DevelopmentCard}
     * @param type the {@link DevelopmentCardType} which specifies which column of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param level the {@link DevelopmentCardLevel} which specifies which row of the {@link DevelopmentCard} {@link Stack} needs to be selected
     * @param name_of_user the {@link String} which lets the system to retrieve {@link Player} object
     * @param posToAdd the int that specify in which user's {@link it.polimi.ingsw.model.developmentCard.DevelopmentCardSpace} the new {@link DevelopmentCard} has to be placed into
     * @throws NotEnoughResources the {@link NotEnoughResources} exception is thrown if the user cannot afford the {@link DevelopmentCard} passed as parameter
     */
    public void buyDevelopmentCardInteraction(DevelopmentCardType type, DevelopmentCardLevel level, String name_of_user, int posToAdd) throws NotEnoughResources {
        Player p = getPlayers().get(getPlayers().indexOf(Player.getInstance(name_of_user)));
        //if the player can afford the development card requested
        if(p.canAfford(new ArrayList<DevelopmentCard>(){{add(getDevelopmentCardOnTop(type,level));}})&&p.developmentCardCanBeAdded(DevelopmentCard.getInstance(level,type),posToAdd))
        {
            DevelopmentCard temp_card = pickDevelopmentCardOnTop(type, level);
            if(p.addDevelopmentCard(temp_card,posToAdd)) //no errors
            {
                notify(BuyDevelopmentCardReponse.getInstance(temp_card));
            }
            else
            {
                //if any error occurs an exception is thrown
                throw new NotEnoughResources();
            }
        }
        else if(!p.canAfford(new ArrayList<DevelopmentCard>(){{add(getDevelopmentCardOnTop(type,level));}}))
        {
            //if the player cannot afford the card an exception is thrown
            throw new NotEnoughResources();
        }
    }

    /**
     * Method used to perform the enable of the interaction
     * @param productivePowers {@link ArrayList} containing the default {@link ProductivePower} enabled
     * @param devCardProductivePlayerSelected {@link ArrayList} containing {@link Integer} representing which {@link DevelopmentCard} 's {@link ProductivePower} is enabled
     * @param name_of_user {@link String} used to retrieve the {@link Player}
     * @throws NotEnoughResources {@link NotEnoughResources} thrown if the Productions can't be enabled
     */
    public void enableProductionInteraction(ArrayList<ProductivePower> productivePowers, ArrayList<Integer> devCardProductivePlayerSelected,String name_of_user) throws NotEnoughResources
    {
        Player p = getPlayers().get(getPlayers().indexOf(Player.getInstance(name_of_user)));
        if(devCardProductivePlayerSelected!=null)
        {
            //list containing the merging of the card productive powers and the "default" productive powers
            productivePowers = (ArrayList<ProductivePower>)
                    Stream.concat(
                    devCardProductivePlayerSelected.stream()
                    .map(elem->p.getDevelopmentCards().get(elem).getPowers()),
                    productivePowers.stream())
            .collect(Collectors.toList());
        }
        //productivePowers contains all the ProductivePower that has to be enabled
        if(p.canEnableProductivePowers(productivePowers))
        {
            //the Player has enough resources to enable all the ProductivePowers
            //togliere risorse a tizio e dargliene di nuove
            //potere produttivo->resourcecount->resource
            ArrayList<Resource> res=(ArrayList<Resource>)productivePowers.stream().
                    flatMap(el->el.getFrom().stream()).
                    flatMap(el->el.toArrayListResources().stream()).
                    collect(Collectors.toList());
            p.removeResources(res);
            //add resources to strongbox
            ArrayList<Resource> resGot = (ArrayList<Resource>)productivePowers.stream()
                    .flatMap(el->el.getTo().stream()).collect(Collectors.toList());
            resGot.stream().map(el-> p.getStrongBox().add(el));
            //bisogna fare il notify
            notify(EnableProductionResponse.getInstance(resGot));
        }
        else
        {
            throw new NotEnoughResources();
        }
    }

    public void updateTurn() {
        /*
            Controllare:
            - è stata eseguita una mossa "sbloccante" (mercato, dev card, produzioni)
            - l'arraylist<Resource> pendingResources è vuoto
         */
    }
}
