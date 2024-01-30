package exceptions;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {

        if (e instanceof BadRequestException) {
            return handleBadRequest((BadRequestException) e);
        } else if (e instanceof NotFoundException) {
            return handleNotFound((NotFoundException) e);
        } else {
            return handleGeneric(e);
        }
    }

    // GENERA "404 Not Found", LA RISORSA NON ESISTE NEL DATABASE
    private  Response handleNotFound(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorPayload(e.getMessage()))
                .build();
    }

    // GENERA "400 Bad Request", DATI INPUT NON VALIDI O NON CONFORMI ALLE ASPETTATIVE
    private Response handleBadRequest(BadRequestException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorPayload(e.getMessage()))
                .build();
    }

    // GENERA "500 Internal Server Error", ERRORI NON SPECIFICI O INATTESI
    private Response handleGeneric(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorPayload("Errore generico"))
                .build();
    }
}
