package com.victorbarreto.sergipe_pay.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ContaMovimentoDTO(@NotNull(message = "Número da agência é obrigatório") @Positive Integer agencia,
                                @NotNull(message = "Número da conta é obrigatório") @Positive Integer numConta,
                                @NotNull(message = "Valor é obrigatório") @Positive BigDecimal valor) {
}
