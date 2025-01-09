package com.perscolas.fintracker.exception;

/**
 * Custom exception thrown when an entity is not found in the system.
 * - Extends RuntimeException and is used to indicate that a requested entity does not exist.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
