package it.luca.project.restaurant.exception;

import it.luca.project.restaurant.controller.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ExceptionRestHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequiredFieldException.class)
    public final ResponseEntity<ErrorResponse> fieldConstrainException(RequiredFieldException ex){

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.CONFLICT);
        errorResponse.setTimeSt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,new HttpHeaders(),errorResponse.getStatus());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<ErrorResponse> clientErrorException(HttpClientErrorException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTimeSt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,new HttpHeaders(),errorResponse.getStatus());

    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setMessage("You're not authorized");
        errorResponse.setTimeSt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,new HttpHeaders(),errorResponse.getStatus());
    }
}
