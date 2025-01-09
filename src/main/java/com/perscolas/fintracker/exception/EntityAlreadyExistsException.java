package com.perscolas.fintracker.exception;

/**
 * Custom exception thrown when an entity already exists in the system.
 * - Extends RuntimeException and is used to indicate that an entity being created already exists.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}
