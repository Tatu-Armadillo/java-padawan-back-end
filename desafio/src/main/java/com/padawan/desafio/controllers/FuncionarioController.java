package com.padawan.desafio.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.padawan.desafio.models.Funcionario;
import com.padawan.desafio.repositories.FuncionarioRepository;
import com.padawan.desafio.services.FuncionarioService;

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
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final PageRequest pageable = PageRequest.of(0, 5);

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private FuncionarioService funcionarioService;
    
    @GetMapping
    public Page<Funcionario> viewFuncionarios() {
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
        return funcionarios;
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> datailsFuncionario(@PathVariable Long idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        if (funcionario.isPresent()) {
            return ResponseEntity.ok(funcionario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Funcionario> createCliente(@RequestBody @Valid Funcionario funcionario,
            UriComponentsBuilder uriComponentsBuilder) {
        funcionarioRepository.save(funcionario);
        URI uri = uriComponentsBuilder.path("/{idFuncionario}").buildAndExpand(funcionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(uri).body(funcionario);
    }

    @PutMapping("/{idFuncionario}")
    @Transactional
    public Funcionario updateFuncionario(@PathVariable Long idFuncionario, @RequestBody Funcionario funcionario) {
        funcionarioService.update(idFuncionario, funcionario);
        funcionario.setIdFuncionario(idFuncionario);       
        funcionario.setDono(false); 
        return funcionario;
    }

    @DeleteMapping("/{idFuncionario}")
    @Transactional
    public ResponseEntity<?> removeCliente(@PathVariable Long idFuncionario) {
        funcionarioRepository.deleteById(idFuncionario);
        return ResponseEntity.ok().build();
    } 
}
