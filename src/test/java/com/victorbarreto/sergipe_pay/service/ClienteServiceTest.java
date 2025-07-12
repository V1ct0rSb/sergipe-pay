package com.victorbarreto.sergipe_pay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.victorbarreto.sergipe_pay.dto.ClienteDTO;
import com.victorbarreto.sergipe_pay.dto.ContaResponseDTO;
import com.victorbarreto.sergipe_pay.repository.ClienteRespository;
import com.victorbarreto.sergipe_pay.repository.ContaRepository;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRespository clienteRespository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Criação de conta com sucesso!")
    void criarContaSucesso() {
        ClienteDTO clienteDTO = new ClienteDTO("victor", "09898798765", "victor@gmail.com", "senha1234");

        Mockito.when(clienteRespository.existsByEmail(anyString())).thenReturn(false);
        Mockito.when(clienteRespository.existsByCpf(anyString())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada_fake");

        ContaResponseDTO contaResponseDTO = clienteService.criarConta(clienteDTO);
        System.out.println(contaResponseDTO);

        assertNotNull(contaResponseDTO);
        assertEquals("victor", contaResponseDTO.nomeTitular());
        assertEquals(0001, contaResponseDTO.agencia());
        assertEquals(BigDecimal.ZERO, contaResponseDTO.saldo());
    }
}