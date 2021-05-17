package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.settings.AskForMove;
import it.polimi.ingsw.exceptions.EndRoundException;
import it.polimi.ingsw.exceptions.SwapWarehouseException;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

import java.io.Serializable;
import java.util.*;


public class MatchSolo extends Match implements Serializable {
    /**
     * Class concerning a Match of one player vs CPU "Lorenzo il Magnifico"
     */

    /*
    Ad ogni turno l'utente fa un pickActionToken(), poi sulla base del tipo dell'action token controller chiama
    - moveAheadBlackCross(2)
    - moveAheadBlackCross(1) e shuffle()
    - discardDevelopmentCards(numero,tipo)
     */
    private LinkedList<ActionToken> actionTokens;
    private int posBlackCross;

    /**
     * Class' constructor, it calls the super method and in addition in initialize the {@link ActionToken} {@link java.util.ArrayList} and the posBlackCross
     */
    public MatchSolo() {
        super(1);
        posBlackCross = 0;
        actionTokens = generateActionTokens();
    }

    /**
     * The method generate the {@link ArrayDeque} of {@link ActionToken} required for a solo match
     *
     * @return {@link ArrayDeque} of {@link ActionToken} required for a solo match
     */
    public static LinkedList<ActionToken> generateActionTokens() {
        LinkedList<ActionToken> temp = new LinkedList<>();
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.GREEN));
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.BLUE));
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.YELLOW));
        temp.add(ActionToken.getInstance(MarkerType.DISCARD, 2, DevelopmentCardType.PURPLE));
        temp.add(ActionToken.getInstance(MarkerType.MOVE, 2, null));
        temp.add(ActionToken.getInstance(MarkerType.MOVE, 1, null));
        Collections.shuffle(temp);
        return temp;
    }

    /**
     * The method pick the {@link ActionToken} (which will be re-inserted in the tail of the queue)
     *
     * @return the {@link ActionToken} polled
     */
    public ActionToken pickActionToken() {
        ActionToken temp = actionTokens.poll();
        if (temp == null) //an error occured
        {
            actionTokens = generateActionTokens(); //rebuild the queue
            return null;
        }
        actionTokens.add(temp);
        return temp;
    }

    /**
     * The method move the black cross ahead in the Faith's path
     *
     * @param numberOfPasses how many cells the black cross needs to go ahead, numberOfPasses = {1,2}
     * @return the position of the blackCross, position € {0,1,2,...,20}
     */
    public int moveAheadBlackCross(int numberOfPasses) {
        posBlackCross = posBlackCross + numberOfPasses;
        if (posBlackCross > 20) {
            posBlackCross = 20;
        }
        return posBlackCross;
    }

    /**
     * Method used to retrieve the black cross' position
     *
     * @return the position of the black cross
     */
    public int getPosBlackCross() {
        return posBlackCross;
    }

    /**
     * The method shuffles the action tokens list
     */
    public void shuffleActionTokens() {
        Collections.shuffle(actionTokens);
    }

    /**
     * This method will discard from the {@link DevelopmentCard} matrix the requested amount of cards of a certain {@link DevelopmentCardType}
     * the method will try to discard LVL 1 cards, otherwise LVL 2 cards otherwise LVL 3 cards
     * @param numberOfCardsToDiscard how many card we have to discard
     * @param cardType which {@link DevelopmentCardType} we have to focus on
     * @return the {@link ArrayList} of {@link DevelopmentCard} that got discarded (it can be empty if no compatible cards are found
     */
    public ArrayList<DevelopmentCard> discardDevelopmentCards(int numberOfCardsToDiscard, DevelopmentCardType cardType)
    {
        /*
            COLUMNS:        GREEN / BLUE / YELLOW / PURPLE
            ROWS: LVL 3->
                  LVL 2->
                  LVL 1->
         */
        ArrayList<DevelopmentCard> temp = new ArrayList<>();
        DevelopmentCardLevel cardLevel = DevelopmentCardLevel.FIRST;
        do
        {
            if(developmentCards[cardLevel.label][cardType.label].size()!=0) //if the stack is empty try the next level otherwise discard
            {
                temp.add(developmentCards[cardLevel.label][cardType.label].pop());
                numberOfCardsToDiscard--;
            }
            else //if the stack we're operating on is empty we try to move a stack of an higher level
            {
                switch(cardLevel.label)
                {
                    case 0 : { cardLevel = null; break;} //it means we are int highest row on the third level, we cannot discard anything else
                    case 1 : { cardLevel = DevelopmentCardLevel.THIRD; break;} //LVL 2 --> LVL 3
                    case 2 : { cardLevel = DevelopmentCardLevel.SECOND; break;} //LVL 1 --> LVL 2
                }
            }
        } while(numberOfCardsToDiscard!=0 && cardLevel!=null); //stop if the correct amount of cards is discarded or if there are no more cards of that color left
        return temp;
    }

    @Override
    public boolean isMyTurn(Player player) {
        return true;
    }

    @Override
    public void setCanChangeTurn(boolean canChangeTurn, Player player) {
        this.canChangeTurn = canChangeTurn;
    }

    /**
     * Method used by the {@link Player} to end a {@link MatchSolo} round
     * @param player {@link Player} who wants to end the round
     * @throws EndRoundException {@link EndRoundException} thrown when an error occurs
     */
    public void endRoundInteraction(Player player)
    {
        setCanChangeTurn(false,player);
        pendingResources = new ArrayList<>();
        notify(EndRoundResponse.getInstance(getPlayers(),true));
        //ANDRA' ESEGUITA LA MOSSA DI LORENZO IL MAGNIFICO
        ActionToken action = pickActionToken();
        System.out.println("lunghezza " + Arrays.stream(developmentCards).count());
        switch (action.getAction())
        {
            case MOVE:
                if(action.getCount()==1)
                {
                    moveAheadBlackCross(1);
                    shuffleActionTokens();
                }
                else //count=2
                {
                    moveAheadBlackCross(2);
                }
                System.out.println("FEDE =  "+ posBlackCross);
                break;
            case DISCARD:
                int lvl = 0;
                int to_discard = action.getCount();
                while (to_discard!=0&&lvl!=3) //I have to discard x cards
                {
                    if(!developmentCards[lvl][action.getCardToReject().label].isEmpty()) //If the stack is not empty
                    {
                        developmentCards[lvl][action.getCardToReject().label].pop(); //discard the card
                        to_discard--;
                    }
                    else //if the stack is empty change the level
                    {
                        lvl++;
                    }
                }
                break;
        }
        askForMove();
    }

    @Override
    public Player getCurrentPlayer() {
        return players.get(0);
    }

    @Override
    public void startMatch() {
        super.startMatch();
        askForMove();
    }
    private void askForMove(){
        notifyModel();
        ArrayList<MovePlayerType> possibleMove = new ArrayList<>();
        if (!canChangeTurn) {
            possibleMove.add(MovePlayerType.MARKET_INTERACTION);
            possibleMove.add(MovePlayerType.BUY_DEVELOPMENT_CARD);
            possibleMove.add(MovePlayerType.ENABLE_PRODUCTION); //TODO: to separate because multiple production could be activated
        }
        possibleMove.add(MovePlayerType.ENABLE_LEADER_CARD);
        possibleMove.add(MovePlayerType.DISCARD_LEADER_CARD);
        possibleMove.add(MovePlayerType.SWAP_WAREHOUSE);
        possibleMove.add(MovePlayerType.END_TURN);
        notify(AskForMove.getInstance(new ArrayList<>(Arrays.asList(players.get(0))), possibleMove));
    }


    @Override
    public void discardLeaderCardInteraction(int leaderCardPosition,Player player) {
        super.discardLeaderCardInteraction(leaderCardPosition,player);
        askForMove();
    }

    @Override
    public void enableLeaderCardInteraction(int leaderCardPosition, Player player) {
        super.enableLeaderCardInteraction(leaderCardPosition, player);
        askForMove();
    }

    @Override
    public void positioningResourcesInteraction(ArrayList<Integer> whereToPlace, Player player) throws Exception {
        super.positioningResourcesInteraction(whereToPlace, player);
        askForMove();
    }

    @Override
    public void swapWarehouseInteraction(int indexFirstWarehouse, int indexSecondWarehouse, Player player) throws SwapWarehouseException {
        super.swapWarehouseInteraction(indexFirstWarehouse, indexSecondWarehouse, player);
        askForMove();
    }

    @Override
    public void enableProductionBaseInteraction(ArrayList<ResourcePick> resourceToUse, ResourceType to, Player player) {
        super.enableProductionBaseInteraction(resourceToUse, to, player);
        askForMove();
    }

    @Override
    public void enableProductionDevelopmentInteraction(ArrayList<ResourcePick> resourceToUse, int positionOfDevelopmentCard, Player player) {
        super.enableProductionDevelopmentInteraction(resourceToUse, positionOfDevelopmentCard, player);
        askForMove();
    }

    @Override
    public void enableProductionLeaderInteraction(ArrayList<ResourcePick> resourceToUse, int positionOfProductivePower, Player player) {
        super.enableProductionLeaderInteraction(resourceToUse, positionOfProductivePower, player);
        askForMove();
    }

    public String toString() {
        try {
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
            System.out.println("\t VERDE \t BLU \t GIALLO \t VIOLA");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if(j==0)
                    {
                        System.out.print("LVL 3\t");
                    }
                    System.out.print(developmentCards[i][j].peek().toString() + "\t");
                }
                System.out.println();
            }
            System.out.println();
            for (Player player : getPlayers()) {
                System.out.println("Player: " + player.getName());
                if (player == null) {
                    break;
                }
                System.out.println("Pos Fede: " + player.getPosFaithMarker());
                System.out.println("WAREHOUSE STD");
                for (int i = 0; i < player.getWarehousesStandard().size(); i++) {
                    System.out.println("WRH " + i + ") ==> " + player.getWarehousesStandard().get(i).toString());
                }
                System.out.println("WAREHOUSE ADD");
                for (int i = 0; i < player.getWarehousesAdditional().size(); i++) {
                    System.out.println("WRH " + i + ") ==> " + player.getWarehousesStandard().get(i).toString());
                }
                System.out.println("FORZIERE");
                System.out.println(player.getStrongBox().toString());
                System.out.println("SPAZI CARTE");
                for (int a = 0; a < player.getDevelopmentCardSpaces().size(); a++) {
                    System.out.println(player.getDevelopmentCardSpaces().get(a).toString());
                }
                System.out.println("LEADER CARD");
                for (LeaderCard leaderCard : player.getLeaderCards()) {
                    System.out.println(leaderCard.toString());
                }
                System.out.println("LORENZO IL MAGNIFICO");
                System.out.println("Faith pos: "+posBlackCross);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
