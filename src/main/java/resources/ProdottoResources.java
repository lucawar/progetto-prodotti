package resources;

import entities.Prodotto;
import enums.ProdottoTipologia;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Path("/prodotti")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ProdottoResources {


    @Inject
    ProdottoRepository pr;


    // CREAZIONE DI UN NUOVO PRODOTTO
    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Prodotto prodotto) {
        if (!prodotto.isPersistent()) {
            prodotto.persist();
            log.info("PRODOTTO CREATO CON SUCCESSO {}", prodotto);
            return Response.ok(prodotto).build();
        } else {
            throw new BadRequestException("Input non valido");
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
            log.info("PRODOTTO CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Prodotto con ID: " + id + " non trovato.");
        }
    }

    // OTTIENI LA LISTA DI TUTTI I PRODOTTI
    @GET
    @Path("/all")
    public Response getAll() {
        List<Prodotto> listaProdotti = Prodotto.listAll();
        if (!listaProdotti.isEmpty()) {
            log.info("LISTA PRODOTTI OTTENUTA {}", listaProdotti);
            return Response.ok(listaProdotti).build();
        } else {
            log.info("NESSUN PRODOTTO TROVATO");
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
            prodottoEsistente.quantitaMagazzino = nuovoProdotto.quantitaMagazzino;
            prodottoEsistente.prezzo = nuovoProdotto.prezzo;
            prodottoEsistente.tipoProd = nuovoProdotto.tipoProd;
            log.info("PRODOTTO CON ID: " + id + " TROVATO {}");
            return Response.ok(prodottoEsistente).build();
        } else {
            log.info("PRODOTTO CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Prodotto con ID: " + id + " non trovato.");
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
            log.info("PRODOTTO CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Prodotto con ID: " + id + " non trovato.");
        }
    }

    // FILTRA PER TIPO PRODOTTO
    @GET
    @Path("/tipo/{tipo}")
    public Response getProdottiPerTipo(@PathParam("tipo") ProdottoTipologia tipo) {
        List<Prodotto> prodotti = pr.findByTipoProdotto(tipo);
        if (!prodotti.isEmpty()) {
            log.info("PRODOTTO/I TROVATO PER LA TIPOLOGIA: " + tipo);
            return Response.ok(prodotti).build();
        } else {
            log.info("NESSUN PRODOTTO TROVATO PER LA TIPOLOGIA: " + tipo);
            throw new NotFoundException("Nessun prodotto trovato per la tipologia: " + tipo);
        }
    }

    // Esempio di utilizzo della nuova query
    @GET
    @Path("/filtro")
    public Response getProdottiByAttributi(
            @QueryParam("marca") String marca,
            @QueryParam("prezzo") BigDecimal prezzo) {

        List<Prodotto> prodotti = pr.findByAttributi(marca, prezzo);

        if (!prodotti.isEmpty()) {
            log.info("PRODOTTI TROVATI PER I PARAMETRI SPECIFICATI");
            return Response.ok(prodotti).build();
        } else {
            log.info("NESSUN PRODOTTO TROVATO PER I PARAMETRI SPECIFICATI");
            throw new NotFoundException("Nessun prodotto trovato per i parametri specificati.");
        }
    }
}


