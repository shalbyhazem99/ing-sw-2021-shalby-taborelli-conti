package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.settings.AskForMove;

import java.io.Serializable;
import java.util.*;

public class MatchMulti extends Match implements Serializable {
    /**
     * Class concerning a Match of many players
     */
    private int posInkwell;
    private int turn;
    private boolean canChangeTurn;

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
        this.posInkwell = (int) (Math.random() * (0 - getPlayers().size())) + getPlayers().size();
        return this.posInkwell;
    }


    @Override
    public void startMatch() {
        randomlyPickInkwellPlayer();
        turn = posInkwell;
        super.startMatch();
        askForMove();
    }

    private void askForMove() {
        //TODO: choose what move to add
        ArrayList<MovePlayerType> possibleMove = new ArrayList<>();
        possibleMove.add(MovePlayerType.BUY_DEVELOPMENT_CARD);
        possibleMove.add(MovePlayerType.MARKET_INTERACTION);
        possibleMove.add(MovePlayerType.BUY_DEVELOPMENT_CARD);
        possibleMove.add(MovePlayerType.MARKET_INTERACTION);
        notify(AskForMove.getInstance(new ArrayList<>(Arrays.asList(players.get(turn - 1))), possibleMove));
    }

    @Override
    public void updateTurn() {
        if (canChangeTurn) {
            canChangeTurn = false;
            if (turn < getPlayers().size())
                turn++;
            else
                turn = 0;
        }
    }

    @Override
    public void setCanChangeTurn(boolean canChangeTurn) {
        this.canChangeTurn = canChangeTurn;
    }

    @Override
    public boolean getCanChangeTurn() {
        return this.canChangeTurn;
    }

    @Override
    public boolean isMyTurn(Player player) {
        return getPlayers().get(turn).equals(player);
    }
}
