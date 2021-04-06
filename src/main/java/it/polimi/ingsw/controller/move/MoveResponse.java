package it.polimi.ingsw.controller.move;

import java.io.Serializable;
import java.util.Scanner;

public abstract class MoveResponse implements Serializable {
    //todo: method that indicate something that the user must do in response to a GameManagerResponse
    public abstract PlayerMove elaborateCliInput(String message, final Scanner stdin);
}
