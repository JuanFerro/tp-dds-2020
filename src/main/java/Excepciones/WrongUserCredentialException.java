package Excepciones;

import javax.persistence.NoResultException;

public class WrongUserCredentialException extends NoResultException {
    public WrongUserCredentialException() {}
    public WrongUserCredentialException(String message) {
        super(message);
    }
}
