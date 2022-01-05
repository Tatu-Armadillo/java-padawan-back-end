package com.padawan.desafio.services;

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
}
