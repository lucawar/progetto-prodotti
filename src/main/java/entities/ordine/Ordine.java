package entities.ordine;

import entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ordine extends PanacheEntity {

    @ManyToOne
    public Cliente cliente;
    @OneToMany(mappedBy = "ordine")
    public List<DettaglioOrdine> dettaglioOrdine = new ArrayList<>();

    public LocalDateTime dataOrdine;

    public LocalDateTime dataConsegna;

    public BigDecimal prezzoTotale;

}
