package it.polimi.ingsw.model;

public enum ColoreBiglia {
    BIANCHE(null),
    BLU(TipoRisorsa.SCUDI),
    GRIGIE(TipoRisorsa.PIETRE),
    GIALLE(TipoRisorsa.MONETE),
    VIOLA(TipoRisorsa.SERVITORI),
    ROSSA(TipoRisorsa.FEDE);

    public final TipoRisorsa equivalent;

    private ColoreBiglia(TipoRisorsa equivalent) {
        this.equivalent = equivalent;
    }

}
