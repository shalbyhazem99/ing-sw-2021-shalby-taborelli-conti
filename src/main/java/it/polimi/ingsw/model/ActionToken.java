package it.polimi.ingsw.model;

import java.io.Serializable;

public class ActionToken implements Serializable {
    private MarkerType azione;
    private int count;
    private DevelopmentCardType CardToReject;
}
