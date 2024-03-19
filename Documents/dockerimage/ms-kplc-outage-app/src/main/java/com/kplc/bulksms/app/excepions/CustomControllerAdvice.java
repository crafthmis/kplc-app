package com.kplc.bulksms.app.excepions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }


    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorExceptions(Exception e) {
        CustomErrorException customErrorException = (CustomErrorException) e;
        HttpStatus status = customErrorException.getStatus();
        return new ResponseEntity<>(new ErrorResponse(status, customErrorException.getMessage()), status);
    }

    @ExceptionHandler(CustomExtraErrorException.class)
    public ResponseEntity<ErrorResponse> handleExtraCustomErrorExceptions(Exception e) {
        CustomExtraErrorException customErrorException = (CustomExtraErrorException) e;
        HttpStatus status = customErrorException.getStatus();
        Object obj = status.value()==200||status.value()==201?customErrorException.getData():null;
        return new ResponseEntity<>(new ErrorResponse(status, customErrorException.getMessage(),obj), status);
    }
}