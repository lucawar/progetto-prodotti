package DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DettaglioOrdineDTO {

    @NotNull(message = "L' ID del prodotto è obbligatorio")
    public Long prodottoId;

    @Min(value = 1, message = "La quantità deve essere almeno 1")
    @NotNull(message = "La quantità è obbligatoria")
    public int quantita;
}
