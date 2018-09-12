package com.quinnandrews.rest.webservices.expensetracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * <p>Custom ResponseEntityExceptionHandler.
 *
 * @author Quinn Andrews
 *
 */
@ControllerAdvice
@RestController
public class ExpenseTrackerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        return new ResponseEntity<>(
                new ExpenseTrackerExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) throws Exception {
        return new ResponseEntity<>(
                new ExpenseTrackerExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EntityCannotBeDeletedException.class)
    public ResponseEntity<Object> handleEntityCannotBeDeletedException(EntityCannotBeDeletedException ex, WebRequest request) throws Exception {
        return new ResponseEntity<>(
                new ExpenseTrackerExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)),
                HttpStatus.FORBIDDEN
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ExpenseTrackerExceptionResponse(new Date(), "Validation failed!", ex.getBindingResult().toString()),
                HttpStatus.BAD_REQUEST
        );
    }

}
