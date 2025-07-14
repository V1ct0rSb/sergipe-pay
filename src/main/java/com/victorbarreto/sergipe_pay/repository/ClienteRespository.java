package com.victorbarreto.sergipe_pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.sergipe_pay.entity.ClienteModel;

public interface ClienteRespository extends JpaRepository<ClienteModel, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<ClienteModel> findByEmail(String email);

}
