package com.padawan.desafio.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.padawan.desafio.models.Loja;
import com.padawan.desafio.repositories.LojaRepository;
import com.padawan.desafio.services.LojaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lojas")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private LojaRepository lojaRepository;

    @GetMapping
    public Page<Loja> viewLoja(
            @PageableDefault(sort = "idLoja", direction = Direction.ASC) Pageable pageable) {
        Page<Loja> lojas = lojaRepository.findAll(pageable);
        return lojas;
    }

    @PostMapping
    @Transactional
    public Loja createLoja(@RequestBody @Valid Loja loja) {
        lojaService.createLoja(loja.getFuncionario(), loja.getNome());
        return this.lojaService.getLoja();
    }

}
