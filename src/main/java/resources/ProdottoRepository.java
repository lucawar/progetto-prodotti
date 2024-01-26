package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProdottoRepository implements PanacheRepository<Prodotto> {

    public List<Prodotto> findByTipoProdotto(ProdottoTipologia tipo) {
        return list("tipoProd = ?1", tipo);
    }
}
