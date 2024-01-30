package exceptions;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class ErrorPayload {

    String message;
    private ZonedDateTime timeStamp;

    public ErrorPayload(String message) {
        this.message = message;
        this.timeStamp = ZonedDateTime.now(ZoneId.of("Europe/Rome"));
    }
}
