package com.victorbarreto.sergipe_pay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.victorbarreto.sergipe_pay.dto.ContaMovimentoDTO;
import com.victorbarreto.sergipe_pay.dto.ContaResponseDTO;
import com.victorbarreto.sergipe_pay.dto.ContaTransferenciaDTO;
import com.victorbarreto.sergipe_pay.dto.TransacaoResponseDTO;
import com.victorbarreto.sergipe_pay.service.ContaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @PostMapping("/transacao/depositar")
    public ResponseEntity<ContaResponseDTO> depositar(@Valid @RequestBody ContaMovimentoDTO contaMovimentoDTO) {
        ContaResponseDTO contaResponseDTO = contaService.depositar(contaMovimentoDTO);
        return ResponseEntity.status(201).body(contaResponseDTO);
    }

    @PostMapping("/transacao/sacar")
    public ResponseEntity<ContaResponseDTO> sacar(@Valid @RequestBody ContaMovimentoDTO contaMovimentoDTO) {
        ContaResponseDTO contaResponseDTO = contaService.sacar(contaMovimentoDTO);
        return ResponseEntity.status(201).body(contaResponseDTO);
    }

    @PostMapping("/transacao/transferir")
    public ResponseEntity<TransacaoResponseDTO> trasferir(@Valid @RequestBody ContaTransferenciaDTO contaTransferenciaDTO) {
        TransacaoResponseDTO transacaoResponseDTO = contaService.tranferir(contaTransferenciaDTO);
        return ResponseEntity.status(201).body(transacaoResponseDTO);
    }

    @GetMapping("/contas/{numConta}/saldo")
    public ResponseEntity<ContaResponseDTO> consultarSaldo(@Valid @PathVariable Integer numConta) {
        ContaResponseDTO contaResponseDTO = contaService.consultarSaldo(numConta);
        return ResponseEntity.ok(contaResponseDTO);
    }

    @GetMapping("/contas/{numConta}/extrato")
    public ResponseEntity<List<TransacaoResponseDTO>> extrato(@Valid @PathVariable Integer numConta) {
        List<TransacaoResponseDTO> transacaoResponseDTOList = contaService.extrato(numConta);
        return ResponseEntity.ok(transacaoResponseDTOList);
    }


}
