package resources;

import entities.Cliente;
import enums.ClienteTipologia;
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

import java.util.List;
import java.util.Set;

@Path("/cliente")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ClienteResources {

    @Inject
    ClienteRepository cr;

    @Inject
    Validator validator;

    // CREA CLIENTE
    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Cliente cliente) {
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if (violations.isEmpty()) {
            // Se non ci sono violazioni di validazione, restituisci una risposta 200 OK con un messaggio di successo
            log.info("CREAZIONE DEL CLIENTE: {} - VALIDAZIONE RIUSCITA", cliente
            );
            cliente.persist();
            return Response.ok(new Result("Cliente valido! È stato convalidato con successo.")).build();
        } else {
            // Se ci sono violazioni di validazione, restituisci una risposta 400 Bad Request con i dettagli delle violazioni
            log.warn("CREAZIONE DEL CLIENTE FALLITA A CAUSA DI VIOLAZIONI DI VALIDAZIONE {}", violations);
            return Response.status(Response.Status.BAD_REQUEST).entity(new Result(violations)).build();
        }
    }

    // OTTIENI UN CLIENTE SPECIFICO PER ID
    @GET
    @Path("/{id}")
    public Response getClienteById(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente != null) {
            log.info("CLIENTE CON ID: " + id + "TROVATO {}");
            return Response.ok(cliente).build();
        } else {
            log.warn("CLIENTE CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Cliente con ID: " + id + " non trovato");
        }
    }

    // OTTIENI LA LISTA DI TUTTI I PRODOTTI
    @GET
    @Path("/all")
    public Response getAll(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {

        List<Cliente> listaClienti = Cliente.findAll().page(offset, limit).list();
        if (!listaClienti.isEmpty()) {
            log.info("LISTA CLIENTI OTTENUTA {}", listaClienti);
            return Response.ok(listaClienti).build();
        } else {
            log.warn("NESSUN CLIENTE TROVATO");
            throw new NotFoundException("Nessun cliente trovato");
        }
    }

    // AGGIORNA UN CLIENTE ESISTENTE
    @PUT
    @Path("/modifica/{id}")
    @Transactional
    public Response modifica(@PathParam("id") Long id, Cliente nuovoCLiente) {
        Cliente clienteEsistente = Cliente.findById(id);
        if (clienteEsistente != null) {
            clienteEsistente.nome = nuovoCLiente.nome;
            clienteEsistente.email = nuovoCLiente.email;
            clienteEsistente.indirizzo = nuovoCLiente.indirizzo;
            clienteEsistente.numeroTelefono = nuovoCLiente.numeroTelefono;
            clienteEsistente.tipoCliente = nuovoCLiente.tipoCliente;
            log.info("CLIENTE CON ID: " + id + " TROVATO {}");
            return Response.ok(clienteEsistente).build();
        } else {
            log.warn("CLIENTE CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Cliente con ID: " + id + " non trovato {}");
        }
    }

    // ELIMINA UN PRODOTTO PER ID
    @DELETE
    @Path("/elimina/{id}")
    @Transactional
    public Response elimina(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente != null) {
            cliente.delete();
            log.info("CLIENTE CON ID: " + id + " ELIMINATO {}");
            return Response.ok(true).build();
        } else {
            log.warn("CLIENTE CON ID: " + id + " NON TROVATO {}");
            throw new NotFoundException("Cliente con ID: " + id + " non trovato {}");
        }
    }

    // FILTRAGGIO CLIENTI
    @GET
    @Path("/filtro")
    public Response getClientiByAttributi(
            @QueryParam("nome") String nome,
            @QueryParam("indirizzo") String indirizzo,
            @QueryParam("email") String email,
            @QueryParam("numeroTelefono") String numeroTelefono,
            @QueryParam("tipoCliente")ClienteTipologia tipoCliente,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {

        List<Cliente> clienti = cr.findByAttribute(nome, indirizzo, email, numeroTelefono, tipoCliente, offset, limit);

        if (!clienti.isEmpty()) {
            log.info("CLIENTI TROVATI PER I PARAMETRI SPECIFICATI");
            return Response.ok(clienti).build();
        } else {
            log.warn("NESSUN CLIENTE TROVATO CON I PARAMETRI SPECIFICATI");
            throw new NotFoundException("Nessun cliente trovato con i parametri specificati");
        }
    }
}
