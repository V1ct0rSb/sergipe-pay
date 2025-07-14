package com.victorbarreto.sergipe_pay.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.victorbarreto.sergipe_pay.exception.ContaSaldoInsuficiennteException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_contas")
public class ContaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer agencia;
    @Column(nullable = false, unique = true)
    private Integer numConta;
    @Column(nullable = false)
    private BigDecimal saldo;

    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteModel clienteModel;

    @OneToMany(mappedBy = "contaModel")
    private List<TransacaoModel> transacaoModelList = new ArrayList<>();

    public ContaModel() {
    }

    public ContaModel(Integer agencia,
                      ClienteModel clienteModel,
                      Integer numConta,
                      BigDecimal saldo,
                      List<TransacaoModel> transacaoModelList) {
        this.agencia = agencia;
        this.clienteModel = clienteModel;
        this.numConta = numConta;
        this.saldo = saldo;
        this.transacaoModelList = transacaoModelList;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public ClienteModel getClienteModel() {
        return clienteModel;
    }

    public void setClienteModel(ClienteModel clienteModel) {
        this.clienteModel = clienteModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumConta() {
        return numConta;
    }

    public void setNumConta(Integer numConta) {
        this.numConta = numConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<TransacaoModel> getTransacaoModelList() {
        return transacaoModelList;
    }

    public BigDecimal deposito(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
        return this.saldo;
    }

    public BigDecimal saque(BigDecimal valor) {
        if (this.saldo.compareTo(valor) >= 0) {
            this.saldo = this.saldo.subtract(valor);

        } else {
            throw new ContaSaldoInsuficiennteException("Saldo menor que o valor informado para saque!");
        }
        return this.saldo;
    }

}
