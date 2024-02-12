package resources;

import entities.Cliente;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Path("/cliente")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ClienteResources {

    @Inject
    ClienteRepository cr;

    // CREA CLIENTE
    @POST
    @Path("/crea")
    @Transactional
    public Response crea(Cliente cliente) {
        if (!cliente.isPersistent()) {
            cliente.persist();
            log.info("CLIENTE CREATO CON SUCCESSO {}");
            return Response.ok(cliente).build();
        } else {
            throw new BadRequestException("CAMPI NON VALIDI");
        }
    }

    // OTTIENI UN CLIENTE SPECIFICO PER ID
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente != null) {
            log.info("CLIENTE CON ID: " + id + "TROVATO {}");
            return Response.ok(cliente).build();
        } else {
            throw new NotFoundException("CLIENTE CON ID: " + id + " NON TROVATO {}");
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
            throw new NotFoundException("NESSUN CLIENTE TROVATO");
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
            clienteEsistente.cognome = nuovoCLiente.cognome;
            clienteEsistente.email = nuovoCLiente.email;
            clienteEsistente.indirizzo = nuovoCLiente.indirizzo;
            clienteEsistente.numeroTelefono = nuovoCLiente.numeroTelefono;
            clienteEsistente.tipoCliente = nuovoCLiente.tipoCliente;
            log.info("CLIENTE CON ID: " + id + " TROVATO {}");
            return Response.ok(clienteEsistente).build();
        } else {
            throw new NotFoundException("CLIENTE CON ID: " + id + " NON TROVATO {}");
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
            throw new NotFoundException("CLIENTE CON ID: " + id + " NON TROVATO {}");
        }
    }
}
