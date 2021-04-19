package Excepciones;

public class NoEgressContainsItemException extends RuntimeException {
    public NoEgressContainsItemException(String message) {
            super(message);
        }
}
