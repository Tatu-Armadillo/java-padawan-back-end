package com.padawan.desafio.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "movimento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimento")
    private Long idMovimento;

    @Column(name = "codigo_inicial")
    private String codigoInicial;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    private LocalDateTime data;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto", referencedColumnName = "id_produto")
    private Produto produto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcionario", referencedColumnName = "id_funcionario")
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    public Movimento() {}

    public Movimento(String codigoInicial, String codigoRastreio, LocalDateTime data, String status, Produto produto,
            Funcionario funcionario, Cliente cliente) {
        this.codigoInicial = codigoInicial;
        this.codigoRastreio = codigoRastreio;
        this.data = data;
        this.status = status;
        this.produto = produto;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    // #region Getters and Setters
    public Long getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(Long idMovimento) {
        this.idMovimento = idMovimento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public String getCodigoInicial() {
        return codigoInicial;
    }

    public void setCodigoInicial(String codigoInicial) {
        this.codigoInicial = codigoInicial;
    }

    // #endregion

}
