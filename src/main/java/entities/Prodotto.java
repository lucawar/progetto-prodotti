package entities;

import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class Prodotto extends PanacheEntity {

    @Column(name = "nome_prodotto")
    public String nome;

    @Column(name = "marca")
    public String marca;

    @Column(name = "descrizione")
    public String descrizione;

    @Column(name = "prezzo_prodotto")
    public BigDecimal prezzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_prodotto")
    public ProdottoTipologia tipoProd;

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
