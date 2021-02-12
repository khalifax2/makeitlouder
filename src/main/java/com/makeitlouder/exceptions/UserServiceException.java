package com.makeitlouder.exceptions;

public class UserServiceException extends RuntimeException {
    public static final Long serialVersionUID = 762979753664005861L;

    public UserServiceException(String message) {
        super(message);
    }
}
