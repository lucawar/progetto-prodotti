package entities.ordine;

import entities.Prodotto;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "dettaglio_ordine")
public class DettaglioOrdine extends PanacheEntity {

    @ManyToOne
    public Ordine ordine;

    @ManyToOne
    public Prodotto prodotto;

    public int quantita;

    public BigDecimal prezzoParziale;

}
