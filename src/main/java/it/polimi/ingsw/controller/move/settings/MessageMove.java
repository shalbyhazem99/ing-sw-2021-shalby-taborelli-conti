package it.polimi.ingsw.controller.move.settings;

import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.model.Match;

public class MessageMove extends PlayerMove {
    String message;

    public MessageMove(String message) {
        this.message = message;
    }

    public static MessageMove getInstance(String message) {
        return new MessageMove(message);
    }
    @Override
    public void execute(Match match) {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
