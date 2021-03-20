package it.polimi.ingsw.model;


/**
 * @generated
 */
public class Risorsa {
    private TipoRisorsa tipo;
    public Risorsa(TipoRisorsa tipo) {
        this.tipo = tipo;
    }

    public static Risorsa getInstance(TipoRisorsa tipoRisorsa){
        return new Risorsa(tipoRisorsa);
    }

    public TipoRisorsa getTipo() {
        return tipo;
    }
}
