package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/prodotti")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdottoResources {

    @Inject
    ProdottoRepository pr;


    //CREA PRODOTTO
    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Prodotto prodotto) {
        if (!prodotto.isPersistent()) prodotto.persist();
        return Response.ok(true).build();
    }

    //GET LISTA PRODOTTI
    @GET
    @Path("/all")
    public Response getAll() {
        List<Prodotto> listaProdotti = Prodotto.listAll();
        return Response.ok(listaProdotti).build();
    }


    //FILTRA PER TIPO PRODOTTO
    @GET
    @Path("/tipo/{tipo}")
    public Prodotto getProdottoPerTipo(@PathParam("tipo") ProdottoTipologia tipo) {
        Optional<Prodotto> prodottoOptional = pr.findByTipoProdotto(tipo);
        return prodottoOptional.orElseThrow(() -> new NotFoundException("Prodotto non trovato"));
    }
}

