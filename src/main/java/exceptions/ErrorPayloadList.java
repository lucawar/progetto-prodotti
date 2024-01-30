package exceptions;

import java.util.List;

public class ErrorPayloadList extends ErrorPayload{

    private final List<String> errors;

    public ErrorPayloadList(String message,List<String> errors) {
        super(message);
        this.errors = errors;
    }

}
