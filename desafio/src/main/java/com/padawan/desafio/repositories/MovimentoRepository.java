package com.padawan.desafio.repositories;

import com.padawan.desafio.models.Movimento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoRepository extends JpaRepository<Movimento, Long>{
    
}
