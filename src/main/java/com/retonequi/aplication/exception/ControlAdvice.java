package com.retonequi.aplication.exception;

import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ControlAdvice {

    @ExceptionHandler(ErrorBadRequest.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(ErrorBadRequest ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionAlreadyExist.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExist(ExceptionAlreadyExist ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                Instant.now().toString()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ErrorNotFound.class)
public ResponseEntity<ExceptionResponse> handleNotFound(ErrorNotFound ex, HttpServletRequest request) {
    ExceptionResponse response = new ExceptionResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI(),
            request.getMethod(),
            Instant.now().toString()
    );
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
}
}
