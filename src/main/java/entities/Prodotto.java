package entities;

import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"nome_prodotto", "marca"})})
public class Prodotto extends PanacheEntity {

    @Column(name = "nome_prodotto")
    @NotBlank(message = "Il campo 'nome' è obbligatorio")
    public String nome;

    @Column(name = "marca")
    @NotBlank(message = "Il campo 'marca' è obbligatorio")
    public String marca;

    @Column(name = "descrizione")
    @NotBlank(message = "Il campo 'descrizione' è obbligatorio")
    public String descrizione;

    @Column(name = "prezzo_prodotto")
    @NotNull(message = "Il campo 'prezzo' è obbligatorio")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0")
    public BigDecimal prezzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_prodotto")
    @NotNull(message = "Il tipo di prodotto è obbligatorio")
    public ProdottoTipologia tipoProd;

    @Column(name = "immagine_prodotto")
    public String immagine;

    @Override
    public String toString() {
        return String.format("Prodotto {" +
                "nome='%s', " +
                "marca='%s', " +
                "descrizione='%s', " +
                "prezzo=%.2f, " +
                "tipoProd=%s" +
                "}", nome, marca, descrizione, prezzo, tipoProd);
    }
}
