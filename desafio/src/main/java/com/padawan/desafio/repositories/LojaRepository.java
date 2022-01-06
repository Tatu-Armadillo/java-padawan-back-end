package com.padawan.desafio.repositories;

import com.padawan.desafio.models.Loja;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long>{
    
}
