package com.victorbarreto.sergipe_pay.service;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.victorbarreto.sergipe_pay.dto.ClienteDTO;
import com.victorbarreto.sergipe_pay.dto.ContaResponseDTO;
import com.victorbarreto.sergipe_pay.entity.ClienteModel;
import com.victorbarreto.sergipe_pay.entity.ContaModel;
import com.victorbarreto.sergipe_pay.exception.ClienteJaCadastradoException;
import com.victorbarreto.sergipe_pay.repository.ClienteRespository;
import com.victorbarreto.sergipe_pay.repository.ContaRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRespository clienteRespository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //POST
    public ContaResponseDTO criarConta(ClienteDTO clienteDTO) {

        if (clienteRespository.existsByCpf(clienteDTO.cpf())) {
            throw new ClienteJaCadastradoException("CPF informado já se encontra cadastrado no sistema.");

        }
        if (clienteRespository.existsByEmail(clienteDTO.email())) {
            throw new ClienteJaCadastradoException("E-mail informado já se encontra cadastrado no sistema.");
        }

        ClienteModel clienteModel = new ClienteModel();

        String senhaCriptografada = passwordEncoder.encode(clienteDTO.senha());
        clienteModel.setSenha(senhaCriptografada);
        clienteModel.setNome(clienteDTO.nome());
        clienteModel.setCpf(clienteDTO.cpf());
        clienteModel.setEmail(clienteDTO.email());

        ContaModel contaModel = new ContaModel();
        contaModel.setNumConta(gerarNumConta());
        contaModel.setAgencia(0001);
        contaModel.setSaldo(BigDecimal.ZERO);

        clienteModel.setContaModel(contaModel);
        contaModel.setClienteModel(clienteModel);

        clienteRespository.save(clienteModel);
        contaRepository.save(contaModel);


        return new ContaResponseDTO(
                contaModel.getAgencia(),
                contaModel.getNumConta(),
                contaModel.getSaldo(),
                contaModel.getClienteModel().getNome()
        );
    }

    private Integer gerarNumConta() {
        int numConta;
        int tentativa = 0;

        do {
            numConta = 1000 + new Random().nextInt(9000);
            tentativa++;
            if (tentativa > 1000) {
                throw new RuntimeException("Não foi possivel gerar um numero de conta!");
            }
        } while (contaRepository.existsByNumConta(numConta));
        return numConta;
    }
}
