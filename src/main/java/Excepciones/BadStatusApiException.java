package Excepciones;

public class BadStatusApiException extends RuntimeException{
    public BadStatusApiException(String message) {
        super(message);
    }
}
