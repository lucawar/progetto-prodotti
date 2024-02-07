package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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

    // QUERY DINAMICA PER RICERCA DEI PRODOTTI TRAMITE 1 O PIU ATTRIBUTI
    public List<Prodotto> findByAttributi(String marca, ProdottoTipologia tipoProd, String nome, BigDecimal prezzoMin, BigDecimal prezzoMax, int offset, int limit) {
        String queryString = "FROM Prodotto p WHERE 1 = 1";
        Map<String, Object> params = new HashMap<>();

        if (marca != null) {
            queryString += " AND p.marca = :marca";
            params.put("marca", marca);
        }
        if (tipoProd != null) {
            queryString += " AND p.tipoProd = :tipoProd";
            params.put("tipoProd", tipoProd);
        }
        if (nome != null) {
            queryString += " AND p.nome = :nome";
            params.put("nome", nome);
        }
        if (prezzoMin != null) {
            queryString += " AND p.prezzo >= :prezzoMin";
            params.put("prezzoMin", prezzoMin);
        }
        if (prezzoMax != null) {
            queryString += " AND p.prezzo <= :prezzoMax";
            params.put("prezzoMax", prezzoMax);
        }

        PanacheQuery<Prodotto> query = find(queryString, params);
        query.page(offset / limit, limit);
        log.info("PRODOTTI FILTRATI CON SUCCESSO!!!!!!");
        return query.list();
    }
}
