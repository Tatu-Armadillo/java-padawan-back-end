package com.padawan.desafio.dto;

import java.math.BigDecimal;

import com.padawan.desafio.models.Produto;

import org.springframework.data.domain.Page;

public class PrecoProduto {

    private String nome;
    private BigDecimal preco;

    public PrecoProduto(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }

    public static Page<PrecoProduto> transform(Page<Produto> produtos) {
        return produtos.map(PrecoProduto::new);
    }

    // #region Getters and Setters
    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    // #endregion

}
