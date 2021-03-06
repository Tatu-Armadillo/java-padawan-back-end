package com.padawan.desafio.repositories;

import com.padawan.desafio.models.Movimento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long>{
    
}
