package com.padawan.desafio.services;

import java.util.Optional;

import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public void update(Long idProduto, Produto novoProduto) {
        Produto produto = this.produtoRepository.getById(idProduto);
        produto.setNome(novoProduto.getNome());
        produto.setDiretorio(novoProduto.getDiretorio());
        produto.setUrl(novoProduto.getUrl());
        produto.setPreco(novoProduto.getPreco());
        produto.setTamanho(novoProduto.getTamanho());
        produto.setDescricao(novoProduto.getDescricao());
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
}
