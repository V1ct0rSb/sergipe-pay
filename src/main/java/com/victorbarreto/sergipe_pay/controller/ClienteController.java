package com.victorbarreto.sergipe_pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.victorbarreto.sergipe_pay.dto.ClienteDTO;
import com.victorbarreto.sergipe_pay.dto.ClienteLoginDTO;
import com.victorbarreto.sergipe_pay.dto.ContaResponseDTO;
import com.victorbarreto.sergipe_pay.security.JwtUtil;
import com.victorbarreto.sergipe_pay.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/clientes")
    public ResponseEntity<ContaResponseDTO> criarConta(@Valid @RequestBody ClienteDTO clienteDTO) {
        ContaResponseDTO contaResponseDTO = clienteService.criarConta(clienteDTO);
        return ResponseEntity.status(201).body(contaResponseDTO);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody ClienteLoginDTO clienteLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        clienteLoginDTO.email(),
                        clienteLoginDTO.senha()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(userDetails);
    }
}
