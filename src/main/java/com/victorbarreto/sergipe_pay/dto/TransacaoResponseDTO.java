package com.victorbarreto.sergipe_pay.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.victorbarreto.sergipe_pay.entity.TipoTransacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransacaoResponseDTO(@NotNull(message = "Tipo da transação é obrigatório") TipoTransacao tipoTransacao,
                                   @NotNull(message = "Valor é obrigatório") @Positive(message = "O valor deve ser positivo") BigDecimal valor,
                                   @NotNull(message = "Data e hora são obrigatórios") LocalDateTime dataHora) {
}
