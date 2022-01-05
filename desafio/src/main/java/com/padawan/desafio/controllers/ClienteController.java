package com.padawan.desafio.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.padawan.desafio.models.Cliente;
import com.padawan.desafio.repositories.ClienteRepository;
import com.padawan.desafio.services.ClienteService;

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
@RequestMapping("/clientes")
public class ClienteController {

    private final PageRequest pageable = PageRequest.of(0, 5);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<Cliente> viewClientes() {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        return clientes;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> datailsCliente(@PathVariable Long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> createCliente(@RequestBody @Valid Cliente cliente,
            UriComponentsBuilder uriComponentsBuilder) {
        clienteRepository.save(cliente);
        URI uri = uriComponentsBuilder.path("/{idCliente}").buildAndExpand(cliente.getIdCliente()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @PutMapping("/{idCliente}")
    @Transactional
    public Cliente updateCliente(@PathVariable Long idCliente, @RequestBody Cliente cliente,
            UriComponentsBuilder uriComponentsBuilder) {
        clienteService.update(idCliente, cliente);
        cliente.setIdCliente(idCliente);
        return cliente;
    }

    @DeleteMapping("/{idCliente}")
    @Transactional
    public ResponseEntity<?> removeCliente(@PathVariable Long idCliente) {
        clienteRepository.deleteById(idCliente);
        return ResponseEntity.ok().build();
    }

}
