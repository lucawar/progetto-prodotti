package DTO;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


public class DettaglioOrdineDTO extends PanacheEntityBase {
    public Long prodottoId;
    public int quantita;
}
