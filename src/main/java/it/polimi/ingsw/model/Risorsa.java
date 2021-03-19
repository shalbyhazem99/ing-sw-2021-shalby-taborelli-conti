package it.polimi.ingsw.model;


/**
 * @generated
 */
public class Risorsa {
    private TipoRisorsa tipo;
    public Risorsa(TipoRisorsa tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo.toString();
    }
}
