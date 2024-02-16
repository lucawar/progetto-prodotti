package DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreazioneOrdineDTO {

    @NotNull(message = "L'ID del cliente è obbligatorio")
    public Long clienteId;


    @NotNull(message = "La lista dettagliOrdine non può essere nulla")
    public List<DettaglioOrdineDTO> dettagliOrdine;
}
