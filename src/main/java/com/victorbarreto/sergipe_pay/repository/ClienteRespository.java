package com.victorbarreto.sergipe_pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.sergipe_pay.entity.ClienteModel;
import com.victorbarreto.sergipe_pay.entity.ContaModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public interface ClienteRespository extends JpaRepository<ClienteModel, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

//    Optional<ClienteModel> findByEmailAndCpf(String cpf, String email);
}
