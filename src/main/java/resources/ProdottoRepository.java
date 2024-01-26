package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ProdottoRepository implements PanacheRepository<Prodotto> {

    public Optional<Prodotto> findByTipoProdotto(ProdottoTipologia tipo) {
        return find("tipoProd = ?1", tipo).singleResultOptional();
    }
}
