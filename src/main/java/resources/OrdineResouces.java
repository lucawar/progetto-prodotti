package resources;

import DTO.CreazioneOrdineDTO;
import DTO.DettaglioOrdineDTO;
import entities.Cliente;
import entities.Prodotto;
import entities.ordine.DettaglioOrdine;
import entities.ordine.Ordine;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Path("/ordine")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class OrdineResouces {

    @Inject
    Validator validator;

    // CREA NUOVO ORDINE
    @POST
    @Path("/crea")
    @Transactional
    public Response creaOrdine(CreazioneOrdineDTO creazioneOrdineDTO) {
        // Validazione del DTO
        Set<ConstraintViolation<CreazioneOrdineDTO>> violations = validator.validate(creazioneOrdineDTO);
        if (!violations.isEmpty()) {
            log.warn("CREAZIONE DELL'ORDINE FALLITA A CAUSA DI VIOLAZIONI DI VALIDAZIONE {}", violations);
            return Response.status(Response.Status.BAD_REQUEST).entity(new Result(violations)).build();
        }

        Cliente cliente = Cliente.findById(creazioneOrdineDTO.clienteId);
        if (cliente == null) {
            log.warn("CLIENTE CON ID {} NON TROVATO", creazioneOrdineDTO.clienteId);
            throw new NotFoundException("Cliente non trovato");
        }

        // Creazione dell'ordine
        Ordine ordine = new Ordine();
        ordine.setCliente(cliente);
        ordine.setDataOrdine(LocalDateTime.now());

        LocalDateTime dataConsegna = LocalDateTime.now().plusDays(5);
        ordine.setDataConsegna(dataConsegna);

        BigDecimal prezzoTotaleOrdine = BigDecimal.ZERO;

        log.info("ORDINE CREATO CON SUCCESSO {}", ordine);

        // Creazione dei dettagli dell'ordine
        List<DettaglioOrdineDTO> dettagliOrdineDTO = creazioneOrdineDTO.dettagliOrdine;
        for (DettaglioOrdineDTO dettaglioDTO : dettagliOrdineDTO) {
            if (dettaglioDTO.prodottoId == null || dettaglioDTO.quantita <= 0) {
                log.warn("LA QUANTITA DEVE ESSERE MINIMO 1: {}", dettaglioDTO);
                return Response.status(Response.Status.BAD_REQUEST).entity(new Result("La quantità deve essere almeno 1")).build();
            }

            DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();
            //dettaglioOrdine.setOrdine_id(ordine.id);


            // Recupera l'oggetto Prodotto utilizzando l'ID fornito nel DTO
            Prodotto prodotto = Prodotto.findById(dettaglioDTO.prodottoId);
            if (prodotto == null) {
                log.warn("PRODOTTO CON ID {} NON TROVATO", dettaglioDTO.prodottoId);
                throw new NotFoundException("Prodotto non trovato");
            }

            dettaglioOrdine.setProdotto(prodotto);
            dettaglioOrdine.setQuantita(dettaglioDTO.quantita);
            dettaglioOrdine.setPrezzoParziale(prodotto.prezzo.multiply(BigDecimal.valueOf(dettaglioDTO.quantita)));
            ordine.getDettaglioOrdine().add(dettaglioOrdine);

            // Persisti il dettaglio dell'ordine
            dettaglioOrdine.persist();

            // Calcola e imposta il prezzo totale dell'ordine
            prezzoTotaleOrdine = prezzoTotaleOrdine.add(dettaglioOrdine.getPrezzoParziale());
            //dettaglioOrdine.persist();
        }

        ordine.setPrezzoTotale(prezzoTotaleOrdine);
        ordine.persist();

        log.info("ORDINE COMPLETATO-------------");
        return Response.status(Response.Status.CREATED).entity(new Result("Ordine creato con successo")).build();
    }

    // OTTIENI LISTA ORDINI
    @GET
    @Path("/all")
    public Response getAll(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {
        List<Ordine> listaOrdini = Ordine.findAll().page(offset, limit).list();
        if (!listaOrdini.isEmpty()) {
            log.info("TUTTI GLI ORDINI OTTENUTI {}", listaOrdini);
            return Response.ok(listaOrdini).build();
        } else {
            log.warn("NESSUN ORDINE TROVATO");
            throw new NotFoundException("Nessun ordine trovato");
        }
    }

    // CERCA ORDINE PER ID
    @GET
    @Path("/{id}")
    public Response getOrdineById(@PathParam("id") Long id) {
        Ordine ordine = Ordine.findById(id);
        if (ordine != null) {
            log.info("ORDINE CON ID: " + id + " TROVATO");
            return Response.ok(ordine).build();
        } else {
            log.warn("ORDINE CON ID: " + id + " NON TROVATO");
            throw new NotFoundException("Ordine con ID: " + id + "non trovato");
        }
    }
}

