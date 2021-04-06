
package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public abstract class PlayerMove implements Serializable {
    protected Player player;

    public abstract void execute(Match match);

    public Player getPlayer() {
        return player;
    }

    public void concatPlayer(Player player) {
        this.player = player;
    }
}