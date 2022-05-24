package az.abbbank.cloud.etaskify.exception;

public class InvalidTaskStatusEnumException extends RuntimeException{
    public InvalidTaskStatusEnumException() {
    }

    public InvalidTaskStatusEnumException(String message) {
        super(message);
    }
}
