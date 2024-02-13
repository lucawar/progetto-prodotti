package resources;

import DTO.CreazioneOrdineDTO;
import DTO.DettaglioOrdineDTO;
import entities.Cliente;
import entities.Prodotto;
import entities.ordine.DettaglioOrdine;
import entities.ordine.Ordine;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Path("/ordine")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class OrdineResouces {

    @POST
    @Path("/crea")
    @Transactional
    public Response creaOrdine(CreazioneOrdineDTO creazioneOrdineDTO) {
        log.info("Inizio creazione dell'ordine...");

        // Creazione dell'ordine
        Cliente cliente = Cliente.findById(creazioneOrdineDTO.clienteId);
        Ordine ordine = new Ordine();
        ordine.setCliente(cliente);
        ordine.setDataOrdine(LocalDateTime.now()); // Imposta la data dell'ordine alla data corrente

        // Calcola e imposta la data di consegna 5 giorni dopo la data dell'ordine
        LocalDateTime dataConsegna = LocalDateTime.now().plusDays(5);
        ordine.setDataConsegna(dataConsegna);

        ordine.persist();

        log.info("Ordine creato con successo.");


            // Creazione dei dettagli dell'ordine
            List<DettaglioOrdineDTO> dettagliOrdineDTO = creazioneOrdineDTO.dettagliOrdine;
            for (DettaglioOrdineDTO dettaglioDTO : dettagliOrdineDTO) {
                DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();
                dettaglioOrdine.setOrdine(ordine);

                // Recupera l'oggetto Prodotto utilizzando l'ID fornito nel DTO
                Prodotto prodotto = Prodotto.findById(dettaglioDTO.prodottoId);

                dettaglioOrdine.setProdotto(prodotto);
                dettaglioOrdine.setQuantita(dettaglioDTO.quantita);
                dettaglioOrdine.calcolaPrezzoParziale(); // Calcola il prezzo parziale

                // Aggiungi il dettaglio all'ordine
                ordine.getDettaglioOrdine().add(dettaglioOrdine);

                // Imposta l'ordine sul dettaglio
                dettaglioOrdine.setOrdine(ordine);

                // Salva il dettaglio dell'ordine
                dettaglioOrdine.persist();
            }

            log.info("Ordine completato.");



        return Response.status(Response.Status.CREATED).build();
    }
}

