package com.padawan.desafio.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.padawan.desafio.dto.ListImagem;
import com.padawan.desafio.dto.PrecoProduto;
import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.ProdutoRepository;
import com.padawan.desafio.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
    public Page<Produto> viewProduto(
            @PageableDefault(sort = "idProduto", direction = Direction.ASC) Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        return produtos;
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> datailsProdutos(@PathVariable Long idProduto) {
        Optional<Produto> produto = produtoRepository.findById(idProduto);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/imagens")
    public Page<ListImagem> listImages() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Produto> images = produtoRepository.findAll(pageable);
        return ListImagem.transform(images);
    }

    @GetMapping("/precos")
    public Page<PrecoProduto> listPrecoProduto() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Produto> precos = produtoRepository.findAll(pageable);
        return PrecoProduto.transform(precos);
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
    public Produto updateProduto(@PathVariable Long idProduto, @RequestBody Produto produto) {
        produtoService.update(idProduto, produto);
        produto.setIdProduto(idProduto);
        return produto;
    }

    @DeleteMapping("/{idProduto}")
    @Transactional
    public ResponseEntity<?> removeProduto(@PathVariable Long idProduto) {
        produtoRepository.deleteById(idProduto);
        return ResponseEntity.ok().build();
    }

}
