package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.response.MoveResponse;
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

/**
 * Abstract class containing the attributes and the methods shared by MatchSolo and MatchMulti
 */
public abstract class Match extends Observable<MoveResponse> implements Serializable {
    protected ArrayList<Player> players;
    protected ArrayList<LeaderCard> leaderCards;
    protected Stack<DevelopmentCard>[][] developmentCards;
    protected MarketBoard marketBoard;
    //TODO: pensare a attributi aggiuntivi, esempio salvare su disco i record, memorizzare timestamp per sapere da quanto tempo si gioca ...
    /**
     * Constructor that intitialize the Match settings reading from a file all the information
     */
    //TODO: leggere da file
    public Match(int numberOfPlayers)
    {
        players = new ArrayList<>(numberOfPlayers);
        for(int i = 0;i<numberOfPlayers;i++) //to understand watch the addPLayer method
        {
            players.add(null);
        }
        leaderCards = FileReader.readLeaderCard();
        developmentCards = FileReader.readDevelopmentCards();
        marketBoard = MarketBoard.getInstance();
    }

    /**
     * Method to get a shallow copy of the {@link Player}'s {@link ArrayList}
     * @return {@link Player} clone
     */
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players.clone();
    }

    /**
     * Method to get a shallow copy of the {@link LeaderCard}'s {@link ArrayList}
     * @return {@link LeaderCard} clone
     */
    public ArrayList<LeaderCard> getLeaderCards() {
        return (ArrayList<LeaderCard>) leaderCards.clone();
    }

    /**
     * Method to get a shallow copy of the {@link DevelopmentCard} section
     * @return {@link DevelopmentCard} clone
     */
    public Stack<DevelopmentCard>[][] getDevelopmentCards() {
        return (Stack<DevelopmentCard>[][]) developmentCards.clone();
    }

    /**
     * Method to get the marketboard
     * @return {@link MarketBoard} clone, pay attention can be null
     */
    public MarketBoard getMarketBoard() {
        return marketBoard;
    }

    /**
     * Method used to add a player in the match, if the match is full the method will return false
     * @param newPlayer the {@link Player} to be added to the {@link java.util.ArrayList}
     * @return true if the {@link Player} is correctly added, false if the {@link java.util.ArrayList} is already full or some error occur
     */
    public boolean addPlayer(Player newPlayer)
    {
        /*
            Frequency method will return how many not initialized players are there in the ArrayList, so how many players we've to wait for
            if frequency == 0 we've already initialized all the needed players
         */
        if(newPlayer==null)
        {
            return false;
        }
        if((Collections.frequency(getPlayers(),null))==0)
        {
            return false;
        }
        return players.set(getPlayers().indexOf(null),newPlayer)!=null;
    }
    //TODO pensare a come ridefinire l'equals, possiamo mettere un nickname che deve essere univoco, quindi dovrei fare un ulteriore controllo in addplayer
    //TODO noi scegliamo di fare la persistenza, se sì l'utente va eliminato dopo x turni che non accede?
    public boolean removeDisconnectedPlayer(Player toRemove){
        return false;
    }

    /**
     * Method used to serialize the {@link Match} object
     * @param location the path in which the {@link Match} has to be serialized
     * @return true if the {@link Match} is correctly serialized to the file system, false otherwise
     */
    public boolean writeToFileSystem(String location)
    {
        if(location==null){ return false;}
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

    public void nextRound(){
        //TODO: go ahead in the round
        //TODO: serialize
    }

    public void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level) throws NotEnoughResources {
        //todo: success of failure response notify();
    }

    public void discardLeaderCard(LeaderCard leaderCard) {
    }

    public void enableLeaderCard(LeaderCard leaderCard) throws NotEnoughResources {
    }

    public void enableProductionMove(ArrayList<ProductivePower> productivePowers) throws NotEnoughResources {
    }

    public void marketInteraction(MoveType moveType, int pos) {
    }

    public void updateTurn(){

    }
}
