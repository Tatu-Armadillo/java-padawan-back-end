package com.padawan.desafio.services;

import java.util.Optional;

import com.padawan.desafio.models.Cliente;
import com.padawan.desafio.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    private Cliente cliente;

    @Autowired
    private ClienteRepository clienteRepository;

    public void update(Long idCliente, Cliente cliente) {
        this.cliente =  this.clienteRepository.getById(idCliente);
        cliente.setNome(cliente.getNome());
    }

    public Cliente inserirCliente(Long idCliente) {
        Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            System.out.println("Cliente não encontrado");
            return null;
        }
    }

    public Cliente getCliente(){
        return this.cliente;
    }
}
