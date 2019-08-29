package com.ifinrelax.exception;

/**
 * @author Timur Berezhnoi
 */
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}