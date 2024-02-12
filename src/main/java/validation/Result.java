package validation;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Result {
    private String message;
    private boolean success;
    private LocalDateTime errorDate;

    public Result(String message) {
        this.success = true;
        this.message = message;
        this.errorDate = errorDate;
    }

    public Result(Set<? extends ConstraintViolation<?>> violations) {
        this.success = false;
        this.message = violations.stream()
                .map(cv -> cv.getMessage())
                .collect(Collectors.joining(", "));
        this.errorDate = LocalDateTime.now(ZoneId.of("Europe/Rome"));
    }

}
