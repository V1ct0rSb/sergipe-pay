package com.victorbarreto.sergipe_pay.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ContaResponseDTO(@NotNull(message = "Número da agência é obrigatório") Integer agencia,
                               @NotNull(message = "Número da conta é obrigatório") Integer numConta,
                               @NotNull(message = "Valor é obrigatório") @Positive(message = "O valor deve ser positivo") BigDecimal saldo,
                               @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres") String nomeTitular) {
}
