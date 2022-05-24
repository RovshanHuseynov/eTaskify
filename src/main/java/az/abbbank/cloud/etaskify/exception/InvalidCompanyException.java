package az.abbbank.cloud.etaskify.exception;

public class InvalidCompanyException extends RuntimeException{
    public InvalidCompanyException() {
    }

    public InvalidCompanyException(String message) {
        super(message);
    }
}
