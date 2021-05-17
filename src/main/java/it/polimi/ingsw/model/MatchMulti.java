package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.production.move.EnableProductionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.settings.AskForMove;
import it.polimi.ingsw.exceptions.EndRoundException;
import it.polimi.ingsw.exceptions.SwapWarehouseException;
import it.polimi.ingsw.model.leaderCard.LeaderCard;

import java.io.Serializable;
import java.util.*;

public class MatchMulti extends Match implements Serializable {
    /**
     * Class concerning a Match of many players
     */
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
    public void endRoundInteraction(Player player) throws EndRoundException {
        if (!isMyTurn(player)) {
            throw new EndRoundException();
        }
        if (pendingResources.size() != 0) {
            throw new EndRoundException();
        }
        if (!getCanChangeTurn()) {
            throw new EndRoundException();
        }
        if (turn == getPlayers().size() - 1) {
            turn = 0;
        } else {
            turn++;
        }
        this.canChangeTurn = false;
        this.pendingResources = new ArrayList<>();
        notify(EndRoundResponse.getInstance(getPlayers(), true));
        askForMove();
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

    @Override
    public void startMatch() {
        randomlyPickInkwellPlayer();
        turn = posInkwell;
        //assign starting resources
        //todo:choose resources
        //start
        super.startMatch();
        askForMove();
    }

    private void askForMove() {
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
        notify(AskForMove.getInstance(new ArrayList<>(Arrays.asList(players.get(turn))), possibleMove));
    }

    @Override
    public void updateTurn() {
        if (canChangeTurn) {
            canChangeTurn = false;
            if (turn < getPlayers().size() - 1)
                turn++;
            else
                turn = 0;
        }
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
        return getPlayers().get(turn).equals(player);
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
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(developmentCards[i][j].peek().toString() + "|");
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
            }
            System.out.println("--------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Player getCurrentPlayer() {
        return getPlayers().get(posInkwell);
    }

    public static void main(String[] args) {
        MatchMulti m = new MatchMulti(2);
        m.toString();
    }
}
