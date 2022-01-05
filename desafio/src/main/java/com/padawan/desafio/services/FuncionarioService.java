package com.padawan.desafio.services;

import com.padawan.desafio.models.Funcionario;
import com.padawan.desafio.repositories.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
 
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void update(Long idFuncionario, Funcionario novoFuncionario) {
        Funcionario funcionario = this.funcionarioRepository.getById(idFuncionario);
        funcionario.setNome(novoFuncionario.getNome());
    }
}
