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

@Path("/prodotti")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdottoResources {

    @Inject
    ProdottoRepository pr;


    // CREAZIONE DI UN NUOVO PRODOTTO
    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Prodotto prodotto) {
        if (prodotto.isPersistent()) {
            prodotto.persist();
            return Response.ok(prodotto).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Il prodotto esiste già.")
                    .build();
        }
    }

    // OTTIENI UN PRODOTTO SPECIFICO PER ID
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Prodotto prodotto = Prodotto.findById(id);
        if (prodotto != null) {
            return Response.ok(prodotto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Prodotto con ID: " + id + " non trovato.")
                    .build();
        }
    }

    // OTTIENI LA LISTA DI TUTTI I PRODOTTI
    @GET
    @Path("/all")
    public Response getAll() {
        List<Prodotto> listaProdotti = Prodotto.listAll();
        if (listaProdotti.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nessun prodotto trovato.")
                    .build();
        }
        return Response.ok(listaProdotti).build();
    }

    // AGGIORNA UN PRODOTTO ESISTENTE
    @PUT
    @Path("/modifica/{id}")
    @Transactional
    public Response modifica(@PathParam("id") Long id, Prodotto nuovoProdotto) {
        Prodotto prodottoEsistente = Prodotto.findById(id);

        if (prodottoEsistente != null) {
            prodottoEsistente.nome = nuovoProdotto.nome;
            prodottoEsistente.marca = nuovoProdotto.marca;
            prodottoEsistente.descrizione = nuovoProdotto.descrizione;
            prodottoEsistente.quantitaMagazzino = nuovoProdotto.quantitaMagazzino;
            prodottoEsistente.prezzo = nuovoProdotto.prezzo;
            prodottoEsistente.tipoProd = nuovoProdotto.tipoProd;

            return Response.ok(prodottoEsistente).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Prodotto con ID: " + id + " non trovato.")
                    .build();
        }
    }

    // ELIMINA UN PRODOTTO PER ID
    @DELETE
    @Path("/elimina/{id}")
    @Transactional
    public Response elimina(@PathParam("id") Long id) {
        Prodotto prodotto = Prodotto.findById(id);
        if (prodotto != null) {
            prodotto.delete();
            return Response.ok(true).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Prodotto con ID: " + id + " non trovato.")
                    .build();
        }
    }

    // FILTRA PER TIPO PRODOTTO
    @GET
    @Path("/tipo/{tipo}")
    public Response getProdottiPerTipo(@PathParam("tipo") ProdottoTipologia tipo) {
        List<Prodotto> prodotti = pr.findByTipoProdotto(tipo);
        if (prodotti.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nessun prodotto trovato per il tipo: " + tipo)
                    .build();
        }
        return Response.ok(prodotti).build();
    }
}

