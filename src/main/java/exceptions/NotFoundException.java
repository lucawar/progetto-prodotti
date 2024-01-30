package exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id) {
        super("Prodotto con ID: " + id + " non trovato");
    }
}
