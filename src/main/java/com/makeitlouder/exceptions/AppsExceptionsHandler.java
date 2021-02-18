package com.makeitlouder.exceptions;

import com.makeitlouder.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppsExceptionsHandler {

    @ExceptionHandler(value = { UserServiceException.class })
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(new Date())
                .message(ex.getMessage()).build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { PetServiceException.class })
    public ResponseEntity<Object> handlePetServiceException(PetServiceException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(new Date())
                .message(ex.getMessage()).build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ReservationServiceException.class })
    public ResponseEntity<Object> handleReservationServiceException(ReservationServiceException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(new Date())
                .message(ex.getMessage()).build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(new Date())
                .message(ex.getMessage()).build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
