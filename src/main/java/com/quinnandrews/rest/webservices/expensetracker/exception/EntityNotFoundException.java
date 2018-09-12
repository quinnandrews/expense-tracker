package com.quinnandrews.rest.webservices.expensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>Common Exception thrown when a requested instance for an Entity is not found.
 *
 * @author Quinn Andrews
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class entity, Long id) {
        super(entity.getSimpleName() + "[" + id + "] was not found.");
    }

}
