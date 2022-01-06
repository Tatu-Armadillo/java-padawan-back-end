package com.padawan.desafio.services;

import com.padawan.desafio.models.Funcionario;
import com.padawan.desafio.models.Loja;
import com.padawan.desafio.repositories.LojaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LojaService {

    private Loja loja;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    public void createLoja(Funcionario funcionario, String nome) {
        Funcionario chefe = funcionarioService.inserirFuncionario(funcionario.getIdFuncionario());
        if (chefe.getDono()) {
            this.loja = new Loja(nome, chefe);
            lojaRepository.save(this.loja);
        }
    }

    public Loja getLoja() {
        return this.loja;
    }

}
