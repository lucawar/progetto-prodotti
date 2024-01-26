package resources;

import entities.Prodotto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/prodotti")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdottoResources {

    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Prodotto prodotto) {
        if (!prodotto.isPersistent()) prodotto.persist();
        return Response.ok(true).build();
    }

    @GET
    @Path("/all")
    public Response getAll() {
        List<Prodotto> listaProdotti = Prodotto.listAll();
        return Response.ok(listaProdotti).build();
    }
}

