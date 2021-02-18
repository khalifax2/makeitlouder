package com.makeitlouder.exceptions;

public class ReservationServiceException extends RuntimeException{
    public static final Long serialVersionUID = 562979753664005861L;

    public ReservationServiceException(String message) {
        super(message);
    }
}
