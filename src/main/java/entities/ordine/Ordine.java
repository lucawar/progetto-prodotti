package entities.ordine;

import entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
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

    @OneToMany(mappedBy = "ordine_id")
    public List<DettaglioOrdine> dettaglioOrdine = new ArrayList<>();

    @Column(name = "data_ordine")
    public LocalDateTime dataOrdine;

    @Column(name = "data_consegna_ordine")
    public LocalDateTime dataConsegna;

    @Column(name = "prezzo_totale_ordine")
    public BigDecimal prezzoTotale;

    @Override
    public String toString() {
        return String.format("Ordine {cliente='%s', dataOrdine='%s', dataConsegna='%s', prezzoTotale=%s}",
                cliente != null ? cliente.toString() : "null",
                dataOrdine != null ? dataOrdine.toString() : "null",
                dataConsegna != null ? dataConsegna.toString() : "null",
                prezzoTotale != null ? prezzoTotale.toString() : "null");
    }
}
