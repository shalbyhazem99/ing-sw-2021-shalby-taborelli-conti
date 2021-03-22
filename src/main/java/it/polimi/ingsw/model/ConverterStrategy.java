package it.polimi.ingsw.model;

import java.util.ArrayList;

public interface ConverterStrategy {
    public ArrayList<Risorsa> convertMarbleToResources(ArrayList<Biglia> marbles);
}
