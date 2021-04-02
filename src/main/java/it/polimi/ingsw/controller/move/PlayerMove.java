package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

public abstract class PlayerMove {
    private Player player;
    public PlayerMove (Player player)
    {
        this.player = player;
    }
    public abstract void execute(Match match);
    public Player getPlayer()
    {
        return player;
    }
    //TODO: add player al posto di stringa
}
