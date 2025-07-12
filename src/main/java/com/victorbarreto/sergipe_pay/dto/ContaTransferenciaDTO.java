package com.victorbarreto.sergipe_pay.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ContaTransferenciaDTO(
        @NotNull(message = "Número da conta é obrigatório") @Positive Integer numContaOrigem,
        @NotNull(message = "Número da conta é obrigatório") @Positive Integer numContaDestino,
        @NotNull(message = "Número da agência é obrigatório") @Positive Integer agenciaOrigem,
        @NotNull(message = "Número da agência é obrigatório") @Positive Integer agenciaDestino,
        @NotNull(message = "Valor é obrigatório") @Positive(message = "O valor deve ser positivo") BigDecimal valor) {
}
