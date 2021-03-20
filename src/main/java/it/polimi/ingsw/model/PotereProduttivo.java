package it.polimi.ingsw.model;

import java.util.ArrayList;

public class PotereProduttivo {
    private java.util.ArrayList<CountRisorse> from;
    private java.util.ArrayList<Risorsa> to;

    public PotereProduttivo(ArrayList<CountRisorse> from, ArrayList<Risorsa> to) {
        this.from = from;
        this.to = to;
    }

    public static PotereProduttivo getInstance(ArrayList<CountRisorse> from, ArrayList<Risorsa> to){
        return new PotereProduttivo(from,to);
    }
}
