package application.controllers;

import application.exceptions.NotFoundException;
import application.exceptions.BadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order( Ordered.HIGHEST_PRECEDENCE )
@RestControllerAdvice
public class Resolver extends ResponseEntityExceptionHandler {

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( value = { BadRequestException.class } )
    public ResponseEntity<?> badRequestResolver( BadRequestException e ) {
        return new ResponseEntity<>( e, new HttpHeaders(),  HttpStatus.BAD_REQUEST );
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler( value = { NotFoundException.class } )
    public ResponseEntity<?> notFoundException( NotFoundException e ) {
        return new ResponseEntity<>( e, new HttpHeaders(), HttpStatus.NOT_FOUND );
    }
}