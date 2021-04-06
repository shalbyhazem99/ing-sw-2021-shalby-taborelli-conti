
package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

public abstract class PlayerMove {
    protected Player player;

    public abstract void execute(Match match);

    public Player getPlayer() {
        return player;
    }

    public void concatPlayer(Player player) {
        this.player = player;
    }
}