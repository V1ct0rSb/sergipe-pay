package com.victorbarreto.sergipe_pay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.sergipe_pay.entity.ContaModel;
import com.victorbarreto.sergipe_pay.entity.TransacaoModel;

public interface TrasacaoRespository extends JpaRepository<TransacaoModel, Long> {
    List<TransacaoModel> findByContaModel(ContaModel contaModel);
}
