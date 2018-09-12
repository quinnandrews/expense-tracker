package com.quinnandrews.rest.webservices.expensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>Common Exception thrown when a requested instance for an Entity is not found.
 *
 * @author Quinn Andrews
 *
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class EntityCannotBeDeletedException extends RuntimeException {

    public EntityCannotBeDeletedException(Class entity, Long id) {
        super(entity.getSimpleName() + "[" + id + "] could not be deleted due to foreign key constraints.");
    }

}
