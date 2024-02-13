package DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreazioneOrdineDTO {

    public Long clienteId;
    public List<DettaglioOrdineDTO> dettagliOrdine;
}
