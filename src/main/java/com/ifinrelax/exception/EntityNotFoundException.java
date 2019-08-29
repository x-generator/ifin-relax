package com.ifinrelax.exception;

/**
 * @author Timur Beerzhnoi.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
