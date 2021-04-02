package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

public abstract class PlayerMove {
    private String name_of_user;
    public PlayerMove (String name_of_user)
    {
        this.name_of_user = name_of_user;
    }
    public abstract void execute(Match match);
    public String getName_of_user()
    {
        return name_of_user;
    }
    //TODO: add player al posto di stringa
}
