package com.padawan.desafio.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.ProdutoRepository;
import com.padawan.desafio.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produto")
public class ProdutosController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public Page<Produto> viewProduto() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Produto> produto = produtoRepository.findAll(pageable);
        return produto;
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> datailsProduto(@PathVariable Long idProduto) {
        Optional<Produto> produto = produtoRepository.findById(idProduto);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Produto> createProduto(@RequestBody @Valid Produto produto,
            UriComponentsBuilder uriComponentsBuilder) {
        produto.setTamanho(produto.getTamanho().toUpperCase());
        produtoRepository.save(produto);
        URI uri = uriComponentsBuilder.path("/{idProduto}").buildAndExpand(produto.getIdProduto()).toUri();
        return ResponseEntity.created(uri).body(produto);
    }

    @PutMapping("/{idProduto}")
    @Transactional
    public Produto updateProduto(@PathVariable Long idProduto, @RequestBody Produto produto,
            UriComponentsBuilder uriComponentsBuilder) {
        produtoService.update(idProduto, produto);
        produto.setIdProduto(idProduto);        
        return new Produto(produto);
    }

    @DeleteMapping("/{idProduto}")
    @Transactional
    public ResponseEntity<?> removeProduto(@PathVariable Long idProduto) {
        produtoRepository.deleteById(idProduto);
        return ResponseEntity.ok().build();
    } 
}
