package az.abbbank.cloud.etaskify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidTaskStatusEnumException extends RuntimeException{
    public InvalidTaskStatusEnumException() {
    }

    public InvalidTaskStatusEnumException(String message) {
        super(message);
    }
}
