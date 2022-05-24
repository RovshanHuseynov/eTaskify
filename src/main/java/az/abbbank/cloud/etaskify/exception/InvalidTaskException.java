package az.abbbank.cloud.etaskify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidTaskException extends RuntimeException{
    public InvalidTaskException() {
    }

    public InvalidTaskException(String message) {
        super(message);
    }
}
