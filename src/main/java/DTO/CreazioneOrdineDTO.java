package DTO;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.util.List;


public class CreazioneOrdineDTO extends PanacheEntityBase {

    public Long clienteId;
    public List<DettaglioOrdineDTO> dettagliOrdine;
}
