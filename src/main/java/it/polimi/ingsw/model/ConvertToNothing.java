package it.polimi.ingsw.model;

import java.util.ArrayList;

public class ConvertToNothing extends ConverterAbstract{
    @Override
    public ArrayList<Risorsa> convertMarbleToResources(ArrayList<Biglia> marbles) {
        ArrayList<Risorsa> temp = new ArrayList<>();
        marbles.forEach((marble)-> {
            switch (marble.getColore())
            {
                case BIANCHE: break;
                case BLU: temp.add(new Risorsa(TipoRisorsa.SCUDI));break;
                case GRIGIE: temp.add(new Risorsa(TipoRisorsa.PIETRE));break;
                case GIALLE: temp.add(new Risorsa(TipoRisorsa.MONETE));break;
                case VIOLA: temp.add(new Risorsa(TipoRisorsa.SERVITORI));break;
                case ROSSA: //dovremmo prevedere una risorsa per il punto fede?;
            }
        });
        return temp;
    }
}