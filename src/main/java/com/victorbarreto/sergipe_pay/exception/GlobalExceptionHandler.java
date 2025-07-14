package com.victorbarreto.sergipe_pay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteJaCadastradoException.class)
    public ResponseEntity<String> handleClienteJaCadastrado(ClienteJaCadastradoException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ContaSaldoInsuficiennteException.class)
    public ResponseEntity<String> handleContaSaldoInsuficiente(ContaSaldoInsuficiennteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }


}