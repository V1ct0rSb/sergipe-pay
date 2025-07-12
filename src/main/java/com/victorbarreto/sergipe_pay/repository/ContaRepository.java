package com.victorbarreto.sergipe_pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.sergipe_pay.entity.ContaModel;

public interface ContaRepository extends JpaRepository<ContaModel, Long> {
    boolean existsByNumConta(int numConta);

    Optional<ContaModel> findByNumContaAndAgencia(Integer integer, Integer agencia);

    Optional<ContaModel> findByNumConta(Integer numConta);
}
