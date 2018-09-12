package com.quinnandrews.rest.webservices.expensetracker.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * <p>Common Class for Controllers.
 *
 * @author Quinn Andrews
 *
 */
public class ExpenseTrackerController {

    /**
     * <p>Generates a URI to an instance of an Entity. Added to the Header after the instance has been inserted.
     *
     * @param id    The ID set on the Entity instance after insertion.
     * @return      The URI to the instance of the Entity associated with the specified ID.
     *
     */
    protected URI generateURI(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
