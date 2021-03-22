package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Stack;

public class Match {
    private int locationInkwell;
    private java.util.ArrayList<Player> players;
    private java.util.ArrayList<LeaderCard> leaderCards;
    private Stack<DevelopmentCard>[][] developmentCards;
    private MarketStructure marketplace;
    private ArrayList<DamageCounterAction> damageCounterAction;
    private boolean solomode;
    private int locationBlackCross;
}
