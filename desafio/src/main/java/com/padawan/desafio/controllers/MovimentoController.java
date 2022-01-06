package com.padawan.desafio.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.padawan.desafio.models.Movimento;
import com.padawan.desafio.repositories.MovimentoRepository;
import com.padawan.desafio.services.MovimentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimentos")
public class MovimentoController {

    @Autowired
    private MovimentoService movimentoService;

    @Autowired
    private MovimentoRepository movimentoRepository;

    @GetMapping
    public Page<Movimento> viewMovimento(
            @PageableDefault(sort = "idMovimento", direction = Direction.ASC) Pageable pageable) {
        Page<Movimento> movimentos = movimentoRepository.findAll(pageable);
        return movimentos;
    }

    @PostMapping
    @Transactional
    public Movimento realizarMovimento(@RequestBody @Valid Movimento movimento) {
        movimentoService.gerarPedido(movimento.getProduto(), movimento.getCliente(), movimento.getFuncionario());
        return movimentoService.getMovimento();
    }

    @PutMapping("/{idMovimento}")
    @Transactional
    public Movimento updateMovimento(@PathVariable Long idMovimento, @RequestBody Movimento movimento) {
        movimentoService.editarPedido(idMovimento, movimento);
        movimento.setIdMovimento(idMovimento);
        return movimentoService.getMovimento();
    }

}
