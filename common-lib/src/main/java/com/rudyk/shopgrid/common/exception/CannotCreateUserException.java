package com.rudyk.shopgrid.common.exception;

public class CannotCreateUserException extends RuntimeException {

    public CannotCreateUserException(String username, String message) {
        super(String.format("User with username %s cannot be created: ", username, message));
    }

    public CannotCreateUserException(String username, String message, Throwable cause) {
        super(String.format("User with username %s cannot be created", username, message), cause);
    }

}
