package com.victorbarreto.sergipe_pay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.victorbarreto.sergipe_pay.dto.ContaMovimentoDTO;
import com.victorbarreto.sergipe_pay.dto.ContaResponseDTO;
import com.victorbarreto.sergipe_pay.dto.ContaTransferenciaDTO;
import com.victorbarreto.sergipe_pay.dto.TransacaoResponseDTO;
import com.victorbarreto.sergipe_pay.entity.ClienteModel;
import com.victorbarreto.sergipe_pay.entity.ContaModel;
import com.victorbarreto.sergipe_pay.entity.TipoTransacao;
import com.victorbarreto.sergipe_pay.repository.ContaRepository;
import com.victorbarreto.sergipe_pay.repository.TrasacaoRespository;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {
    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TrasacaoRespository trasacaoRespository;

    @Test
    @DisplayName("Deposito feito com sucesso!")
    void depositarSucesso() {
        ContaMovimentoDTO contaMovimentoDTO = new ContaMovimentoDTO(0001, 1111, new BigDecimal("10"));

        ContaModel contaModel = new ContaModel();
        contaModel.setClienteModel(new ClienteModel(
                contaModel,
                "01365954254",
                "victor@gmail.com",
                "victor",
                "senha1234"
        ));
        contaModel.setNumConta(1111);
        contaModel.setAgencia(0001);
        contaModel.setSaldo(BigDecimal.ZERO);


        Mockito.when(contaRepository.findByNumContaAndAgencia(
                contaMovimentoDTO.numConta(),
                contaMovimentoDTO.agencia()
        )).thenReturn(Optional.of(contaModel));

        ContaResponseDTO contaResponseDTO = contaService.depositar(contaMovimentoDTO);

        assertNotNull(contaResponseDTO);
        assertEquals(1, contaResponseDTO.agencia());
        assertEquals(BigDecimal.TEN, contaResponseDTO.saldo());
        assertEquals("victor", contaResponseDTO.nomeTitular());

    }

    @Test
    @DisplayName("Saque feito com sucesso!")
    void sacarSucesso() {
        ContaMovimentoDTO contaMovimentoDTO = new ContaMovimentoDTO(0001, 1111, new BigDecimal("10"));

        ContaModel contaModel = new ContaModel();
        contaModel.setClienteModel(new ClienteModel(
                contaModel,
                "12376567667",
                "victor@gmail.com",
                "victor",
                "senha1234"
        ));
        contaModel.setNumConta(1111);
        contaModel.setAgencia(0001);
        contaModel.setSaldo(new BigDecimal(100));

        Mockito.when(contaRepository.findByNumContaAndAgencia(
                contaMovimentoDTO.numConta(),
                contaMovimentoDTO.agencia()
        )).thenReturn(Optional.of(contaModel));

        ContaResponseDTO contaResponseDTO = contaService.sacar(contaMovimentoDTO);

        assertNotNull(contaResponseDTO);
        assertEquals(1, contaResponseDTO.agencia());
        assertEquals(new BigDecimal(90), contaResponseDTO.saldo());
        assertEquals("victor", contaResponseDTO.nomeTitular());
    }

    @Test
    @DisplayName("Tranferir com sucesso")
    void transferirSucesso() {

        ContaTransferenciaDTO contaTransferenciaDTO = new ContaTransferenciaDTO(
                1111,
                1112,
                0001,
                0001,
                new BigDecimal(90)
        );

        ContaModel contaModelOrigem = new ContaModel();
        contaModelOrigem.setClienteModel(new ClienteModel(
                contaModelOrigem,
                "12387667856",
                "victor@gmail.com",
                "victor",
                "senha1234"
        ));
        contaModelOrigem.setNumConta(1111);
        contaModelOrigem.setAgencia(0001);
        contaModelOrigem.setSaldo(new BigDecimal(100));


        ContaModel contaModelDestino = new ContaModel();
        contaModelDestino.setClienteModel(new ClienteModel(
                contaModelDestino,
                "09878965667",
                "isabela@gmail.com",
                "isabela",
                "senha1234"
        ));
        contaModelDestino.setNumConta(1112);
        contaModelDestino.setAgencia(0001);
        contaModelDestino.setSaldo(BigDecimal.ZERO);

        Mockito.when(contaRepository.findByNumContaAndAgencia(
                        contaModelOrigem.getNumConta(),
                        contaModelOrigem.getAgencia()
                ))
                .thenReturn(Optional.of(contaModelOrigem));

        Mockito.when(contaRepository.findByNumContaAndAgencia(
                        contaModelDestino.getNumConta(),
                        contaModelDestino.getAgencia()
                ))
                .thenReturn(Optional.of(contaModelDestino));

        TransacaoResponseDTO transacaoResponseDTO = contaService.tranferir(contaTransferenciaDTO);

        assertNotNull(transacaoResponseDTO);
        assertEquals(TipoTransacao.TRANSFERENCIA_ENVIDA, transacaoResponseDTO.tipoTransacao());
        assertEquals(new BigDecimal(90), transacaoResponseDTO.valor());
    }
}
