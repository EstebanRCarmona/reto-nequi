package com.retonequi.aplication.exception;

import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;

@RestControllerAdvice
public class ControlAdvice {

    @ExceptionHandler(ErrorBadRequest.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleBadRequest(ErrorBadRequest ex, ServerWebExchange exchange) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getMethod().name(),
                Instant.now().toString()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(ExceptionAlreadyExist.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleAlreadyExist(ExceptionAlreadyExist ex, ServerWebExchange exchange) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getMethod().name(),
                Instant.now().toString()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }

    @ExceptionHandler(ErrorNotFound.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleNotFound(ErrorNotFound ex, ServerWebExchange exchange) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getMethod().name(),
                Instant.now().toString()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }
}
