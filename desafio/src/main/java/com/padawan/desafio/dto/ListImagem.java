package com.padawan.desafio.dto;

import com.padawan.desafio.models.Produto;

import org.springframework.data.domain.Page;

public class ListImagem {

    private String nome;
    private String url;
    private String diretorio;

    public ListImagem(Produto produto) {
        this.nome = produto.getNome();
        this.url = produto.getUrl();
        this.diretorio = produto.getDiretorio();
    }

    public static Page<ListImagem> transform(Page<Produto> produtos) {
        return produtos.map(ListImagem::new);
    }

    // #region Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    // #endregion
}
