package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class ProdottoRepository implements PanacheRepository<Prodotto> {


    public List<Prodotto> findByTipoProdotto(ProdottoTipologia tipo) {
        return list("tipoProd = ?1", tipo);
    }

    /*
    public List<Prodotto> findByMarcaQuantitaPrezzo(String marca, int quantitaMagazzino, BigDecimal prezzo) {
        List<Prodotto> result = list("marca = ?1 and quantitaMagazzino = ?2 and prezzo = ?3", marca, quantitaMagazzino, prezzo);
        log.info("Query findByMarcaQuantitaPrezzo chiamata correttamente con i parametri: marca={}, quantitaMagazzino={}, prezzo={}", marca, quantitaMagazzino, prezzo);
        return result;
    }
    */

    // QUERY DINAMICA PER RICERCA DEI PRODOTTI TRAMITE 1 O PIU ATTRIBUTI
    public List<Prodotto> findByAttributi(String marca, BigDecimal prezzo) {
        String queryString = "FROM Prodotto p WHERE 1 = 1";
        Map<String, Object> params = new HashMap<>();

        if (marca != null) {
            queryString += " AND p.marca = :marca";
            params.put("marca", marca);
        }
        if (prezzo != null) {
            queryString += " AND p.prezzo = :prezzo";
            params.put("prezzo", prezzo);
        }

        return find(queryString, params).list();
    }
}
