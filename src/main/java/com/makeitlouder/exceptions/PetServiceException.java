package com.makeitlouder.exceptions;

public class PetServiceException extends RuntimeException{
    public static final Long serialVersionUID = 772979752664105061L;

    public PetServiceException(String message) {
        super(message);
    }
}
