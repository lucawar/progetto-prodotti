package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

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

    public List<Prodotto> findByAttributi(String marca, BigDecimal prezzo) {
        List<Prodotto> result;

        // Almeno uno dei parametri è specificato, costruisci la query con i parametri specificati
        String queryString = "1 = 1";

        if (marca != null) {
            queryString += " and marca = ?1";
        }
        if (prezzo != null) {
            queryString += " and prezzo >= ?2";
        }

        // Esegui la query solo se almeno uno dei parametri è specificato
        if (marca != null || prezzo != null) {
            result = list(queryString, marca, prezzo);
        } else {
            // Nessun parametro specificato, esegui una query senza restrizioni
            result = listAll();
        }
        log.info("Query findByAttributi chiamata correttamente con i parametri: marca={}, prezzo={}", marca, prezzo);
        return result;
    }
}
