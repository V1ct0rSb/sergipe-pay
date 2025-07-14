package com.victorbarreto.sergipe_pay.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteLoginDTO(@Email(message = "Email inválido") String email,
                              @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") String senha) {
}
