package az.abbbank.cloud.etaskify.exception;

public class InvalidEmployeeException extends RuntimeException{
    public InvalidEmployeeException() {
    }

    public InvalidEmployeeException(String message) {
        super(message);
    }
}
