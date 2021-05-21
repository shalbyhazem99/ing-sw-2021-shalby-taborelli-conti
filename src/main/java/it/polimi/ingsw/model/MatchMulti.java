package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.production.move.EnableProductionPlayerMove;
import it.polimi.ingsw.controller.move.settings.AskForMove;
import it.polimi.ingsw.controller.move.settings.EndMatch;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

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
        if (!noControl && (!isMyTurn(player) || pendingResources.size() != 0 || !getCanChangeTurn())) {
            notify(EndRoundResponse.getInstance(new ArrayList<>(Arrays.asList(player)), getPlayers().indexOf(player), false, this.hashCode()));
            askForMove();
            return;
        }
        updateTurn();
        this.pendingResources = new ArrayList<>();
        notify(EndRoundResponse.getInstance(getPlayers(), getPlayers().indexOf(player), true, this.hashCode()));
        if (!hasWon(noControl)) {
            askForMove();
        } else {
            // todo: close connection
        }
    }


    @Override
    public void startMatch() {
        randomlyPickInkwellPlayer();
        turn = posInkwell;
        //assign starting resources
        //todo:choose resources
        //start
        super.startMatch();
        //askForDiscardLeaderCard();
        //askForMove();
    }

    @Override
    public void discardTwoLeaderCardInteraction(int posFirst, int posSecond, Player player) {
        super.discardTwoLeaderCardInteraction(posFirst, posSecond, player);
        notifyModel();
        if (numPlayerWhoDiscard == players.size()) {
            //notifyModel();
            askForMove();
        }
    }

    public void askForMove() {
        //notifyModel();
        ArrayList<MovePlayerType> possibleMove = new ArrayList<>();
        if (!canChangeTurn) {
            possibleMove.add(MovePlayerType.MARKET_INTERACTION);
            possibleMove.add(MovePlayerType.BUY_DEVELOPMENT_CARD);
            possibleMove.add(MovePlayerType.ENABLE_PRODUCTION); //TODO: to separate because multiple production could be activated
        }
        possibleMove.add(MovePlayerType.ENABLE_LEADER_CARD);
        possibleMove.add(MovePlayerType.DISCARD_LEADER_CARD);
        possibleMove.add(MovePlayerType.MOVE_RESOURCES);
        possibleMove.add(MovePlayerType.END_TURN);
        notify(AskForMove.getInstance(new ArrayList<>(Arrays.asList(players.get(turn))), possibleMove, turn, this.hashCode()));
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
        return getPlayers().get(posInkwell);
    }

    public void enableFinalTurn() {
        this.finalTurn = true;
    }

    public boolean hasWon(boolean noControl) {
        if (this.finalTurn) {
            if (turn == posInkwell) {
                if (!noControl) {
                    for (int i = 0; i < players.size(); i++) {
                        notify(EndMatch.getInstance(players.get(i), i, this.hashCode()));
                    }
                }
            }
            return true;
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getDevelopmentCards().size() >= 7 || players.get(i).getPosFaithMarker() >= 24) {
                this.enableFinalTurn();
            }
        }
        return false;
    }

    public ArrayList<Winner> whoIsWinner() {
        ArrayList<Winner> winners = new ArrayList<>();

        //Adding the points from the Faith Track
        for (int i = 0; i < players.size(); i++) {
            int totalPoints = 0;

            if (players.get(i).getPosFaithMarker() < 3) {
                totalPoints += 0;
            } else if (players.get(i).getPosFaithMarker() < 6) {
                totalPoints += 1;
            } else if (players.get(i).getPosFaithMarker() < 9) {
                totalPoints += 2;
            } else if (players.get(i).getPosFaithMarker() < 12) {
                totalPoints += 4;
            } else if (players.get(i).getPosFaithMarker() < 15) {
                totalPoints += 6;
            } else if (players.get(i).getPosFaithMarker() < 18) {
                totalPoints += 9;
            } else if (players.get(i).getPosFaithMarker() < 21) {
                totalPoints += 12;
            } else if (players.get(i).getPosFaithMarker() < 24) {
                totalPoints += 16;
            } else {
                totalPoints += 20;
            }

            //Getting the point from the Pope's Tales
            for (int j = 0; j < 3; j++) {
                totalPoints += players.get(i).getPopeFavorTiles().get(j).getPoints();
            }

            //Getting the points from the LeaderCards that are activated
            for (int j = 0; j < players.get(i).getLeaderCards().size(); j++) {
                totalPoints += players.get(i).getLeaderCard(j).getPoints();
            }

            //Counting all the Resources stored
            int totalResources = 0;
            for (int j = 0; j < 3; j++) {
                totalResources += players.get(i).getWarehousesStandard().get(j).getResources().size();
            }
            for (int j = 0; j < players.get(i).getWarehousesStandard().size(); j++) {
                totalResources += players.get(i).getWarehousesAdditional().get(j).getResources().size();
            }
            totalResources += players.get(i).getStrongBox().size();
            totalPoints = (int) totalResources / 5;

            //creating the ArrayList of Winners
            if (i == 0) {
                winners.add(Winner.getInstance(players.get(i).getName(), totalPoints, totalResources, turn));
            } else if (totalPoints == winners.get(0).getPoints()) {
                if (totalResources == winners.get(0).getTotalResources()) {
                    winners.add(Winner.getInstance(players.get(i).getName(), totalPoints, totalResources, turn));
                } else if (totalResources > winners.get(0).getTotalResources()) {
                    winners.remove(0);
                    winners.add(Winner.getInstance(players.get(i).getName(), totalPoints, totalResources, turn));
                }
            } else if (totalPoints > winners.get(0).getPoints()) {
                winners.remove(0);
                winners.add(Winner.getInstance(players.get(i).getName(), totalPoints, totalResources, turn));
            }
        }
        return winners;
    }

}
