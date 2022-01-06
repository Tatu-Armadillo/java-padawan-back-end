package com.padawan.desafio.services;

import java.util.Optional;

import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private Produto produto;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void update(Long idProduto, Produto produto) {
        produto = this.produtoRepository.getById(idProduto);
        produto.setNome(produto.getNome());
        produto.setDiretorio(produto.getDiretorio());
        produto.setUrl(produto.getUrl());
        produto.setPreco(produto.getPreco());
        produto.setTamanho(produto.getTamanho());
        produto.setDescricao(produto.getDescricao());
    }

    public Produto inserirProduto(Long idProduto) {
        Optional<Produto> produto = this.produtoRepository.findById(idProduto);
        if (produto.isPresent()) {
            return produto.get();
        } else {
            System.out.println("Produto não encontrado");
            return null;
        }
    }

    public Produto getProduto() {
        return this.produto;
    }
}
