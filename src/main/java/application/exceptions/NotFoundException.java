package application.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class NotFoundException extends DataIntegrityViolationException {

    public NotFoundException( String message ) {
        super( message );
    }

    public NotFoundException( String message, Throwable cause ) {
        super( message, cause );
    }

}
