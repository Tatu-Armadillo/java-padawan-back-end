package com.padawan.desafio.services;

import java.util.Optional;

import com.padawan.desafio.models.Funcionario;
import com.padawan.desafio.repositories.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
 
    private Funcionario funcionario;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void update(Long idFuncionario, Funcionario funcionario) {
        this.funcionario = this.funcionarioRepository.getById(idFuncionario);
        funcionario.setNome(funcionario.getNome());
    }

    public Funcionario inserirFuncionario(Long idFuncionario) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findById(idFuncionario);
        if (funcionario.isPresent()) {
            return funcionario.get();
        } else {
            System.out.println("Funcionario não encontrado");
            return null;
        }
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }
}
