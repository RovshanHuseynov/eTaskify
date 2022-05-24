package az.abbbank.cloud.etaskify.exception;

public class InvalidTaskException extends RuntimeException{
    public InvalidTaskException() {
    }

    public InvalidTaskException(String message) {
        super(message);
    }
}
