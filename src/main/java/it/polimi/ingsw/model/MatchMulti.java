package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.leaderCard.DiscardTwoLeaderCardsResponse;
import it.polimi.ingsw.controller.move.production.move.EnableProductionPlayerMove;
import it.polimi.ingsw.controller.move.endMatch.EndMatchResponse;
import it.polimi.ingsw.controller.move.settings.SendMessage;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;

public class MatchMulti extends Match implements Serializable {
    /**
     * Class concerning a Match of many players
     */
    private boolean finalTurn = false;
    private int posInkwell;
    private int turn;
    //private boolean canChangeTurn;

    /**
     * Call method of superclass, posInkwell will be initially set to '-1' and then it will be correctly modified just before the match's starting
     * The {@link Player} {@link java.util.ArrayList} will be initalized at a fixed length
     */
    public MatchMulti(int numberOfPlayers) {
        super(numberOfPlayers);
        posInkwell = -1;
    }


    /**
     * Get the index of the first player in the playing order
     *
     * @return the index of the first plauer in the playing order
     */
    public int getPosInkwell() {
        return posInkwell;
    }

    /**
     * The method randomly choose the player with the inkwell
     *
     * @return the index of the {@link Player} with the inkwell
     */
    public int randomlyPickInkwellPlayer() {
        if (this.posInkwell != -1) {
            return this.posInkwell;
        }

        Random rand = new Random();
        this.posInkwell = rand.nextInt(getPlayers().size());
        return this.posInkwell;
    }

    /**
     * ATTENZIONE QUESTO METODO POI ANDRA' GESTITO DIVERSAMENTE DENTRO MATCHSOLO E MATCHMULTI, IO PER ORA LO METTO QUI E LO FACCIO PER MATCHMULTI
     * Method used by the {@link Player} to end his round
     *
     * @param player the {@link Player} who wants to end his round
     */
    public void endRoundInteraction(Player player, boolean noControl) {
        if (!noControl && (!isMyTurn(player) || pendingMarketResources.size() != 0 || !getCanChangeTurn())) {
            notify(EndRoundResponse.getInstance(new ArrayList<>(Arrays.asList(player)), getPlayers().indexOf(player), false, this.hashCode()));
            askForMove();
            return;
        }
        super.endRoundInteraction(player, noControl);
        updateTurn();
        this.pendingMarketResources = new ArrayList<>();
        if (!noControl) {
            notify(EndRoundResponse.getInstance(getPlayers(), getPlayers().indexOf(player), true, this.hashCode()));
            if (!hasWon()) {
                askForMove();
            } else {
                // todo: close connection
            }
        }
    }


    @Override
    public void startMatch() {
        randomlyPickInkwellPlayer();
        turn = posInkwell;
        //start
        super.startMatch();
        //askForDiscardLeaderCard();
        //askForMove();
    }

    @Override
    public void askForDiscardLeaderCard() {
        for (int i = 0; i < players.size(); i++) {
            int numOfRes=0;
            switch (i-posInkwell){
                case 1:
                case 2:
                    numOfRes=1;
                    break;
                case 3:
                    numOfRes=2;
                    break;
                default:
                    numOfRes=0;
            }
            notify(DiscardTwoLeaderCardsResponse.getInstance(players.get(i), i, this.hashCode(),numOfRes));
        }
    }

    @Override
    public void discardTwoLeaderCardInteraction(int posFirst, int posSecond, Player player, ResourceType resourceTypeFirst, ResourceType resourceTypeSecond) {
        if (posFirst != posSecond && posFirst >= 0 && posSecond >= 0 && posFirst < player.getLeaderCards().size() && posSecond < player.getLeaderCards().size()) {
            LeaderCard first = player.getLeaderCard(posFirst);
            LeaderCard second = player.getLeaderCard(posSecond);
            player.getLeaderCards().remove(first);
            player.getLeaderCards().remove(second);
            numPlayerWhoDiscard++;
        } else {
            notify(SendMessage.getInstance("Something wrong, Leader Cards cannot be discarded, retry", player, players.indexOf(player), this.hashCode()));
            notify(DiscardTwoLeaderCardsResponse.getInstance(player, players.indexOf(player), this.hashCode(),players.indexOf(player)-posInkwell ) );
            return;
        }
        switch (players.indexOf(player)-posInkwell){ // get the turn position
            case 1: // 2°
                player.getWarehousesStandard().get(0).addResource(Resource.getInstance(resourceTypeFirst));
                break;
            case 2: // 3°
                player.getWarehousesStandard().get(0).addResource(Resource.getInstance(resourceTypeFirst));
                player.moveAheadFaith(1);
                break;
            case 3: // 4°
                player.getWarehousesStandard().get(0).addResource(Resource.getInstance(resourceTypeFirst));
                player.getWarehousesStandard().get(1).addResource(Resource.getInstance(resourceTypeSecond));
                player.moveAheadFaith(1);
                break;
            default:
                break;
        }


        notifyModel();
        if (numPlayerWhoDiscard == players.size()) {
            //notifyModel();
            askForMove();
        }
    }

    @Override
    public void updateTurn() {
        canChangeTurn = false;
        if (turn < getPlayers().size() - 1)
            turn++;
        else
            turn = 0;
    }

    /**
     * Setter
     * It will be invoked passing a true parameter as soon as a "main" {@link it.polimi.ingsw.controller.move.PlayerMove} is executed
     * When is it invoked:
     * - When a {@link it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove} is executed and NO WHITE {@link it.polimi.ingsw.model.market.Marble} has to be converted
     * - When a {@link it.polimi.ingsw.controller.move.market.MarketMarbleConversionMove} is executed
     * - When a {@link it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove} is executed
     * - When a {@link EnableProductionPlayerMove} is executed
     *
     * @param canChangeTurn boolean value to be set
     * @param player        {@link Player} that perform the action
     */
    @Override
    public void setCanChangeTurn(boolean canChangeTurn, Player player) {
        if (isMyTurn(player)) {
            this.canChangeTurn = canChangeTurn;
        }
    }

    @Override
    public boolean isMyTurn(Player player) {
        //the second condition is for the first discard of the leader card at the beginning
        return getPlayers().get(turn).equals(player) || numPlayerWhoDiscard < players.size();
    }

    @Override
    public Player getCurrentPlayer() {
        return getPlayers().get(turn);
    }

    public void enableFinalTurn() {
        this.finalTurn = true;
    }

    public boolean hasWon() {
        if (this.finalTurn) {
            if (turn == posInkwell) {
                for (int i = 0; i < players.size(); i++) {
                    notify(EndMatchResponse.getInstance(players.get(i), i, this.hashCode()));
                }
                return true;
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getDevelopmentCards().size() >= 7 || players.get(i).getPosFaithMarker() >= 24) {
                this.enableFinalTurn();
                return turn == posInkwell;
            }

        }
        return false;
    }
}
