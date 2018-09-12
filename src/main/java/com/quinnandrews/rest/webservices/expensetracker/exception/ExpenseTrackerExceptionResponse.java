package com.quinnandrews.rest.webservices.expensetracker.exception;

import java.util.Date;

/**
 * <p>Formats structure of response appropriately when an Exception is thrown.
 *
 * @author Quinn Andrews
 *
 */
public class ExpenseTrackerExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

    public ExpenseTrackerExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
