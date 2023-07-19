package com.rasha;


import com.rasha.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerConfig {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerConfig.class);

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleError(IllegalStateException e, HttpServletRequest request) {
        String errorMessage = String.format("IllegalStateException occurred while processing the request: %s - Method: %s - IP: %s",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr());
        logger.error(errorMessage, e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleError(IllegalArgumentException e, HttpServletRequest request) {
        String errorMessage = String.format("Invalid argument exception while processing the request: %s - Method: %s - IP: %s",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr());
        logger.warn(errorMessage, e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception e, HttpServletRequest request) {
        String errorMessage = String.format("Exception while processing the request: %s - Method: %s - IP: %s",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr());
        logger.error(errorMessage, e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
