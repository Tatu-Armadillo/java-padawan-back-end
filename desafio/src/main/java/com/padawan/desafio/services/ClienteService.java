package com.padawan.desafio.services;

import java.util.Optional;

import com.padawan.desafio.models.Cliente;
import com.padawan.desafio.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    public void update(Long idCliente, Cliente novoCliente) {
        Cliente cliente =  this.clienteRepository.getById(idCliente);
        cliente.setNome(novoCliente.getNome());
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
}
