package com.victorbarreto.sergipe_pay.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.victorbarreto.sergipe_pay.dto.ContaMovimentoDTO;
import com.victorbarreto.sergipe_pay.dto.ContaResponseDTO;
import com.victorbarreto.sergipe_pay.dto.ContaTransferenciaDTO;
import com.victorbarreto.sergipe_pay.dto.TransacaoResponseDTO;
import com.victorbarreto.sergipe_pay.entity.ContaModel;
import com.victorbarreto.sergipe_pay.entity.TipoTransacao;
import com.victorbarreto.sergipe_pay.entity.TransacaoModel;
import com.victorbarreto.sergipe_pay.repository.ContaRepository;
import com.victorbarreto.sergipe_pay.repository.TrasacaoRespository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TrasacaoRespository trasacaoRespository;

    //POST
    public ContaResponseDTO depositar(ContaMovimentoDTO contaMovimentoDTO) {
        ContaModel contaModel = contaRepository.findByNumContaAndAgencia(
                        contaMovimentoDTO.numConta(),
                        contaMovimentoDTO.agencia()
                )
                .orElseThrow(() -> new RuntimeException("Número da Conta ou agência invalida!"));

        contaModel.deposito(contaMovimentoDTO.valor());

        TransacaoModel transacaoModel = new TransacaoModel();
        transacaoModel.setContaModel(contaModel);
        transacaoModel.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacaoModel.setValor(contaMovimentoDTO.valor());
        transacaoModel.setDataHora(LocalDateTime.now());

        contaRepository.save(contaModel);
        trasacaoRespository.save(transacaoModel);

        return new ContaResponseDTO(
                contaModel.getAgencia(),
                contaModel.getNumConta(),
                contaModel.getSaldo(),
                contaModel.getClienteModel().getNome()
        );
    }

    //POST
    public ContaResponseDTO sacar(ContaMovimentoDTO contaMovimentoDTO) {
        ContaModel contaModel = contaRepository.findByNumContaAndAgencia(
                        contaMovimentoDTO.numConta(),
                        contaMovimentoDTO.agencia()
                )
                .orElseThrow(() -> new RuntimeException("Numero da conta ou agência invalida!"));

        contaModel.saque(contaMovimentoDTO.valor());

        TransacaoModel transacaoModel = new TransacaoModel();
        transacaoModel.setContaModel(contaModel);
        transacaoModel.setTipoTransacao(TipoTransacao.SAQUE);
        transacaoModel.setValor(contaMovimentoDTO.valor());
        transacaoModel.setDataHora(LocalDateTime.now());

        contaRepository.save(contaModel);
        trasacaoRespository.save(transacaoModel);

        return new ContaResponseDTO(
                contaModel.getAgencia(),
                contaModel.getNumConta(),
                contaModel.getSaldo(),
                contaModel.getClienteModel().getNome()
        );
    }

    //POST
    public TransacaoResponseDTO tranferir(ContaTransferenciaDTO contaTransferenciaDTO) {
        ContaModel contaModelOrigem = contaRepository.findByNumContaAndAgencia(
                        contaTransferenciaDTO.numContaOrigem(),
                        contaTransferenciaDTO.agenciaOrigem()
                )
                .orElseThrow(() -> new RuntimeException("Numero da conta de Origem ou agencia de origem invalida"));

        ContaModel contaModelDestino = contaRepository.findByNumContaAndAgencia(
                        contaTransferenciaDTO.numContaDestino(),
                        contaTransferenciaDTO.agenciaDestino()
                )
                .orElseThrow(() -> new RuntimeException("Numero da conta de destino ou agencia de destino invalida"));

        if (contaModelOrigem.getSaldo().compareTo(contaTransferenciaDTO.valor()) >= 0) {
            contaModelDestino.setSaldo(contaModelDestino.getSaldo().add(contaTransferenciaDTO.valor()));
            contaModelOrigem.setSaldo(contaModelOrigem.getSaldo().subtract(contaTransferenciaDTO.valor()));
        } else {
            throw new RuntimeException("Saldo da conta origem menor que o valor informado");
        }

        contaRepository.save(contaModelOrigem);
        contaRepository.save(contaModelDestino);

        TransacaoModel transacaoModelOrigem = new TransacaoModel();
        transacaoModelOrigem.setContaModel(contaModelOrigem);
        transacaoModelOrigem.setTipoTransacao(TipoTransacao.TRANSFERENCIA_ENVIDA);
        transacaoModelOrigem.setValor(contaTransferenciaDTO.valor());
        transacaoModelOrigem.setDataHora(LocalDateTime.now());

        TransacaoModel transacaoModelDestino = new TransacaoModel();
        transacaoModelDestino.setContaModel(contaModelDestino);
        transacaoModelDestino.setTipoTransacao(TipoTransacao.TRANSFERENCIA_RECEBIDA);
        transacaoModelDestino.setValor(contaTransferenciaDTO.valor());
        transacaoModelDestino.setDataHora(LocalDateTime.now());

        trasacaoRespository.save(transacaoModelOrigem);
        trasacaoRespository.save(transacaoModelDestino);

        return new TransacaoResponseDTO(
                transacaoModelOrigem.getTipoTransacao(),
                transacaoModelOrigem.getValor(),
                transacaoModelOrigem.getDataHora()
        );

    }

    //GET
    public ContaResponseDTO consultarSaldo(Integer numConta) {
        ContaModel contaModel = contaRepository.findByNumConta(numConta)
                .orElseThrow(() -> new RuntimeException("Número da conta informado não encontrado!"));

        return new ContaResponseDTO(
                contaModel.getAgencia(),
                contaModel.getNumConta(),
                contaModel.getSaldo(),
                contaModel.getClienteModel().getNome()
        );
    }

    public List<TransacaoResponseDTO> extrato(Integer numConta) {
        ContaModel contaModel = contaRepository.findByNumConta(numConta)
                .orElseThrow(() -> new RuntimeException("Número da conta informado não encontrado!"));

        List<TransacaoModel> transacoes = trasacaoRespository.findByContaModel(contaModel);

        if (transacoes.isEmpty()) {
            throw new RuntimeException("Nenhuma transação encontrada para esta conta.");
        }

        return transacoes.stream()
                .map(t -> new TransacaoResponseDTO(
                        t.getTipoTransacao(),
                        t.getValor(),
                        t.getDataHora()
                ))
                .collect(Collectors.toList());
    }

}
