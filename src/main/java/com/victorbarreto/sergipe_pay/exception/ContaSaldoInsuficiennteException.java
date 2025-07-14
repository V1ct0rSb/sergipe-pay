package com.victorbarreto.sergipe_pay.exception;

public class ContaSaldoInsuficiennteException extends RuntimeException {
    public ContaSaldoInsuficiennteException(String mensagem) {
        super(mensagem);
    }
}
