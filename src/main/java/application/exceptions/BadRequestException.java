package application.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class BadRequestException extends DataIntegrityViolationException {

    public BadRequestException( String message ) {
        super( message );
    }

    public BadRequestException( String message, Throwable cause ) {
        super( message, cause );
    }
}
