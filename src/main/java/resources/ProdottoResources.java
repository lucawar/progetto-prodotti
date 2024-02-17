package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import exceptions.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import validation.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Path("/prodotti")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ProdottoResources {


    @Inject
    ProdottoRepository pr;
    @Inject
    Validator validator;


    // CREAZIONE DI UN NUOVO PRODOTTO CON VALIDAZIONE
    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Prodotto prodotto) {
        Set<ConstraintViolation<Prodotto>> violations = validator.validate(prodotto);
        if (violations.isEmpty()) {
            // Se non ci sono violazioni di validazione, restituisci una risposta 200 OK con un messaggio di successo
            log.info("CREAZIONE DEL PRODOTTO: {} - VALIDAZIONE RIUSCITA", prodotto);
            prodotto.persist();
            return Response.ok(new Result("Prodotto valido! È stato convalidato con successo.")).build();
        } else {
            // Se ci sono violazioni di validazione, restituisci una risposta 400 Bad Request con i dettagli delle violazioni
            log.warn("CREAZIONE DEL PRODOTTO FALLITA A CAUSA DI VIOLAZIONI DI VALIDAZIONE {}", violations);
            return Response.status(Response.Status.BAD_REQUEST).entity(new Result(violations)).build();
        }
    }


    // OTTIENI UN PRODOTTO SPECIFICO PER ID
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Prodotto prodotto = Prodotto.findById(id);
        if (prodotto != null) {
            log.info("PRODOTTO CON ID: " + id + "TROVATO {}");
            return Response.ok(prodotto).build();
        } else {
            log.warn("PRODOTTO CON ID: "  + id +  " NON TROVATO {}");
            throw new NotFoundException("Prodotto con " + id + " non trovato {}");
        }
    }

    // OTTIENI LA LISTA DI TUTTI I PRODOTTI
    @GET
    @Path("/all")
    public Response getAll(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {

        List<Prodotto> listaProdotti = Prodotto.findAll().page(offset, limit).list();
        if (!listaProdotti.isEmpty()) {
            log.info("LISTA PRODOTTI OTTENUTA {}", listaProdotti);
            return Response.ok(listaProdotti).build();
        } else {
            log.warn("NESSUN PRODOTTO TROVATO");
            throw new NotFoundException("Nessun prodotto trovato");
        }
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
            prodottoEsistente.prezzo = nuovoProdotto.prezzo;
            prodottoEsistente.tipoProd = nuovoProdotto.tipoProd;
            prodottoEsistente.immagine = nuovoProdotto.immagine;
            log.info("PRODOTTO CON ID: " + id + " MODIFICATO {}");
            return Response.ok(prodottoEsistente).build();
        } else {
            log.warn("PRODOTTO CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Prodotto con id: " + id + " non trovato {}");
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
            log.info("PRODOTTO CON ID: " + id + " ELIMINATO {}");
            return Response.ok(true).build();
        } else {
            log.warn("PRODOTTO CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Prodotto con id: " + id + " non trovato {}");
        }
    }


    // FILTRAGGIO PRODOTTI
    @GET
    @Path("/filtro")
    public Response getProdottiByAttributi(
            @QueryParam("marca") String marca,
            @QueryParam("tipoProd") ProdottoTipologia tipoProd,
            @QueryParam("nome") String nome,
            @QueryParam("prezzoMin") BigDecimal prezzoMin,
            @QueryParam("prezzoMax") BigDecimal prezzoMax,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {

        List<Prodotto> prodotti = pr.findByAttributi(marca, tipoProd, nome, prezzoMin, prezzoMax, offset, limit);

        if (!prodotti.isEmpty()) {
            log.info("PRODOTTI TROVATI PER I PARAMETRI SPECIFICATI");
            return Response.ok(prodotti).build();
        } else {
            log.warn("NESSUN PRODOTTO TROVATO CON I PARAMETRI SPECIFICATI");
            throw new NotFoundException("Nessun prodotto trovato con i parametri specificati");
        }
    }
}


