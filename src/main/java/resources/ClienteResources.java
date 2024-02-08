package resources;

import entities.Cliente;
import exceptions.BadRequestException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

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
    private Response crea(Cliente cliente) {
        if (!cliente.isPersistent()) {
            cliente.persist();
            log.info("CLIENTE CREATO CON SUCCESSO {}");
            return Response.ok(cliente).build();
        } else {
            throw new BadRequestException("CAMPI NON VALIDI");
        }
    }
}
